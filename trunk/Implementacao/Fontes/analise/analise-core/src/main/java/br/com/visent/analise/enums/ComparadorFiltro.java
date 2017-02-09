package br.com.visent.analise.enums;

public enum ComparadorFiltro {

	COMPARADOR_MAIOR(1),
	COMPARADOR_MENOR(-1);
	
	private Integer codigo;
	
	private ComparadorFiltro(Integer codigo) {
		this.codigo = codigo;
	}
	
	public Integer getCodigo() {
		return this.codigo;
	}
	
}
