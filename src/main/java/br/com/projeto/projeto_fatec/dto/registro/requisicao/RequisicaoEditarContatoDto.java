package br.com.projeto.projeto_fatec.dto.registro.requisicao;

import java.util.List;

import br.com.projeto.projeto_fatec.dto.registro.ContatoDto;

public record RequisicaoEditarContatoDto(
                String telefone,
                List<ContatoDto> contatos) {

}
