package br.com.senac.g4crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senac.g4crm.domain.Acao;

@Repository
public interface AcaoRepository extends JpaRepository<Acao, Integer> {

}
