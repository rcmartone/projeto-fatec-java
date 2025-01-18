package br.com.projeto.projeto_fatec.rests;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.projeto_fatec.dto.RequisicaoLoginDto;
import br.com.projeto.projeto_fatec.dto.RespostaLoginDto;
import br.com.projeto.projeto_fatec.models.usuario.Usuario;
import br.com.projeto.projeto_fatec.services.JwtService;
import br.com.projeto.projeto_fatec.services.UsuarioService;

@RestController
@RequestMapping("/login")
public class LoginRest {

    /*----Injeção de dependencia-----
     * Processo de se criar e prover instancias para as
     * propriedades que uma classe precisa para ser instanciada e são fornecidas
     * de fora da classe, aqui pelo Spring Framework e é um tipo de inversão de controle,
     * onde o controle é de quem utilizar a classe e não dela mesma.
     * 
     * ------ Pontos de injeção--------
     *  
     * 1 - Construtor, criar classe e propriedades de instancia 
     * e no construtor você recebe cada propriedade de instacia, sem o
     * trabalho de instanciar as propriedades, recebendo elas via construtor.
     * 
     * 2 - Pelo Setter de cada propriedade que se precisa.
     */

    private final UsuarioService usuarioService;
    private final JwtService jwt;

    public LoginRest(JwtService jwt, UsuarioService usuarioService) {
        this.jwt = jwt;
        this.usuarioService = usuarioService;
    }

    /* @PostMapping("/SUBROTA") */

    @PostMapping
    public ResponseEntity<RespostaLoginDto> login(@RequestBody RequisicaoLoginDto req) {

        Usuario usuarioAutenticado = usuarioService.authenticate(req);
        String jwtToken = jwt.gerarToken(usuarioAutenticado);

        return ResponseEntity.ok(new RespostaLoginDto(jwtToken));

    }
}
