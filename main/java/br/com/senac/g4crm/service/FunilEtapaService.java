package br.com.senac.g4crm.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.g4crm.domain.FunilEtapa;
import br.com.senac.g4crm.repository.FunilEtapaRepository;
import br.com.senac.g4crm.service.exception.ObjectNotFoundException;

@Service
public class FunilEtapaService {

	@Autowired
	FunilEtapaRepository funilRepository;

	public FunilEtapa buscar(Integer id) {
		Optional<FunilEtapa> funilEtapa = funilRepository.findById(id);
		return funilEtapa.orElseThrow(() -> new ObjectNotFoundException(
				"Etapa do Funil não encontrada! Id: " + id + ", Tipo: " + FunilEtapa.class.getName()));
	}

	public FunilEtapa inserir(FunilEtapa funil) {
		return funilRepository.save(funil);
	}

	public FunilEtapa alterar(FunilEtapa funilEtapa) throws ObjectNotFoundException {
		FunilEtapa funil = buscar(funilEtapa.getFunilEtapaId());
		funil.setDescricao(funilEtapa.getDescricao());
		funil.setStatus(funilEtapa.getStatus());
		return funilRepository.save(funil);
	}

	public void excluir(Integer id) {
		funilRepository.deleteById(id);
	}

	public List<FunilEtapa> listAll() {
		return funilRepository.findAll().stream().filter(a -> !"Mudança de etapa no funil de vendas".equals(a.getDescricao())).collect(Collectors.toList());
	}

}
