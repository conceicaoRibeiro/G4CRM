package br.com.senac.g4crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.senac.g4crm.domain.Cliente;
import br.com.senac.g4crm.domain.ClienteDado;

public interface ClienteDadoRepository extends JpaRepository<ClienteDado, Integer>{
	
	List<ClienteDado> findByCliente(Cliente cliente);

}
