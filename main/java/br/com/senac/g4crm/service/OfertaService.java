package br.com.senac.g4crm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.g4crm.domain.Oferta;
import br.com.senac.g4crm.repository.OfertaRepository;
import br.com.senac.g4crm.service.exception.ObjectNotFoundException;

@Service
public class OfertaService {

	@Autowired
	private OfertaRepository repository;
	
	public Oferta buscar(Integer id) {
		Optional<Oferta> ofertaEncontrada = repository.findById(id);
		return ofertaEncontrada.orElseThrow(() -> new ObjectNotFoundException("Oferta não encontrado! Id: "+id+", Tipo: "+Oferta.class.getName()));
	}
	
	public Oferta salvar(Oferta oferta) {
		return repository.save(oferta);
	}
	
	public void excluir(Integer id) {
		repository.deleteById(id);
	}
	
	public List<Oferta> listAll() {
		return repository.findAll();
	}
}
