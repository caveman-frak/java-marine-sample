package uk.co.bluegecko.marine.sample.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.uom.lib.jackson.UnitJacksonModule;

import javax.measure.Quantity;
import javax.measure.Unit;

import static org.assertj.core.api.Assertions.assertThat;
import static systems.uom.ucum.UCUM.METER;
import static systems.uom.ucum.UCUM.TONNE;
import static tech.units.indriya.quantity.Quantities.getQuantity;

class JacksonConfigurationTest {

	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		objectMapper = new ObjectMapper();
		Module module = new UnitJacksonModule()
				.addSerializer(Quantity.class, new JacksonConfiguration.QuantityJsonSerializer())
				.addDeserializer(Quantity.class, new JacksonConfiguration.QuantityJsonDeserializer());
		objectMapper.registerModule(module);
	}

	@Test
	void testUnitSerialize() throws JsonProcessingException {
		assertThat(objectMapper.writeValueAsString(METER)).isEqualTo("\"m\"");
	}

	@Test
	void testUnitDeserialize() throws JsonProcessingException {
		assertThat(objectMapper.readValue("\"m\"", Unit.class)).isEqualTo(METER);
	}

	@Test
	void testQuantitySerialize() throws JsonProcessingException {
		assertThat(objectMapper.writeValueAsString(getQuantity(10.01, METER)))
				.as("=> 10.01m")
				.isEqualTo("\"10.01 m\"");
		assertThat(objectMapper.writeValueAsString(getQuantity(10.01, TONNE)))
				.as("=> 10.01m")
				.isEqualTo("\"10.01 kg*1000\"");
	}

	@Test
	@SuppressWarnings("raw")
	void testQuantityDeserialize() throws JsonProcessingException {
		Quantity<?> quantity = objectMapper.readValue("\"10.02 m\"", Quantity.class);
		assertThat(quantity).extracting(q -> q.getValue().doubleValue())
				.as("10.02m => value")
				.isEqualTo(10.02);
		assertThat(quantity)
				.as("10.02m => unit")
				.extracting(Quantity::getUnit).isEqualTo(METER);
		assertThat(objectMapper.readValue("\"10.02 kg*1000\"", Quantity.class))
				.as("10.02 tonnes => unit")
				.extracting(Quantity::getUnit).isEqualTo(TONNE);
	}

}