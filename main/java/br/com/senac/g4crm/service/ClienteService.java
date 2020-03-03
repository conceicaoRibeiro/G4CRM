package br.com.senac.g4crm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.g4crm.domain.Cliente;
import br.com.senac.g4crm.repository.ClienteRepository;
import br.com.senac.g4crm.service.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> objCurso = repository.findById(id);
		return objCurso.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado! Id: "+id+", Tipo: "+Cliente.class.getName()));
	}
	
	public Cliente inserir(Cliente cliente) {
		return repository.save(cliente);
	}
	
	public Cliente alterar(Cliente cliente) throws ObjectNotFoundException {
		Cliente clienteEncontrado = buscar(cliente.getClienteId());
		clienteEncontrado.setNome(cliente.getNome());
		clienteEncontrado.setSobrenome(cliente.getSobrenome());
		clienteEncontrado.setCpf(cliente.getCpf());
		clienteEncontrado.setEmail(cliente.getEmail());
		clienteEncontrado.setStatus(cliente.getStatus());
		return repository.save(clienteEncontrado);
	}
	
	public void excluir(Integer id) {
		repository.deleteById(id);
	}
	
	public List<Cliente> listAll() {
		return repository.findAll();
	}
	

}
