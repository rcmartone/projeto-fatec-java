package br.com.projeto.projeto_fatec.dto.registro.requisicao;

public record RequisicaoConfirmarEmailDto(
                String token,
                String senha,
                String email) {
}
