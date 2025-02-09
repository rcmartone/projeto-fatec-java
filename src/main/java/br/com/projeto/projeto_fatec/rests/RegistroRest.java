package br.com.projeto.projeto_fatec.rests;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.projeto_fatec.dto.registro.requisicao.RequisicaoConfirmarEmailDto;
import br.com.projeto.projeto_fatec.dto.registro.requisicao.RequisicaoRegistrarClienteDto;
import br.com.projeto.projeto_fatec.dto.registro.requisicao.RequisicaoRegistrarDoutorDto;
import br.com.projeto.projeto_fatec.dto.resposta.RespostaConfirmarEmailDto;
import br.com.projeto.projeto_fatec.services.UsuarioService;

@RestController
@RequestMapping("/registrar")
public class RegistroRest {

    private final UsuarioService usuarioService;

    public RegistroRest(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cliente")
    public ResponseEntity<String> registroCliente(@RequestBody RequisicaoRegistrarClienteDto req) {
        usuarioService.salvarCliente(req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/doutor")
    public ResponseEntity<String> registroDoutor(@RequestBody RequisicaoRegistrarDoutorDto req) {
        usuarioService.salvarDoutor(req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/confirmar-email")
    public ResponseEntity<RespostaConfirmarEmailDto> confirmarEmail(@RequestBody RequisicaoConfirmarEmailDto req) {
        String token = usuarioService.ativarUsuario(req);
        return ResponseEntity.ok(new RespostaConfirmarEmailDto(token));
    }
}
