package br.com.visent.analise.converter;

import javax.persistence.AttributeConverter;

import br.com.visent.analise.enums.Granularidade;

public class GranularidadeAttributeConverter implements AttributeConverter<Granularidade, String> {

	@Override
	public String convertToDatabaseColumn(Granularidade granularidade) {
		return granularidade.getSigla();
	}

	@Override
	public Granularidade convertToEntityAttribute(String dbData) {
		return Granularidade.toEnum(dbData);
	}

}
