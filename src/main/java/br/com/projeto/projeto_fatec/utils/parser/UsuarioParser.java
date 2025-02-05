package br.com.projeto.projeto_fatec.utils.parser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.projeto.projeto_fatec.dto.registro.RequisicaoRegistrarClienteDto;
import br.com.projeto.projeto_fatec.dto.registro.RequisicaoRegistrarDoutorDto;
import br.com.projeto.projeto_fatec.models.cliente.Cliente;
import br.com.projeto.projeto_fatec.models.cliente.ClienteContato;
import br.com.projeto.projeto_fatec.models.cliente.ClientePrecedente;
import br.com.projeto.projeto_fatec.models.doutor.Doutor;
import br.com.projeto.projeto_fatec.models.precedente.Precedente;
import br.com.projeto.projeto_fatec.models.usuario.Papel;
import br.com.projeto.projeto_fatec.models.usuario.Usuario;
import br.com.projeto.projeto_fatec.repositories.PrecedenteRepository;
import br.com.projeto.projeto_fatec.utils.ValidadorCpf;
import br.com.projeto.projeto_fatec.utils.ValidadorSenha;
import br.com.projeto.projeto_fatec.utils.ValidadorTelefone;
import br.com.projeto.projeto_fatec.utils.ValidadorValoresObrigatorios;

@Component
public class UsuarioParser {

    private final PrecedenteRepository precedenteRepository;

    public UsuarioParser(PrecedenteRepository precedenteRepository) {
        this.precedenteRepository = precedenteRepository;
    }

    public Usuario criarUsuarioCliente(RequisicaoRegistrarClienteDto dto)
            throws IllegalArgumentException {
        ValidadorValoresObrigatorios.verificacaValoresObrigatorios(dto.nome(), dto.email());
        ValidadorCpf.cpfValido(dto.cpf());
        ValidadorTelefone.telefoneValido(dto.telefone());
        ValidadorSenha.senhaForte(dto.senha());
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        usuario.setAtivo(false);
        usuario.setDataInicio(LocalDateTime.now());
        usuario.setPapel(Papel.CLIENTE);
        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario);
        cliente.setCpf(dto.cpf());
        cliente.setSexo(dto.sexo());
        cliente.setTelefone(dto.telefone());
        cliente.setRg(dto.rg());
        cliente.setNome(dto.nome());
        List<ClienteContato> contatos = dto.contatos().stream().map(contato -> {
            ClienteContato clienteContato = new ClienteContato();
            clienteContato.setTipo(contato.tipo());
            clienteContato.setCliente(cliente);
            clienteContato.setContato(contato.contato());
            return clienteContato;
        }).collect(Collectors.toList());
        cliente.setClientesContato(contatos);
        List<Precedente> tiposPrecedente = precedenteRepository.findAllById(dto.precedentes().stream()
                .map(precedente -> precedente.codigo()).collect(Collectors.toList()));
        List<ClientePrecedente> precedentes = dto.precedentes().stream().map(precedente -> {
            ClientePrecedente clientePrecedente = new ClientePrecedente();
            clientePrecedente.setCliente(cliente);
            Precedente tipoPrecedente = tiposPrecedente.stream()
                    .filter(tipoPre -> tipoPre.getId().equals(precedente.codigo()))
                    .findFirst().orElseThrow(() -> new IllegalArgumentException(
                            String.format("O precedente de código %d não foi encontrado.", precedente.codigo())));

            clientePrecedente.setPrecedente(tipoPrecedente);
            if (tipoPrecedente.getAdicional() == true) {
                if (precedente.adicional() == null || precedente.adicional().isBlank()) {
                    throw new IllegalArgumentException(
                            String.format("O precedente %s precisa da informação adicional", tipoPrecedente.getNome()));
                }
                clientePrecedente.setRespostaAdicional(precedente.adicional());
            }
            return clientePrecedente;
        }).collect(Collectors.toList());
        cliente.setClientesPrecedente(precedentes);
        usuario.setCliente(cliente);
        return usuario;
    }

    public Usuario criarUsuarioDoutor(RequisicaoRegistrarDoutorDto dto) throws IllegalArgumentException {
        ValidadorValoresObrigatorios.verificacaValoresObrigatorios(dto.nome(), dto.email(), dto.cro());
        ValidadorSenha.senhaForte(dto.senha());
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        usuario.setDataInicio(LocalDateTime.now());
        usuario.setAtivo(false);
        usuario.setPapel(Papel.DOUTOR);
        Doutor doutor = new Doutor();
        doutor.setUsuario(usuario);
        doutor.setNome(dto.nome());
        doutor.setCro(dto.cro());
        usuario.setDoutor(doutor);
        return usuario;
    }

}
