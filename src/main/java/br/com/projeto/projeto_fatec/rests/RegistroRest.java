package br.com.projeto.projeto_fatec.rests;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.projeto.projeto_fatec.dto.RequisicaoLoginDto;
import br.com.projeto.projeto_fatec.dto.RequisicaoRegistrarPessoaDto;
import br.com.projeto.projeto_fatec.dto.RespostaLoginDto;
import br.com.projeto.projeto_fatec.events.EnvioEmailEvent;
import br.com.projeto.projeto_fatec.events.PublicadorDeEvento;
import br.com.projeto.projeto_fatec.models.cliente.Cliente;
import static br.com.projeto.projeto_fatec.models.usuario.Papel.CLIENTE;
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

    @Value("${app.urlbase}")
    private String urlBase;

    public RegistroRest(JwtService jwt, PublicadorDeEvento publicador, UsuarioService usuarioService) {
        this.jwt = jwt;
        this.publicador = publicador;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/teste")
    public String getTeste() {
        return "Funcionou";
    }

    @Transactional(rollbackFor = { InstantiationException.class, IllegalArgumentException.class })
    @PostMapping("/cliente")
    public ResponseEntity<String> registro(@RequestBody RequisicaoRegistrarPessoaDto req) {

        // Validação da força da senha
        if (!Validator.senhaForte(req.senha())) {
            return ResponseEntity.badRequest().body("Senha não atende aos requisitos mínimos.");
        }

        // Validação do CPF
        if (!Validator.cpfValido(req.cpf())) {
            return ResponseEntity.badRequest().body("CPF inválido!");
        }

        // Validação de campos obrigatórios
        if (req.nome() == null || req.nome().isBlank() ||
                req.email() == null || req.email().isBlank() ||
                req.telefone() == null || req.telefone().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campos obrigatórios nulos ou inválidos");
        }

        // Criação do usuario + vincular

        Usuario usuario = new Usuario();
        usuario.setEmail(req.email());
        usuario.setSenha(req.senha());
        usuario.setAtivo(false);
        usuario.setDataInicio(LocalDateTime.now());
        usuario.setPapel(CLIENTE);
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

        String linkVerificacao = urlBase + "/verificar-email?token=" +
                jwt.gerarToken(usuario);

        Map<String, Object> variaveis = new HashMap<>();
        variaveis.put("linkConfirmacao", linkVerificacao);
        try {
            publicador.publicarEvento(EnvioEmailEvent.class, req.email(), "Confirmação de Email", "confirmar-email",
                    variaveis);
        } catch (InstantiationException | IllegalArgumentException ex) {
            return ResponseEntity.internalServerError().body("Não foi possivel enviar o Email de confirmação");
        }
        return ResponseEntity.ok().build();
    }

    // Geração de token de verificação
    @PostMapping
    public ResponseEntity<RespostaLoginDto> login(@RequestBody RequisicaoLoginDto req) {

        Usuario usuarioAutenticado = usuarioService.authenticate(req);
        String jwtToken = jwt.gerarToken(usuarioAutenticado);

        return ResponseEntity.ok(new RespostaLoginDto(jwtToken));
    }
}
