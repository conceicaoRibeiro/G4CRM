package br.com.senac.g4crm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.g4crm.domain.Usuario;
import br.com.senac.g4crm.repository.UsuarioRepository;
import br.com.senac.g4crm.service.exception.ObjectNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario buscar(Integer id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		return usuario.orElseThrow(() -> new ObjectNotFoundException(
				"Usuario não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
	}

	public Usuario inserir(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	public Usuario alterar(Usuario usuario) throws ObjectNotFoundException {
		Usuario usuAlterado = buscar(usuario.getUsuarioId());
		usuAlterado.setNome(usuario.getNome());
		usuAlterado.setLogin(usuario.getLogin());
		usuAlterado.setSenha(usuario.getSenha());
		usuAlterado.setStatus(usuario.getStatus());
		usuAlterado.setCargo(usuario.getCargo());
		return usuarioRepository.save(usuAlterado);
	}

	public void excluir(Integer id) {
		usuarioRepository.deleteById(id);
	}

	public List<Usuario> listAll() {
		return usuarioRepository.findAll();
	}

}
