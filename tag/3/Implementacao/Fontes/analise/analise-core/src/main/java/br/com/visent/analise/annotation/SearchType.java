package br.com.visent.analise.annotation;

/**
 * Enum do tipo de pesquisa.
 * @see SearchConfig 
 */
public enum SearchType {
	
	EQUALS(" = "), 
	LIKE(" like "), 
	LIKE_END(" like "), 
	LIKE_BEGIN(" like "), 
	GREATER(" > "), 
	GREATER_EQUALS(" >= "), 
	LESSER(" < "), 
	LESSER_EQUALS(" <= ");
	
	private String value;
	
	SearchType (String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}

}
