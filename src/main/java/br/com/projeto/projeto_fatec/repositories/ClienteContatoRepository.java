package br.com.projeto.projeto_fatec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.projeto.projeto_fatec.models.cliente.ClienteContato;

@Repository
public interface ClienteContatoRepository extends JpaRepository<ClienteContato, Long> {
    void deleteByClienteCpf(String cpf);
}
