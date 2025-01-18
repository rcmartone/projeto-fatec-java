package br.com.projeto.projeto_fatec.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.projeto.projeto_fatec.dto.RequisicaoLoginDto;
import br.com.projeto.projeto_fatec.models.usuario.Usuario;
import br.com.projeto.projeto_fatec.repositories.UsuarioRepository;

@Service
public class RegistroService {
    private final UsuarioRepository usuarioRepository;

    private final AuthenticationManager authenticationManager;

    public RegistroService(
            UsuarioRepository usuarioRepository,
            AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario authenticate(RequisicaoLoginDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.username(),
                        input.senha()));

        return usuarioRepository.findByEmail(input.username())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, /* Erro 400 */
                        "Credenciais incorretas ou inexistentes"));
    }
}
