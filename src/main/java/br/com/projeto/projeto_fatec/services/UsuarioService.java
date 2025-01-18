package br.com.projeto.projeto_fatec.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.projeto.projeto_fatec.dto.RequisicaoLoginDto;
import br.com.projeto.projeto_fatec.models.usuario.Usuario;
import br.com.projeto.projeto_fatec.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    private final AuthenticationManager authenticationManager;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
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
                    HttpStatus.BAD_REQUEST, /* Erro 400 */
                    "Conta inativa ou Email não confirmado");
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, /* Erro 400 */
                    "Credenciais incorretas ou inexistentes");
        }

        return usuarioRepository.findByEmail(input.username())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, /* Erro 400 */
                        "Credenciais incorretas ou inexistentes"));
    }

    public void salvarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}
