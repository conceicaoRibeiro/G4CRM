package br.com.senac.g4crm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.g4crm.domain.Acao;
import br.com.senac.g4crm.domain.AcaoUsuarioCliente;
import br.com.senac.g4crm.domain.AcaoUsuarioClienteOferta;
import br.com.senac.g4crm.domain.Cliente;
import br.com.senac.g4crm.repository.AcaoRepository;
import br.com.senac.g4crm.repository.AcaoUsuarioClienteOfertaRepository;
import br.com.senac.g4crm.repository.AcaoUsuarioClienteRepository;
import br.com.senac.g4crm.repository.UsuarioRepository;
import br.com.senac.g4crm.service.exception.ObjectNotFoundException;

@Service
public class AcaoUsuarioClienteService {
	
	@Autowired
	private AcaoUsuarioClienteRepository  acaoUsuarioClienteRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AcaoUsuarioClienteOfertaRepository acaoUsuarioClienteOfertaRepository;
	
	@Autowired
	private AcaoRepository acaoRepository;
	
	public AcaoUsuarioCliente buscar(Integer id) {
		Optional<AcaoUsuarioCliente> acaoUsuario = acaoUsuarioClienteRepository.findById(id);
		return acaoUsuario.orElseThrow(() -> new ObjectNotFoundException("Ação do usuário não encontrado! Id: "+id+", Tipo: "+ AcaoUsuarioCliente.class.getName()));
	}
	
	public AcaoUsuarioCliente salvar(AcaoUsuarioCliente acaoUsuarioCliente) {
		acaoUsuarioCliente.setUsuario(usuarioRepository.findById(1).get());
		acaoUsuarioCliente.setData(new Date());
		return acaoUsuarioClienteRepository.save(acaoUsuarioCliente);
	}
	
	public void excluir(Integer id) {
		acaoUsuarioClienteRepository.deleteById(id);
	}
	
	public List<AcaoUsuarioCliente> listAll() {
		return acaoUsuarioClienteRepository.findAll();
	}
	
	public List<AcaoUsuarioCliente> findByCliente(Cliente cliente) {
		return acaoUsuarioClienteRepository.findByCliente(cliente);
	}
	
	public Map<String,String> listaTotalAcoesRegistradas() {
		List<AcaoUsuarioClienteOferta> acoesUsuarioClienteOferta = acaoUsuarioClienteOfertaRepository.findAll();
		List<AcaoUsuarioCliente> acoesUsuarioCliente = acaoUsuarioClienteRepository.findAll();
		List<Acao> acoes = acaoRepository.findAll();
		acoes = acoes.stream().filter(a -> !"Mudança de etapa no funil de vendas".equals(a.getDescricao())).collect(Collectors.toList());
		Map<String,String> retorno = new HashMap<>();
		long total;
		for(Acao acao:acoes) {
			total = 0;
			total=acoesUsuarioCliente.stream().filter(a -> a.getAcao().equals(acao)).count();
			total+=acoesUsuarioClienteOferta.stream().filter(a -> a.getAcao().equals(acao)).count();
			retorno.put(acao.getDescricao(), String.valueOf(total));
		}
		return retorno;
	}
	
}
