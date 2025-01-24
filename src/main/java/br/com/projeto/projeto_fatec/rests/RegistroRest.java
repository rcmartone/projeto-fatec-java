package br.com.projeto.projeto_fatec.rests;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.projeto.projeto_fatec.dto.registro.RequisicaoRegistrarDoutorDto;
import br.com.projeto.projeto_fatec.dto.registro.RequisicaoRegistrarPessoaDto;
import br.com.projeto.projeto_fatec.events.EnvioEmailEvent;
import br.com.projeto.projeto_fatec.events.PublicadorDeEvento;
import br.com.projeto.projeto_fatec.models.cliente.Cliente;
import br.com.projeto.projeto_fatec.models.doutor.Doutor;
import br.com.projeto.projeto_fatec.models.usuario.Papel;
import br.com.projeto.projeto_fatec.models.usuario.Usuario;
import br.com.projeto.projeto_fatec.services.JwtService;
import br.com.projeto.projeto_fatec.services.UsuarioService;
import br.com.projeto.projeto_fatec.utils.Validator;

@RestController
@RequestMapping("/registrar")
public class RegistroRest {

    private final UsuarioService usuarioService;
    private final JwtService jwt;
    private final PublicadorDeEvento publicador;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.urlbase}")
    private String urlBase;

    public RegistroRest(JwtService jwt, PasswordEncoder passwordEncoder, PublicadorDeEvento publicador,
            UsuarioService usuarioService) {
        this.jwt = jwt;
        this.passwordEncoder = passwordEncoder;
        this.publicador = publicador;
        this.usuarioService = usuarioService;
    }

    @Transactional(rollbackFor = { InstantiationException.class, IllegalArgumentException.class })
    @PostMapping("/cliente")
    public ResponseEntity<String> registroCliente(@RequestBody RequisicaoRegistrarPessoaDto req) {

        // Validação do CPF
        if (!Validator.cpfValido(req.cpf())) {
            return ResponseEntity.badRequest().body("CPF inválido!");
        }
        verificacaValoresObrigatorios(
                new String[] { req.nome(), req.email(), req.telefone() });
        verificarSenha(req.senha());
        String senhaEncriptada = passwordEncoder.encode(req.senha());

        // Criação do usuario + vincular
        Usuario usuario = new Usuario();
        usuario.setEmail(req.email());
        usuario.setSenha(senhaEncriptada);
        usuario.setAtivo(false);
        usuario.setDataInicio(LocalDateTime.now());
        usuario.setPapel(Papel.CLIENTE);
        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario);
        cliente.setCpf(req.cpf());
        cliente.setSexo(req.sexo());
        cliente.setTelefone(req.telefone());
        cliente.setRg(req.rg());
        cliente.setNome(req.nome());
        cliente.setClientesContato(req.contatos());
        cliente.setClientesPrecedente(req.precedentes());
        usuario.setCliente(cliente);

        // salvar no banco de dados
        usuarioService.salvarUsuario(usuario);
        gerarEmailVerificacao(usuario);
        return ResponseEntity.ok().build();
    }

    @Transactional(rollbackFor = { InstantiationException.class,
            IllegalArgumentException.class })
    @PostMapping("/doutor")
    public ResponseEntity<String> registroDoutor(@RequestBody RequisicaoRegistrarDoutorDto req) {

        verificacaValoresObrigatorios(new String[] { req.nome(), req.email(), req.cro() });
        verificarSenha(req.senha());
        String senhaEncriptada = passwordEncoder.encode(req.senha());

        // Criação do usuario + vincular
        Usuario usuario = new Usuario();
        usuario.setEmail(req.email());
        usuario.setSenha(senhaEncriptada);
        usuario.setDataInicio(LocalDateTime.now());
        usuario.setAtivo(false);
        usuario.setPapel(Papel.DOUTOR);
        Doutor doutor = new Doutor();
        doutor.setUsuario(usuario);
        doutor.setNome(req.nome());
        doutor.setCro(req.cro());
        usuario.setDoutor(doutor);

        // salvar no banco de dados
        usuarioService.salvarUsuario(usuario);
        gerarEmailVerificacao(usuario);
        return ResponseEntity.ok().build();
    }

    private void verificacaValoresObrigatorios(String[] valores) {
        for (String valor : valores) {
            if (valor == null || valor.isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campos obrigatórios nulos ou inválidos");
            }
        }
    }

    private void verificarSenha(String senha) {
        if (!Validator.senhaForte(senha)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha não atende aos requisitos mínimos.");
        }
    }

    private void gerarEmailVerificacao(Usuario usuario) {
        String linkVerificacao = urlBase + "/verificar-email?token=" +
                jwt.gerarToken(usuario);

        Map<String, Object> variaveis = new HashMap<>();
        variaveis.put("linkConfirmacao", linkVerificacao);
        try {
            publicador.publicarEvento(EnvioEmailEvent.class, usuario.getEmail(),
                    "Confirmação de Email", "confirmar-email",
                    variaveis);
        } catch (InstantiationException | IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Não foi possivel enviar o Email de confirmação.");
        }
    }
}
