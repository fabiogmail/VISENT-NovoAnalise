package br.com.visent.analise.enums;

public enum AgSemanaOcorrencia {

	PRIMEIRA(1), 
	SEGUNDA(2), 
	TERCEIRA(3), 
	QUARTA(4), 
	ULTIMA(5);
	
	private Integer ordem;
	
	private AgSemanaOcorrencia(Integer ordem) {
		this.ordem = ordem;
	}
	
	public Integer getOrdem() {
		return this.ordem;
	}
	
}
