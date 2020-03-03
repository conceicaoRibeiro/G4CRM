package br.com.senac.g4crm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.g4crm.domain.Cliente;
import br.com.senac.g4crm.domain.ClienteDado;
import br.com.senac.g4crm.repository.ClienteDadoRepository;
import br.com.senac.g4crm.service.exception.ObjectNotFoundException;

@Service
public class ClienteDadoService {
	
	@Autowired
	private ClienteDadoRepository repository;
	
	public ClienteDado buscar(Integer id) {
		Optional<ClienteDado> clienteDadoEncontrado = repository.findById(id);
		return clienteDadoEncontrado.orElseThrow(() -> new ObjectNotFoundException("ClienteDado não encontrado! Id: "+id+", Tipo: "+ClienteDado.class.getName()));
	}
	
	public ClienteDado inserir(ClienteDado clienteDado) {
		clienteDado.setClienteDadoId(null);
		return repository.save(clienteDado);
	}
	
	public List<ClienteDado> inserir(List<ClienteDado> clientesDado) {
		return repository.saveAll(clientesDado);
	}
	
	public ClienteDado alterar(ClienteDado clienteDado) throws ObjectNotFoundException {
		ClienteDado clienteDadoEncontrado = buscar(clienteDado.getClienteDadoId());
		clienteDadoEncontrado.setValor(clienteDado.getValor());
		clienteDadoEncontrado.setStatus(clienteDado.getStatus());
		clienteDadoEncontrado.setCliente(clienteDado.getCliente());
		clienteDadoEncontrado.setDadoTipo(clienteDado.getDadoTipo());		
		return repository.save(clienteDadoEncontrado);
	}
	
	public void excluir(Integer id) {
		repository.deleteById(id);
	}
	
	public List<ClienteDado> listaDadosCliente(Cliente cliente) {
		return repository.findByCliente(cliente);
	}
	
	public void excluirByCliente(Cliente cliente) {
		repository.deleteAll(listaDadosCliente(cliente));
	}
	
	public List<ClienteDado> listaClienteDados() {
		return repository.findAll();
	}

}
