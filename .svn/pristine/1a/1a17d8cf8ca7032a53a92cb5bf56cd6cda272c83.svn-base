package br.com.visent.analise.enums;

import java.util.Arrays;
import java.util.Optional;

public enum Granularidade {

	_1_HORA("1H"),
	_30_MINUTOS("30M"),
	_15_MINUTOS("15M");
	
	private String sigla;
	
	private Granularidade(String sigla) {
		this.sigla = sigla;
	}
	
	public String getSigla() {
		return this.sigla;
	}
	
	public static Granularidade toEnum(String sigla) {
		Optional<Granularidade> opt = Arrays.asList(values()).stream()
				.filter(g->g.getSigla().equals(sigla)).findFirst();
		return opt.orElse(null);
	}
	
}
