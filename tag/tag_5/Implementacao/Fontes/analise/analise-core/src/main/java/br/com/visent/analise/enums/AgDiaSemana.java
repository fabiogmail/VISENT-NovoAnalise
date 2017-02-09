package br.com.visent.analise.enums;

import java.time.DayOfWeek;

public enum AgDiaSemana {

	DOM(DayOfWeek.SUNDAY), 
	SEG(DayOfWeek.MONDAY), 
	TER(DayOfWeek.TUESDAY), 
	QUA(DayOfWeek.WEDNESDAY), 
	QUI(DayOfWeek.THURSDAY), 
	SEX(DayOfWeek.FRIDAY), 
	SAB(DayOfWeek.SATURDAY);
	
	private DayOfWeek dayOfWeek;
	
	private AgDiaSemana(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	
	public DayOfWeek getDayOfWeek() {
		return this.dayOfWeek;
	}
	
}
