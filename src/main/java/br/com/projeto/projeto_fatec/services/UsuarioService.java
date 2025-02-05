package br.com.projeto.projeto_fatec.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.com.projeto.projeto_fatec.dto.RequisicaoLoginDto;
import br.com.projeto.projeto_fatec.dto.registro.RequisicaoRegistrarClienteDto;
import br.com.projeto.projeto_fatec.dto.registro.RequisicaoRegistrarDoutorDto;
import br.com.projeto.projeto_fatec.events.EnvioEmailEvent;
import br.com.projeto.projeto_fatec.events.PublicadorDeEvento;
import br.com.projeto.projeto_fatec.models.usuario.Usuario;
import br.com.projeto.projeto_fatec.repositories.UsuarioRepository;
import br.com.projeto.projeto_fatec.utils.parser.UsuarioParser;

@Service
public class UsuarioService {
    @Value("${app.urlbase}")
    private String urlBase;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwt;
    private final PublicadorDeEvento publicador;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UsuarioParser usuarioParser;

    public UsuarioService(AuthenticationManager authenticationManager, JwtService jwt, PasswordEncoder passwordEncoder,
            PublicadorDeEvento publicador, UsuarioParser usuarioParser, UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwt = jwt;
        this.passwordEncoder = passwordEncoder;
        this.publicador = publicador;
        this.usuarioParser = usuarioParser;
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario authenticate(RequisicaoLoginDto input) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.username(),
                            input.senha()));
        } catch (DisabledException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Conta inativa ou Email não confirmado");
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Credenciais incorretas ou inexistentes");
        }

        return usuarioRepository.findByEmail(input.username())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Credenciais incorretas ou inexistentes"));
    }

    @Transactional
    public void salvarCliente(RequisicaoRegistrarClienteDto dto) throws IllegalArgumentException {
        Usuario usuario = usuarioParser.criarUsuarioCliente(dto);
        encriptarSenha(usuario);
        salvarUsuario(usuario);
        gerarEmailVerificacao(usuario);
    }

    @Transactional
    public void salvarDoutor(RequisicaoRegistrarDoutorDto dto) throws IllegalArgumentException {
        Usuario usuario = usuarioParser.criarUsuarioDoutor(dto);
        encriptarSenha(usuario);
        salvarUsuario(usuario);
        gerarEmailVerificacao(usuario);
    }

    public Usuario obterUsuarioLogado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não encontrado."));
    }

    @Transactional
    public void salvarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
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

    private void encriptarSenha(Usuario usuario) {
        String senhaEncriptada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaEncriptada);
    }

}
