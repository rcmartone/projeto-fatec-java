package br.com.projeto.projeto_fatec.rests;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.projeto_fatec.dto.RequisicaoEditarContatoDto;
import br.com.projeto.projeto_fatec.dto.registro.ContatoDto;
import br.com.projeto.projeto_fatec.dto.registro.PrecedenteDto;
import br.com.projeto.projeto_fatec.dto.registro.RequisicaoTrocarSenhaDto;
import br.com.projeto.projeto_fatec.models.cliente.Cliente;
import br.com.projeto.projeto_fatec.models.cliente.ClienteContato;
import br.com.projeto.projeto_fatec.models.cliente.ClientePrecedente;
import br.com.projeto.projeto_fatec.models.precedente.Precedente;
import br.com.projeto.projeto_fatec.models.usuario.Usuario;
import br.com.projeto.projeto_fatec.repositories.ClienteContatoRepository;
import br.com.projeto.projeto_fatec.repositories.PrecedenteRepository;
import br.com.projeto.projeto_fatec.repositories.UsuarioRepository;
import br.com.projeto.projeto_fatec.services.UsuarioService;
import br.com.projeto.projeto_fatec.utils.ValidadorSenha;
import br.com.projeto.projeto_fatec.utils.ValidadorTelefone;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/cliente")
public class EditarClienteRest {

    private final UsuarioService usuarioService;
    private final PrecedenteRepository precedenteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClienteContatoRepository clienteContatoRepository;
    private final PasswordEncoder passwordEncoder;

    public EditarClienteRest(ClienteContatoRepository clienteContatoRepository, PasswordEncoder passwordEncoder,
            PrecedenteRepository precedenteRepository, UsuarioRepository usuarioRepository,
            UsuarioService usuarioService) {
        this.clienteContatoRepository = clienteContatoRepository;
        this.passwordEncoder = passwordEncoder;
        this.precedenteRepository = precedenteRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @PutMapping("/editar-precedentes")
    @Transactional
    public ResponseEntity<String> editarPrecedentes(@RequestBody List<PrecedenteDto> req)
            throws IllegalArgumentException {
        Usuario usuario = usuarioService.obterUsuarioLogado();
        Cliente cliente = usuario.getCliente();
        List<ClientePrecedente> precedentesAtuais = cliente.getClientesPrecedente();

        Set<Long> codigosRecebidos = req.stream()
                .map(PrecedenteDto::codigo)
                .collect(Collectors.toSet());

        List<Precedente> precedentesDisponiveis = precedenteRepository.findAllById(codigosRecebidos);

        if (precedentesDisponiveis.size() != codigosRecebidos.size()) {
            throw new IllegalArgumentException("Um ou mais precedentes não foram encontrados.");
        }

        List<ClientePrecedente> novosPrecedentes = req.stream().map(precedente -> {

            Optional<ClientePrecedente> existente = precedentesAtuais.stream()
                    .filter(clientePrecedente -> clientePrecedente.getPrecedente().getId()
                            .equals(precedente.codigo()))
                    .findFirst();
            if (existente.isPresent()) {
                existente.get().setRespostaAdicional(precedente.adicional());
                return existente.get();
            } else {
                ClientePrecedente novoPrecedente = new ClientePrecedente();
                novoPrecedente.setCliente(cliente);
                Precedente precedenteNovo = precedentesDisponiveis.stream()
                        .filter(precedentes -> precedentes.getId().equals(precedente.codigo()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Precedente inválido."));
                novoPrecedente.setPrecedente(precedenteNovo);
                novoPrecedente.setRespostaAdicional(precedente.adicional());
                return novoPrecedente;
            }
        }).collect(Collectors.toList());
        cliente.setClientesPrecedente(novosPrecedentes);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Precedentes atualizados com sucesso.");
    }

    @PutMapping("/editar-contatos")
    @Transactional
    public ResponseEntity<String> editarContatos(@RequestBody RequisicaoEditarContatoDto reqs)
            throws IllegalArgumentException {
        Usuario usuario = usuarioService.obterUsuarioLogado();
        Cliente cliente = usuario.getCliente();
        ValidadorTelefone.telefoneValido(reqs.telefone());
        List<ClienteContato> contatosAtuais = cliente.getClientesContato();
        Set<String> contatosRecebidos = reqs.contatos().stream()
                .map(ContatoDto::contato)
                .collect(Collectors.toSet());
        for (ContatoDto contatoDto : reqs.contatos()) {
            Optional<ClienteContato> contatoExistente = contatosAtuais.stream()
                    .filter(contato -> contato.getContato().equals(contatoDto.contato()))
                    .findFirst();
            if (contatoExistente.isPresent()) {
                contatoExistente.get().setTipo(contatoDto.tipo());
            } else {
                ClienteContato novoContato = new ClienteContato();
                novoContato.setContato(contatoDto.contato());
                novoContato.setTipo(contatoDto.tipo());
                novoContato.setCliente(cliente);
                contatosAtuais.add(novoContato);
            }
        }
        contatosAtuais.removeIf(contato -> {
            if (!contatosRecebidos.contains(contato.getContato())) {
                clienteContatoRepository.delete(contato);
                return true;
            }
            return false;
        });
        cliente.setClientesContato(contatosAtuais);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Contatos atualizados com sucesso.");
    }

    @PutMapping("/trocar-senha")
    @Transactional
    public ResponseEntity<String> trocarSenha(@RequestBody RequisicaoTrocarSenhaDto req)
            throws IllegalArgumentException {
        Usuario usuario = usuarioService.obterUsuarioLogado();
        ValidadorSenha.senhaForte(req.senhaNova());
        if (!passwordEncoder.matches(req.senhaAntiga(), usuario.getSenha())) {
            throw new IllegalArgumentException("Credenciais inválidas");
        }
        usuario.setSenha(passwordEncoder.encode(req.senhaNova()));
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Senha alterada com sucesso.");
    }
}
