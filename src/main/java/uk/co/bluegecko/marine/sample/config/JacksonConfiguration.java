package uk.co.bluegecko.marine.sample.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import tech.units.indriya.format.SimpleQuantityFormat;
import tech.uom.lib.jackson.UnitJacksonModule;

import javax.measure.Quantity;
import java.io.IOException;

@Component
public class JacksonConfiguration {

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer registerUnitOfMeasure() {

		return builder -> builder.modules(new UnitJacksonModule())
				.serializers(new QuantityJsonSerializer())
				.deserializers(new QuantityJsonDeserializer());
	}

	@SuppressWarnings("rawtypes")
	static class QuantityJsonSerializer extends StdScalarSerializer<Quantity> {

		public QuantityJsonSerializer() {
			super(Quantity.class);
		}

		@Override
		public void serialize(Quantity value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			gen.writeObject(SimpleQuantityFormat.getInstance().format(value));
		}

	}

	@SuppressWarnings("rawtypes")
	static class QuantityJsonDeserializer extends StdDeserializer<Quantity> {
		public QuantityJsonDeserializer() {
			super(Quantity.class);
		}

		@Override
		public Quantity deserialize(JsonParser jp, DeserializationContext deserializationContext)
				throws IOException {

			return SimpleQuantityFormat.getInstance().parse(jp.getValueAsString());
		}
	}
}
