package br.com.projeto.projeto_fatec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.projeto.projeto_fatec.models.precedente.Precedente;

@Repository
public interface PrecedenteRepository extends JpaRepository<Precedente, Long> {

}
