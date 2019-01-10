package com.bss.socialbooks.service.exceptions;

public class AutoresExistenteException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2484583833977423411L;

	public AutoresExistenteException(String mensagem) {
		super(mensagem);
	}

	public AutoresExistenteException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
