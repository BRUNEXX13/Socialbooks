package com.bss.socialbooks.client;

import java.net.URI;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.bss.socialbooks.domain.Livro;

public class LivrosClient {

	// Rest Template
	private RestTemplate restTemplate;
	
	//URI = JUNCAO DA URL COM URN
	private String URI_BASE;
	
	//URN NOME DO RECURSO = livros
	private String URN_BASE = "/livros";

	private String credencial;
	
	
	public LivrosClient(String url, String usuario, String senha) {
		
		restTemplate = new RestTemplate();
		
		URI_BASE = url.concat(URN_BASE);
		
		String credencialAux = usuario + ":" + senha;
		
		credencial = "Basic " + Base64.getEncoder()
		.encodeToString(credencialAux.getBytes());
	}

	public List<Livro> listar() {

		// O que eu quero fazer como requicao? = Get na uri com a autorizacao
		RequestEntity<Void> request = RequestEntity.
				get(URI.create(URI_BASE))
				.header("Authorization", credencial)
				.build();

		// Vetor de Livros
		ResponseEntity<Livro[]> response = restTemplate.exchange(request, Livro[].class);

		return Arrays.asList(response.getBody());

	}

	public String salvar(Livro livro) {
		// Montando o Template Rest
		RestTemplate restTemplate = new RestTemplate();

		RequestEntity<Livro> request = RequestEntity.
				post(URI.create(URI_BASE))
				.header("Authorization", credencial)
				.body(livro);

		// PAssando resposta
		ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);

		return response.getHeaders().getLocation().toString();

	}

	public Livro buscar(String uri) {

		RequestEntity<Void> request = RequestEntity
				.get(URI.create(uri))
				.header("Authorization", credencial)
				.build();

		ResponseEntity<Livro> response = restTemplate.exchange(request, Livro.class);

		return response.getBody();

	}
}
