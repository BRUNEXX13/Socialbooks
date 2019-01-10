package com.bss.socialbooks.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.bss.socialbooks.domain.Comentario;
import com.bss.socialbooks.domain.Livro;
import com.bss.socialbooks.repository.ComentariosRepository;
import com.bss.socialbooks.repository.LivrosRepository;
import com.bss.socialbooks.service.exceptions.LivroNaoEncontradoException;

// Camada de Serviço acessa Repository /// / Separar as camadas // 
@Service
public class LivrosService {

	@Autowired
	private LivrosRepository livrosRepository;

	@Autowired
	private ComentariosRepository comentarioRepository;

	public List<Livro> listar() {
		return livrosRepository.findAll();
	}

	public Livro buscar(Long id) {
		Livro livro = livrosRepository.findOne(id);

		if (livro == null) {
			throw new LivroNaoEncontradoException("O livro não pode ser encontrado");
		}

		return livro;
	}

	public Livro salvar(Livro livro) {
		livro.setId(null);
		return livrosRepository.save(livro);

	}

	public void deletar(Long id) {
		try {
			livrosRepository.delete(id);
		} catch (EmptyResultDataAccessException e) {
			throw new LivroNaoEncontradoException("O livro nao pode ser foi encontrado");
		}

	}

	// Verifico a Existencia e depois Atualiza
	public void atualizar(Livro livro) {
		verificarExistencia(livro);
		livrosRepository.save(livro);

	}

	// Verifica a Existencia de um livro
	private void verificarExistencia(Livro livro) {
		buscar(livro.getId());
	}

	public Comentario salvarComentario(Long livroId, Comentario comentario) {
		Livro livro = buscar(livroId);
		comentario.setLivro(livro);
		comentario.setData(new Date());

		return comentarioRepository.save(comentario);

	}

	public List<Comentario> listarComentario(Long livroId) {
		Livro livro = buscar(livroId);
		return livro.getComentarios();

	}
}
