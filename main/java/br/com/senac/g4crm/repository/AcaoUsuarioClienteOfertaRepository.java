package br.com.senac.g4crm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senac.g4crm.domain.AcaoUsuarioClienteOferta;
import br.com.senac.g4crm.domain.ClienteOferta;

@Repository
public interface AcaoUsuarioClienteOfertaRepository extends JpaRepository<AcaoUsuarioClienteOferta, Integer> {
	
	public List<AcaoUsuarioClienteOferta> findByClienteOferta(ClienteOferta clienteOferta);
	
	public List<AcaoUsuarioClienteOferta> findAllByDataAcaoBetween(Date dataInicial, Date dataFinal);
	
}
