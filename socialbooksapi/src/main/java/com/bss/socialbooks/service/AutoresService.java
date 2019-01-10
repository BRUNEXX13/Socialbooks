package com.bss.socialbooks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bss.socialbooks.domain.Autor;
import com.bss.socialbooks.repository.AutorRepository;
import com.bss.socialbooks.service.exceptions.AutorNaoEncontradoException;
import com.bss.socialbooks.service.exceptions.AutoresExistenteException;

@Service
public class AutoresService {

	@Autowired
	private AutorRepository autoresRepository;

	public List<Autor> listar() {
		return (List<Autor>) autoresRepository.findAll();
	}

	// Salvando um Autor
	public Autor salvar(Autor autor) {
		if (autor.getId() != null) {

			Autor a = autoresRepository.findOne(autor.getId());

			if (a != null) {
				throw new AutoresExistenteException("O autor já existe");
			}
		}
		return autoresRepository.save(autor);

	}

	// Listando um autor
	public Autor buscar(Long id) {
		Autor autor = autoresRepository.findOne(id);

		if (autor == null) {
			throw new AutorNaoEncontradoException("O autor não pode  ser encontrado	");
		}
		return autor;
	}
}
