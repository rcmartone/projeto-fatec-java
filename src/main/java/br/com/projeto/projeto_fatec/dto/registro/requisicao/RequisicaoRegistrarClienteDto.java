package br.com.projeto.projeto_fatec.dto.registro.requisicao;

import java.time.LocalDate;
import java.util.List;

import br.com.projeto.projeto_fatec.dto.registro.ContatoDto;
import br.com.projeto.projeto_fatec.dto.registro.PrecedenteDto;
import br.com.projeto.projeto_fatec.models.cliente.Sexo;

public record RequisicaoRegistrarClienteDto(
        String nome,
        String cpf,
        LocalDate dataNascimento,
        Sexo sexo,
        String rg,
        String email,
        String telefone,
        List<ContatoDto> contatos,
        List<PrecedenteDto> precedentes,
        String senha) {
}
