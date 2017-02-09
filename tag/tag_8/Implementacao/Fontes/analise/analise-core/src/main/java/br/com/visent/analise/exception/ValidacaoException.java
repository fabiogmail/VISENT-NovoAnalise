package br.com.visent.analise.exception;

public class ValidacaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private boolean messageKey;
	
	public ValidacaoException(String message) {
		super(message);
	}
	
	public ValidacaoException(String message, boolean key) {
		super(message);
		this.messageKey = key;
	}
	
	public boolean isMessageKey() {
		return this.messageKey;
	}

}
