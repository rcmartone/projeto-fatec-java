package br.com.projeto.projeto_fatec.dto;

import java.time.LocalDate;
import java.util.List;

import br.com.projeto.projeto_fatec.models.cliente.ClienteContato;
import br.com.projeto.projeto_fatec.models.cliente.ClientePrecedente;
import br.com.projeto.projeto_fatec.models.cliente.Sexo;

public record RequisicaoRegistrarPessoaDto(
                String nome,
                String cpf,
                LocalDate dataNascimento,
                Sexo sexo,
                String rg,
                String email,
                String telefone,
                List<ClienteContato> contatos,
                List<ClientePrecedente> precedentes,
                String senha) {
}
