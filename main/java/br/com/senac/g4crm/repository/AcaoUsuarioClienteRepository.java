package br.com.senac.g4crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senac.g4crm.domain.AcaoUsuarioCliente;
import br.com.senac.g4crm.domain.Cliente;

@Repository
public interface AcaoUsuarioClienteRepository extends JpaRepository<AcaoUsuarioCliente, Integer>{
	
	public List<AcaoUsuarioCliente> findByCliente(Cliente cliente);

}
