package br.com.senac.g4crm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.g4crm.domain.NivelInstrucao;
import br.com.senac.g4crm.repository.NivelInstrucaoRepository;
import br.com.senac.g4crm.service.exception.ObjectNotFoundException;

@Service
public class NivelInstrucaoService {

	@Autowired
	private NivelInstrucaoRepository repository;
	
	public NivelInstrucao buscar(Integer id) {
		Optional<NivelInstrucao> nivelInstrucaoEncontrado = repository.findById(id);
		return nivelInstrucaoEncontrado.orElseThrow(() -> new ObjectNotFoundException("NivelInstrucao não encontrado! Id: "+id+", Tipo: "+NivelInstrucao.class.getName()));
	}
	
	public NivelInstrucao salvar(NivelInstrucao nivelInstrucao) {
		return repository.save(nivelInstrucao);
	}
	
	public void excluir(Integer id) {
		repository.deleteById(id);
	}
	
	public List<NivelInstrucao> listAll() {
		return repository.findAll();
	}
}
