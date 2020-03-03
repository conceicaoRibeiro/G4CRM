package br.com.senac.g4crm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.g4crm.domain.DadoTipo;
import br.com.senac.g4crm.repository.DadoTipoRepository;
import br.com.senac.g4crm.service.exception.ObjectNotFoundException;

@Service
public class DadoTipoService {

	@Autowired
	private DadoTipoRepository repository;
	
	public DadoTipo buscar(Integer id) {
		Optional<DadoTipo> dadoTipoEncontrado = repository.findById(id);
		return dadoTipoEncontrado.orElseThrow(() -> new ObjectNotFoundException("DadoTipo não encontrado! Id: "+id+", Tipo: "+DadoTipo.class.getName()));
	}
	
	public DadoTipo inserir(DadoTipo dadoTipo) {
		dadoTipo.setDadoTipoId(null);
		return repository.save(dadoTipo);
	}
	
	public void inserir(List<DadoTipo> dados) {
		repository.saveAll(dados);
	}
	
	public DadoTipo alterar(DadoTipo dadoTipo) throws ObjectNotFoundException {
		DadoTipo dadoTipoEncontrado = buscar(dadoTipo.getDadoTipoId());
		dadoTipoEncontrado.setDescricao(dadoTipo.getDescricao());
		dadoTipoEncontrado.setStatus(dadoTipo.getStatus());
		dadoTipoEncontrado.setCategoriaDado(dadoTipo.getCategoriaDado());
		dadoTipoEncontrado.setObrigatorio(dadoTipo.getObrigatorio());
		dadoTipoEncontrado.setPadrao(dadoTipo.getPadrao());
		dadoTipoEncontrado.setMascara(dadoTipo.getMascara());
		return repository.save(dadoTipoEncontrado);
	}
	
	public void excluir(Integer id) {
		repository.deleteById(id);
	}
	
	public List<DadoTipo> listaDadoTipo() {
		return repository.findAll();
	}

}
