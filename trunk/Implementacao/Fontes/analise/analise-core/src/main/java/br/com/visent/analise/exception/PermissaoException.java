package br.com.visent.analise.exception;

public class PermissaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private boolean messageKey;
	
	public PermissaoException(String message) {
		super(message);
	}
	
	public PermissaoException(String message, boolean key) {
		super(message);
		this.messageKey = key;
	}
	
	public boolean isMessageKey() {
		return this.messageKey;
	}
	
}
