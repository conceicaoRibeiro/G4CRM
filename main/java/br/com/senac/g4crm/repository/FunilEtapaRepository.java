package br.com.senac.g4crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senac.g4crm.domain.FunilEtapa;

@Repository
public interface FunilEtapaRepository extends JpaRepository<FunilEtapa, Integer> {

	public FunilEtapa findTopByOrderByFunilEtapaIdDesc();
	
	public FunilEtapa findTopByOrderByFunilEtapaIdAsc();
	
}
