package br.com.senac.g4crm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.g4crm.domain.CategoriaDado;
import br.com.senac.g4crm.repository.CategoriaDadoRepository;
import br.com.senac.g4crm.service.exception.ObjectNotFoundException;

@Service
public class CategoriaDadoService {
	
	@Autowired
	private CategoriaDadoRepository repository;
	
	public CategoriaDado buscar(Integer id) {
		Optional<CategoriaDado> categoriaDadoEncontrado = repository.findById(id);
		return categoriaDadoEncontrado.orElseThrow(() -> new ObjectNotFoundException("CategoriaDado não encontrado! Id: "+id+", Tipo: "+CategoriaDado.class.getName()));
	}
	
	public CategoriaDado inserir(CategoriaDado categoriaDado) {
		categoriaDado.setCategoriaDadoId(null);
		return repository.save(categoriaDado);
	}
	
	public CategoriaDado alterar(CategoriaDado categoriaDado) throws ObjectNotFoundException {
		CategoriaDado categoriaDadoEncontrado = buscar(categoriaDado.getCategoriaDadoId());
		categoriaDadoEncontrado.setDescricao(categoriaDado.getDescricao());
		categoriaDadoEncontrado.setStatus(categoriaDado.getStatus());
		return repository.save(categoriaDadoEncontrado);
	}
	
	public void excluir(Integer id) {
		repository.deleteById(id);
	}
	
	public List<CategoriaDado> listaNivelInstrucaos() {
		return repository.findAll();
	}

}
