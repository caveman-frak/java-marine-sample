package uk.co.bluegecko.marine.sample.model.data.convertor;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StringTrimConverter implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String attribute) {
		return attribute;
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		return dbData == null ? null : dbData.trim();
	}

}