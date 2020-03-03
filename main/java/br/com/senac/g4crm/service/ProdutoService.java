package br.com.senac.g4crm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.g4crm.domain.Produto;
import br.com.senac.g4crm.repository.ProdutoRepository;
import br.com.senac.g4crm.service.exception.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;
	
	public Produto buscar(Integer id) {
		Optional<Produto> produtoEncontrado = repository.findById(id);
		return produtoEncontrado.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado! Id: "+id+", Tipo: "+Produto.class.getName()));
	}
	
	public Produto salvar(Produto produto) {
		return repository.save(produto);
	}
	
	public Produto alterar(Produto produto) throws ObjectNotFoundException {
		Produto produtoEncontrado = buscar(produto.getProdutoId());
		produtoEncontrado.setDescricao(produto.getDescricao());
		produtoEncontrado.setStatus(produto.getStatus());
		produtoEncontrado.setNivelInstrucao(produto.getNivelInstrucao());
		return repository.save(produtoEncontrado);
	}
	
	public void excluir(Integer id) {
		repository.deleteById(id);
	}
	
	public List<Produto> listAll() {
		return repository.findAll();
	}
}
