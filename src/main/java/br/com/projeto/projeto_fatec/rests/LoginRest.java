package br.com.projeto.projeto_fatec.rests;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.projeto_fatec.dto.registro.requisicao.RequisicaoLoginDto;
import br.com.projeto.projeto_fatec.dto.resposta.RespostaLoginDto;
import br.com.projeto.projeto_fatec.models.usuario.Usuario;
import br.com.projeto.projeto_fatec.services.JwtService;
import br.com.projeto.projeto_fatec.services.UsuarioService;

@RestController
@RequestMapping("/login")
public class LoginRest {

    private final UsuarioService usuarioService;
    private final JwtService jwt;

    public LoginRest(JwtService jwt, UsuarioService usuarioService) {
        this.jwt = jwt;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<RespostaLoginDto> login(@RequestBody RequisicaoLoginDto req) {

        Usuario usuarioAutenticado = usuarioService.authenticate(req);
        String jwtToken = jwt.gerarToken(usuarioAutenticado);

        return ResponseEntity.ok(new RespostaLoginDto(jwtToken));

    }
}
