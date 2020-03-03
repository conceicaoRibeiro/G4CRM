package br.com.senac.g4crm.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.com.senac.g4crm.domain.Acao;
import br.com.senac.g4crm.domain.CategoriaDado;
import br.com.senac.g4crm.domain.Cliente;
import br.com.senac.g4crm.domain.ClienteOferta;
import br.com.senac.g4crm.domain.ClienteOfertaId;
import br.com.senac.g4crm.domain.DadoTipo;
import br.com.senac.g4crm.domain.FunilEtapa;
import br.com.senac.g4crm.domain.NivelInstrucao;
import br.com.senac.g4crm.domain.Oferta;
import br.com.senac.g4crm.domain.Produto;
import br.com.senac.g4crm.domain.Usuario;
import br.com.senac.g4crm.repository.AcaoRepository;
import br.com.senac.g4crm.repository.CategoriaDadoRepository;
import br.com.senac.g4crm.repository.ClienteOfertaRepository;
import br.com.senac.g4crm.repository.ClienteRepository;
import br.com.senac.g4crm.repository.DadoTipoRepository;
import br.com.senac.g4crm.repository.FunilEtapaRepository;
import br.com.senac.g4crm.repository.NivelInstrucaoRepository;
import br.com.senac.g4crm.repository.OfertaRepository;
import br.com.senac.g4crm.repository.ProdutoRepository;
import br.com.senac.g4crm.repository.UsuarioRepository;

@Component
public class Init implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	CategoriaDadoRepository categoriaDadoRepository;
	
	@Autowired
	DadoTipoRepository dadoTipoRepository;
	
	@Autowired
	NivelInstrucaoRepository nivelInstrucaoRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	OfertaRepository ofertaRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	AcaoRepository acaoRepository;
	
	@Autowired
	FunilEtapaRepository funilEtapaRepository;
	
	@Autowired
	ClienteOfertaRepository clienteOfertaRepository;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// gerando massa de dados para teste...
		CategoriaDado categoriaDado = new CategoriaDado();
		categoriaDado.setDescricao("Dados Pessoais");
		categoriaDado.setStatus("A");
		categoriaDadoRepository.save(categoriaDado);
		
		categoriaDado = new CategoriaDado();
		categoriaDado.setDescricao("Dados Profissionais");
		categoriaDado.setStatus("A");
		categoriaDadoRepository.save(categoriaDado);
		
		DadoTipo dadoTipo = new DadoTipo();
		dadoTipo.setCategoriaDado(categoriaDadoRepository.findById(1).get());
		dadoTipo.setDescricao("Passaporte");
		dadoTipo.setStatus("A");
		dadoTipoRepository.save(dadoTipo);
		
		dadoTipo = new DadoTipo();
		dadoTipo.setCategoriaDado(categoriaDadoRepository.findById(1).get());
		dadoTipo.setDescricao("Telefone");
		dadoTipo.setStatus("A");
		dadoTipoRepository.save(dadoTipo);
		
		dadoTipo = new DadoTipo();
		dadoTipo.setCategoriaDado(categoriaDadoRepository.findById(2).get());
		dadoTipo.setDescricao("Título de Eleitor");
		dadoTipo.setStatus("A");
		dadoTipoRepository.save(dadoTipo);
		
		dadoTipo = new DadoTipo();
		dadoTipo.setCategoriaDado(categoriaDadoRepository.findById(2).get());
		dadoTipo.setDescricao("PIS");
		dadoTipo.setStatus("A");
		dadoTipoRepository.save(dadoTipo);
		
		NivelInstrucao nivelInstrucao = new NivelInstrucao();
		nivelInstrucao.setDescricao("Nível Médio Técnico");
		nivelInstrucao.setStatus("A");
		nivelInstrucaoRepository.save(nivelInstrucao);
		
		nivelInstrucao = new NivelInstrucao();
		nivelInstrucao.setDescricao("Curso Livre");
		nivelInstrucao.setStatus("A");
		nivelInstrucaoRepository.save(nivelInstrucao);
		
		nivelInstrucao = new NivelInstrucao();
		nivelInstrucao.setDescricao("Nível Superior");
		nivelInstrucao.setStatus("A");
		nivelInstrucaoRepository.save(nivelInstrucao);
		
		List<Produto> produtos = new ArrayList<>();
		Produto produto = new Produto();
		produto.setNivelInstrucao(nivelInstrucaoRepository.findById(3).get());
		produto.setDescricao("Análise e Desenvolvimento de Sistemas");
		produto.setStatus("A");
		produtos.add(produto);
		produto = new Produto();
		produto.setNivelInstrucao(nivelInstrucaoRepository.findById(2).get());
		produto.setDescricao("Excel Iniciante");
		produto.setStatus("A");
		produtos.add(produto);
		produto = new Produto();
		produto.setNivelInstrucao(nivelInstrucaoRepository.findById(2).get());
		produto.setDescricao("Excel Avançado");
		produto.setStatus("A");
		produtos.add(produto);
		produto = new Produto();
		produto.setNivelInstrucao(nivelInstrucaoRepository.findById(1).get());
		produto.setDescricao("Técnico em informática");
		produto.setStatus("A");
		produtos.add(produto);
		produto = new Produto();
		produto.setNivelInstrucao(nivelInstrucaoRepository.findById(1).get());
		produto.setDescricao("Técnico de redes");
		produto.setStatus("A");
		produtos.add(produto);
		produto = new Produto();
		produto.setNivelInstrucao(nivelInstrucaoRepository.findById(1).get());
		produto.setDescricao("Técnico de manutenção de computadores");
		produto.setStatus("A");
		produtos.add(produto);
		produtoRepository.saveAll(produtos);
		
		List<Oferta> ofertas = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Oferta oferta = new Oferta();
		oferta.setProduto(produtos.get(0));
		oferta.setPreco(20970.00);
		try {
			oferta.setDataInicio(sdf.parse("01/07/2017"));
			oferta.setDataFim(sdf.parse("31/12/2019"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		oferta.setDescricao("Graduação ADS padrão");
		oferta.setStatus("A");
		ofertas.add(oferta);
		oferta = new Oferta();
		oferta.setProduto(produtos.get(0));
		oferta.setPreco(20970.00);
		try {
			oferta.setDataInicio(sdf.parse("01/07/2017"));
			oferta.setDataFim(sdf.parse("31/12/2019"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		oferta.setDescricao("Graduação ADS Noturno");
		oferta.setStatus("A");
		ofertas.add(oferta);
		oferta = new Oferta();
		oferta.setProduto(produtos.get(0));
		oferta.setPreco(20970.00);
		try {
			oferta.setDataInicio(sdf.parse("01/07/2017"));
			oferta.setDataFim(sdf.parse("31/12/2019"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		oferta.setDescricao("Graduação ADS EAD");
		oferta.setStatus("A");
		ofertas.add(oferta);
		oferta = new Oferta();
		oferta.setProduto(produtos.get(1));
		oferta.setPreco(1200.00);
		try {
			oferta.setDataInicio(sdf.parse("01/07/2019"));
			oferta.setDataFim(sdf.parse("30/11/2019"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		oferta.setDescricao("Excel Iniciantes Turma noturna");
		oferta.setStatus("A");
		ofertas.add(oferta);
		oferta = new Oferta();
		oferta.setProduto(produtos.get(2));
		oferta.setPreco(3700.00);
		try {
			oferta.setDataInicio(sdf.parse("01/07/2017"));
			oferta.setDataFim(sdf.parse("31/12/2019"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		oferta.setDescricao("Excel Avançado turma diurna");
		oferta.setStatus("A");
		ofertas.add(oferta);
		ofertaRepository.saveAll(ofertas);
		
		
		Usuario usuario = new Usuario();
		usuario.setNome("Usuário Teste");
		usuario.setLogin("user");
		usuario.setCargo("atendente");
		usuario.setStatus("A");
		usuarioRepository.save(usuario);
		
		List<Cliente> clientes = new ArrayList<>();
		Cliente cliente = new Cliente();
		cliente.setCpf("678.856.236-96");
		cliente.setEmail("mmalualicemariacaldeira@br.festo.com");
		cliente.setNome("Malu");
		cliente.setSobrenome("Alice Maria Caldeira");
		cliente.setStatus("A");
		clientes.add(cliente);
		
		cliente = new Cliente();
		cliente.setCpf("702.370.739-51");
		cliente.setEmail("kkaiqueaugustobaptista@tglaw.com.br");
		cliente.setNome("Kaique");
		cliente.setSobrenome("Augusto Baptista");
		cliente.setStatus("A");
		clientes.add(cliente);
		
		cliente = new Cliente();
		cliente.setCpf("102.667.709-24");
		cliente.setEmail("jessicamanuelapires-96@djapan.com.br");
		cliente.setNome("Jéssica");
		cliente.setSobrenome("Manuela Pires");
		cliente.setStatus("A");
		clientes.add(cliente);
		
		cliente = new Cliente();
		cliente.setCpf("963.688.217-79");
		cliente.setEmail("isadorasueliluciacosta_@sfreitasadvogados.com.br");
		cliente.setNome("Isadora");
		cliente.setSobrenome("Sueli Lúcia Costa");
		cliente.setStatus("A");
		clientes.add(cliente);
		
		cliente = new Cliente();
		cliente.setCpf("245.855.793-70");
		cliente.setEmail("mirellanataliamoura-78@vmetaiscba.com.br");
		cliente.setNome("Mirella");
		cliente.setSobrenome("Natália Moura");
		cliente.setStatus("A");
		clientes.add(cliente);
		
		cliente = new Cliente();
		cliente.setCpf("173.798.696-55");
		cliente.setEmail("franciscokevinluanalmada@ticem.com.br");
		cliente.setNome("Francisco");
		cliente.setSobrenome("Kevin Luan Almada");
		cliente.setStatus("A");
		clientes.add(cliente);
		
		cliente = new Cliente();
		cliente.setCpf("018.242.748-07");
		cliente.setEmail("franciscokevinluanalmada@ticem.com.br");
		cliente.setNome("Vanessa");
		cliente.setSobrenome("Ester Souza");
		cliente.setStatus("A");
		clientes.add(cliente);
		
		cliente = new Cliente();
		cliente.setCpf("313.604.045-70");
		cliente.setEmail("viniciusarthursouza-78@jonasmartinez.com");
		cliente.setNome("Vinicius");
		cliente.setSobrenome("Arthur Souza");
		cliente.setStatus("A");
		clientes.add(cliente);
		
		cliente = new Cliente();
		cliente.setCpf("939.771.556-95");
		cliente.setEmail("ggeraldojoaosales@fredericodiniz.com");
		cliente.setNome("Geraldo");
		cliente.setSobrenome("João Sales");
		cliente.setStatus("A");
		clientes.add(cliente);
		
		cliente = new Cliente();
		cliente.setCpf("367.928.029-72");
		cliente.setEmail("fernandataniaantoniagalvao-71@termaqui.com.br");
		cliente.setNome("Fernanda");
		cliente.setSobrenome("Tânia Antônia Galvão");
		cliente.setStatus("A");
		clientes.add(cliente);
		clienteRepository.saveAll(clientes);
		
		List<Acao> acoes = new ArrayList<>();
		Acao acao = new Acao();
		acao.setDescricao("Mudança de etapa no funil de vendas");
		acao.setStatus("A");
		acoes.add(acao);
		acao = new Acao();
		acao.setDescricao("Alteração de valor da oferta");
		acao.setStatus("A");
		acoes.add(acao);
		acao = new Acao();
		acao.setDescricao("Envio de e-mail");
		acao.setStatus("A");
		acoes.add(acao);
		acao = new Acao();
		acao.setDescricao("Atendimento presencial");
		acao.setStatus("A");
		acoes.add(acao);
		acao = new Acao();
		acao.setDescricao("Contato Telefônico");
		acao.setStatus("A");
		acoes.add(acao);
		acaoRepository.saveAll(acoes);
		
		List<FunilEtapa> funilEtapas = new ArrayList<>();
		FunilEtapa funilEtapa = new FunilEtapa();
		funilEtapa.setDescricao("Visitante");
		funilEtapa.setStatus("A");
		funilEtapas.add(funilEtapa);
		funilEtapa = new FunilEtapa();
		funilEtapa.setDescricao("Lead");
		funilEtapa.setStatus("A");
		funilEtapas.add(funilEtapa);
		funilEtapa = new FunilEtapa();
		funilEtapa.setDescricao("Oportunidade");
		funilEtapa.setStatus("A");
		funilEtapas.add(funilEtapa);
		funilEtapa = new FunilEtapa();
		funilEtapa.setDescricao("Cliente");
		funilEtapa.setStatus("A");
		funilEtapas.add(funilEtapa);
		funilEtapaRepository.saveAll(funilEtapas);
		
		List<ClienteOferta> clientesOferta = new ArrayList<>();
		ClienteOferta clienteOferta = new ClienteOferta();
		ClienteOfertaId clienteOfertaId = new ClienteOfertaId();
		clienteOfertaId.setCliente(clientes.get(1));
		clienteOfertaId.setOferta(ofertas.get(1));
		clienteOferta.setClienteOfertaId(clienteOfertaId);
		clienteOferta.setDescricao(ofertas.get(1).getDescricao());
		clienteOferta.setFunilEtapa(funilEtapas.get(0));
		clienteOferta.setPreco(1485.00);
		clienteOferta.setStatus("A");
		clientesOferta.add(clienteOferta);
		
		clienteOferta = new ClienteOferta();
		clienteOfertaId = new ClienteOfertaId();
		clienteOfertaId.setCliente(clientes.get(0));
		clienteOfertaId.setOferta(ofertas.get(1));
		clienteOferta.setClienteOfertaId(clienteOfertaId);
		clienteOferta.setDescricao(ofertas.get(1).getDescricao());
		clienteOferta.setFunilEtapa(funilEtapas.get(0));
		clienteOferta.setPreco(1485.00);
		clienteOferta.setStatus("A");
		clienteOferta.setFunilEtapa(funilEtapas.get(0));
		clientesOferta.add(clienteOferta);
		
		clienteOferta = new ClienteOferta();
		clienteOfertaId = new ClienteOfertaId();
		clienteOfertaId.setCliente(clientes.get(2));
		clienteOfertaId.setOferta(ofertas.get(0));
		clienteOferta.setClienteOfertaId(clienteOfertaId);
		clienteOferta.setDescricao(ofertas.get(0).getDescricao());
		clienteOferta.setFunilEtapa(funilEtapas.get(0));
		clienteOferta.setPreco(1654.00);
		clienteOferta.setStatus("A");
		clienteOferta.setFunilEtapa(funilEtapas.get(0));
		clientesOferta.add(clienteOferta);
		
		clienteOferta = new ClienteOferta();
		clienteOfertaId = new ClienteOfertaId();
		clienteOfertaId.setCliente(clientes.get(3));
		clienteOfertaId.setOferta(ofertas.get(3));
		clienteOferta.setClienteOfertaId(clienteOfertaId);
		clienteOferta.setDescricao(ofertas.get(3).getDescricao());
		clienteOferta.setFunilEtapa(funilEtapas.get(0));
		clienteOferta.setPreco(1789.00);
		clienteOferta.setStatus("A");
		clienteOferta.setFunilEtapa(funilEtapas.get(0));
		clientesOferta.add(clienteOferta);
		
		clienteOfertaRepository.saveAll(clientesOferta);
		
	}

}
