package br.com.senac.g4crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.senac.g4crm.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Integer> {

}
