package br.com.visent.analise.enums;

import java.util.Arrays;

public enum AgExportacao {

	VAZIO(""),
	HTML("HTML"),
	CSV("CSV"),
	HTML_CSV("HTML,CSV"),
	TXT("TXT"),
	HTML_TXT("HTML,TXT"),
	CSV_TXT("CSV,TXT"),
	HTML_CSV_TXT("HTML,CSV,TXT"), 
	FTP("FTP"), 
	BD("BD"), 
	TERMINAIS("TERMINAIS");
	
	private String valores;
	
	private AgExportacao(String valores) {
		this.valores = valores;
	}
	
	public String getValores() {
		return this.valores;
	}
	
	public static AgExportacao getByOrdinal(int ordinal) {
		AgExportacao agExportacao = Arrays.stream(AgExportacao.values())
			.filter(exportacao -> exportacao.ordinal() == ordinal)
			.findFirst().orElse(null);
		return agExportacao;
	}
	
}
