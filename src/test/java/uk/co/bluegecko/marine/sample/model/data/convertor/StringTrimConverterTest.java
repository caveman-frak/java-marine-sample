package uk.co.bluegecko.marine.sample.model.data.convertor;

import jakarta.persistence.AttributeConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringTrimConverterTest {

	private AttributeConverter<String, String> convertor;

	@BeforeEach
	void setUp() {
		convertor = new StringTrimConverter();
	}

	@Test
	void testFromDb() {
		assertThat(convertor.convertToEntityAttribute("Test      ")).isEqualTo("Test");
	}

	@Test
	void testFromDbWithNull() {
		assertThat(convertor.convertToEntityAttribute(null)).isEqualTo(null);
	}

	@Test
	void testToDb() {
		assertThat(convertor.convertToDatabaseColumn("Test      ")).isEqualTo("Test      ");
	}

	@Test
	void testToDbWithNull() {
		assertThat(convertor.convertToDatabaseColumn(null)).isEqualTo(null);
	}

}