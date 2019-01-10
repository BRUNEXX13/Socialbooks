package com.bss.socialbooks.resources;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bss.socialbooks.domain.Comentario;
import com.bss.socialbooks.domain.Livro;
import com.bss.socialbooks.service.LivrosService;

@RestController //
@RequestMapping(value = "/livros") // Executando = http://localhost:8080/livros
public class LivrosResource {

	@Autowired
	private LivrosService livrosService;

	// GET obtem Recursos
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Livro>> listar() {

		// Fazendo o tratamento de Mensagens dos códigos
		return ResponseEntity.status(HttpStatus.OK).body(livrosService.listar());

	}

	// Post - Metodo para Criacao de Recursos
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> salvar(@Valid @RequestBody Livro livro) {
		livro = livrosService.salvar(livro);

		// Retornando a URI onde o recurso pode ser localizado = Location
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(livro.getId()).toUri();

		// Retorno da URI
		return ResponseEntity.created(uri).build();
	}

	// Fazendo a Busca atraves de um id Especifico //
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
		Livro livro = livrosService.buscar(id);

		// Tempo de Inforomacao Valida no nosso Listar por ID 
		CacheControl cacheControl = CacheControl.maxAge(20, TimeUnit.SECONDS);

		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(livro);
	}

	// Deletando um Livro by id
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
		livrosService.deletar(id);
		return ResponseEntity.noContent().build();

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@RequestBody Livro livro, @PathVariable("id") Long id) {
		livro.setId(id);
		livrosService.atualizar(livro);
		return ResponseEntity.noContent().build();
		// Tratando o metodo caso tenta atualizar um valor que nao existe.

	}

	@RequestMapping(value = "/{id}/comentarios", method = RequestMethod.POST)
	public ResponseEntity<Void> adicionarComentario(@PathVariable("id") Long livroId,
			@RequestBody Comentario comentario) {
		
		//Captura o Usuario e o contexto atual de seguranca
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		//Capturando o Usuário quando ele fizer um comentário.
		comentario.setUsuario(auth.getName());

		livrosService.salvarComentario(livroId, comentario);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

		// Retorno da URI
		return ResponseEntity.created(uri).build();

	}
	
	@RequestMapping(value = "/{id}/comentarios", method = RequestMethod.GET)
	public ResponseEntity<List<Comentario>> listarComentarios(@PathVariable("id") Long livroId) {
		List<Comentario> comentarios = livrosService.listarComentario(livroId);

		return ResponseEntity.status(HttpStatus.OK).body(comentarios);

	}
}
