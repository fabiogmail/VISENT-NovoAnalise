package br.com.visent.analise.converter;

import java.time.Duration;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.visent.analise.adapter.AdapterHelper;

@Converter(autoApply = true)
public class DurationAttributeConverter implements AttributeConverter<Duration, String> {

    @Override
    public String convertToDatabaseColumn(Duration duration) {
        return duration == null ? null : AdapterHelper.durationToString(duration);
    }

    @Override
    public Duration convertToEntityAttribute(String dbData) {
        return dbData == null ? null : AdapterHelper.stringToDuration(dbData);
    }
    
}
