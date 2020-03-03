package br.com.senac.g4crm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.g4crm.domain.Acao;
import br.com.senac.g4crm.repository.AcaoRepository;
import br.com.senac.g4crm.service.exception.ObjectNotFoundException;

@Service
public class AcaoService {

	@Autowired
	private AcaoRepository acaoRepository;

	public Acao buscar(Integer id) {
		Optional<Acao> objAcao = acaoRepository.findById(id);
		return objAcao.orElseThrow(() -> new ObjectNotFoundException(
				"Ação não encontrada! Id: " + id + ", Tipo: " + Acao.class.getName()));
	}

	public Acao inserir(Acao acao) {
		return acaoRepository.save(acao);
	}

	public Acao alterar(Acao acao) throws ObjectNotFoundException {
		Acao acaoAlterada = buscar(acao.getAcaoId());
		acaoAlterada.setDescricao(acao.getDescricao());
		acaoAlterada.setStatus(acao.getStatus());
		return acaoRepository.save(acaoAlterada);
	}
	
	public void excluir(Integer id){
		acaoRepository.deleteById(id);
	}
	
	public List<Acao> listAll() {
		return acaoRepository.findAll();
	}

}
