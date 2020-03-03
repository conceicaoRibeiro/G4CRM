package br.com.senac.g4crm.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.g4crm.domain.AcaoUsuarioClienteOferta;
import br.com.senac.g4crm.domain.ClienteOferta;
import br.com.senac.g4crm.domain.FunilEtapa;
import br.com.senac.g4crm.repository.AcaoUsuarioClienteOfertaRepository;
import br.com.senac.g4crm.repository.FunilEtapaRepository;
import br.com.senac.g4crm.repository.UsuarioRepository;
import br.com.senac.g4crm.service.exception.ObjectNotFoundException;

@Service
public class AcaoUsuarioClienteOfertaService {
	
	@Autowired
	private AcaoUsuarioClienteOfertaRepository  repository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private FunilEtapaRepository funilEtapaRepository;
	
	public AcaoUsuarioClienteOferta buscar(Integer id) {
		Optional<AcaoUsuarioClienteOferta> acaoUsuario = repository.findById(id);
		return acaoUsuario.orElseThrow(() -> new ObjectNotFoundException("Ação do usuário não encontrado! Id: "+id+", Tipo: "+ AcaoUsuarioClienteOferta.class.getName()));
	}
	
	public AcaoUsuarioClienteOferta salvar(AcaoUsuarioClienteOferta acaoUsuarioClienteOferta) {
		acaoUsuarioClienteOferta.setUsuario(usuarioRepository.findById(1).get());
		acaoUsuarioClienteOferta.setDataAcao(new Date());
		return repository.save(acaoUsuarioClienteOferta);
	}
	
	public void excluir(Integer id) {
		repository.deleteById(id);
	}
	
	public List<AcaoUsuarioClienteOferta> listAll() {
		return repository.findAll();
	}
	
	public List<AcaoUsuarioClienteOferta> findByClienteOferta(ClienteOferta clienteOferta) {
		return repository.findByClienteOferta(clienteOferta);
	}
	
	public List<AcaoUsuarioClienteOferta> listByPeriodo(String dataInicial, String dataFinal) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
		if(dataInicial.isEmpty()) {
			dataInicial = sdf.format(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		} else {
			dataInicial+=" 00:00:00";
		}
		if(dataFinal.isEmpty()) {
			dataFinal = sdf.format(Date.from(LocalDate.now().atTime(23, 59).atZone(ZoneId.systemDefault()).toInstant()));
		} else {
			dataFinal+=" 23:59:59";
		}
		List<AcaoUsuarioClienteOferta> acoes = new ArrayList<>();
		try {
			acoes = repository.findAllByDataAcaoBetween(sdf.parse(dataInicial), sdf.parse(dataFinal));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		FunilEtapa etapa = funilEtapaRepository.findTopByOrderByFunilEtapaIdDesc();
		return acoes.stream().filter(a -> a.getClienteOferta().getFunilEtapa().equals(etapa)).filter(a -> a.getDescricao().equals(etapa.getDescricao())).collect(Collectors.toList());
	}

}
