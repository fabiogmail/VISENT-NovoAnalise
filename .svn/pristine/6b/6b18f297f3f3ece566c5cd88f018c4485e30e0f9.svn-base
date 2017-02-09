package br.com.visent.analise.enums;

public enum AgPeriodicidade {

	MINUTO_15("15 minutos"), 
	MINUTO_30("30 minutos"), 
	HORA("Hora"), 
	DIA("Diário"), 
	SEMANA("Semanal"), 
	MES("Mensal"), 
	UMA_VEZ("Uma vez");
	
	private String descricao;
	
	private AgPeriodicidade(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public static AgPeriodicidade[] valuesAgenda() {
		return new AgPeriodicidade[]{UMA_VEZ, SEMANA, MES};
	}
	
	public static AgPeriodicidade[] valuesHistorico() {
		return new AgPeriodicidade[]{MINUTO_15, MINUTO_30, HORA, DIA, SEMANA, MES};
	}
	
}
