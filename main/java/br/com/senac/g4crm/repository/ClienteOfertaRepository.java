package br.com.senac.g4crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senac.g4crm.domain.ClienteOferta;
import br.com.senac.g4crm.domain.ClienteOfertaId;
import br.com.senac.g4crm.domain.Oferta;

@Repository
public interface ClienteOfertaRepository extends JpaRepository<ClienteOferta, ClienteOfertaId> {

	@Query("select co from ClienteOferta co where co.clienteOfertaId.oferta=?1")
	public List<ClienteOferta> findByIdOferta(Oferta oferta);
	
}
