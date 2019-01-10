package com.bss.socialbooks.aplicacao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.bss.socialbooks.client.LivrosClient;
import com.bss.socialbooks.domain.Livro;

public class Aplicacao {

	public static void main(String[] args) throws ParseException {

		LivrosClient cliente = new LivrosClient("http://localhost:8080", "brunoss", "12345");

		List<Livro> listaLivros = cliente.listar();

		// For para imprimir com response.getbody = traz o corpo da mensagem//
		for (Livro livro : listaLivros) {
			System.out.println("Livro : " + livro.getNome());

		}

		Livro livro = new Livro();
		livro.setNome("Gitao");
		livro.setEditora("Casa do Codigo");

		SimpleDateFormat publicacao = new SimpleDateFormat("dd/MM/yyyy");
		livro.setPublicacao(publicacao.parse("01/01/1990"));

		livro.setResumo("Dicas do Github");

		String localizacao = cliente.salvar(livro);

		System.out.println("URI DO LIVRO SALVO : " + localizacao);
		
		Livro livroBuscado = cliente.buscar(localizacao);
		
		System.out.println("Livro Buscado : " + livroBuscado.getNome());
	}

}
