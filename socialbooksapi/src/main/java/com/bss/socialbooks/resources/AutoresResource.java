package com.bss.socialbooks.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bss.socialbooks.domain.Autor;
import com.bss.socialbooks.service.AutoresService;

@RestController //
@RequestMapping(value = "/autores")
public class AutoresResource {

	@Autowired
	private AutoresService autoresService;

	// Listar os Autores
	@RequestMapping(method = RequestMethod.GET, produces = {
			org.springframework.http.MediaType.APPLICATION_JSON_VALUE, 
			org.springframework.http.MediaType.APPLICATION_XML_VALUE
			
	})
	public ResponseEntity<List<Autor>> listar() {

		return ResponseEntity.status(HttpStatus.OK).body(autoresService.listar());
	}

	// Salvado um Autor - Methodo Post
	@RequestMapping(method = RequestMethod.POST) //@Valid Verifica a Camada de persitência 
	public ResponseEntity<Void> salvar(@Valid @RequestBody Autor autor) {
		autor = autoresService.salvar(autor);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(autor.getId()).toUri();

		return ResponseEntity.created(uri).build();

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Autor> buscar(@PathVariable("id") Long id) {
		Autor autor = autoresService.buscar(id);
		return ResponseEntity.status(HttpStatus.OK).body(autor);
	}
}
