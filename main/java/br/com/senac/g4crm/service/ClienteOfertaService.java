package br.com.senac.g4crm.service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.g4crm.domain.AcaoUsuarioClienteOferta;
import br.com.senac.g4crm.domain.ClienteOferta;
import br.com.senac.g4crm.domain.ClienteOfertaId;
import br.com.senac.g4crm.domain.Oferta;
import br.com.senac.g4crm.repository.AcaoRepository;
import br.com.senac.g4crm.repository.ClienteOfertaRepository;
import br.com.senac.g4crm.repository.ClienteRepository;
import br.com.senac.g4crm.repository.FunilEtapaRepository;
import br.com.senac.g4crm.repository.OfertaRepository;

@Service
public class ClienteOfertaService {

	@Autowired
	ClienteOfertaRepository repository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	OfertaRepository ofertaRepository;
	
	@Autowired
	FunilEtapaRepository funilEtapaRepository;
	
	@Autowired
	AcaoRepository acaoRepository;
	
	@Autowired
	AcaoUsuarioClienteOfertaService acaoUsuarioClienteOfertaService;
	
	public ClienteOferta salvar(ClienteOferta clienteOferta) {
		clienteOferta.getClienteOfertaId().setCliente(clienteRepository.findById(clienteOferta.getClienteOfertaId().getCliente().getClienteId()).get());
		clienteOferta.getClienteOfertaId().setOferta(ofertaRepository.findById(clienteOferta.getClienteOfertaId().getOferta().getOfertaId()).get());
		Optional<ClienteOferta> co = repository.findById(clienteOferta.getClienteOfertaId());
		if(!co.isPresent()) {
			clienteOferta.setFunilEtapa(funilEtapaRepository.findTopByOrderByFunilEtapaIdAsc());
			return repository.save(clienteOferta);
		}
		if(clienteOferta.getPreco()!=co.get().getPreco() && clienteOferta.getPreco()<clienteOferta.getClienteOfertaId().getOferta().getPreco()) {
			co.get().setPreco(clienteOferta.getPreco());
		}
		return repository.save(co.get());
	}
	
	public ClienteOferta atualizar(ClienteOferta clienteOferta) {
		AcaoUsuarioClienteOferta acao = new AcaoUsuarioClienteOferta();
		acao.setClienteOferta(clienteOferta);
		acao.setAcao(acaoRepository.findById(1).get());
		if(clienteOferta.getFunilEtapa()!=null) {
			acao.setDescricao(clienteOferta.getFunilEtapa().getDescricao());
		} else {
			acao.setDescricao("");
		}
		acaoUsuarioClienteOfertaService.salvar(acao);
		return repository.save(clienteOferta);
	}
	
	public List<ClienteOferta> listarTodos() {
		return repository.findAll();
	}
	
	public Map<String, Map<String, List<ClienteOferta>>> listaPorFunilEtapa() {
		List<ClienteOferta> clientesOferta = repository.findAll();
		Map<String, Map<String, List<ClienteOferta>>> clientes = clientesOferta.stream().collect(Collectors.groupingBy(ClienteOferta::getDescricao, Collectors.groupingBy(ClienteOferta::getFunilEtapaDescricao)));
		return clientes;		
	}
	
	public ClienteOferta buscar(ClienteOfertaId clienteofertaId) {
		try{
			return repository.findById(clienteofertaId).get();
		} catch(NoSuchElementException e) {
			return null;
		}
	}
	
	public List<ClienteOferta> listaPorOferta(Oferta oferta) {
		return repository.findByIdOferta(oferta);
	}
}
