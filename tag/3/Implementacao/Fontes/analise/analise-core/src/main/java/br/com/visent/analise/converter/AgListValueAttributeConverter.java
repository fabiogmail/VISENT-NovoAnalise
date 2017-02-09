package br.com.visent.analise.converter;

import java.util.Arrays;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.visent.analise.enums.AgListValue;

@Converter(autoApply = true)
public class AgListValueAttributeConverter implements AttributeConverter<AgListValue<String>, String> {

	@Override
	public String convertToDatabaseColumn(AgListValue<String> attribute) {
		if (attribute == null || attribute.isEmpty()) return "";
		return String.join(",", attribute.getValues());
	}

	@Override
	public AgListValue<String> convertToEntityAttribute(String dbData) {
		AgListValue<String> obj = new AgListValue<>();
		Arrays.asList(dbData.split(",")).forEach(obj::add);
		return obj;
	}
	
}
