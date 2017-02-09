package br.com.visent.analise.converter;

import java.sql.Time;
import java.time.LocalTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalTimeAttributeConverter implements AttributeConverter<LocalTime, Time> {

	public Time convertToDatabaseColumn(LocalTime locDateTime) {
		return (locDateTime == null ? null : Time.valueOf(locDateTime));
	}

	public LocalTime convertToEntityAttribute(Time sqlTime) {
		return (sqlTime == null ? null : sqlTime.toLocalTime());
	}
	
}
