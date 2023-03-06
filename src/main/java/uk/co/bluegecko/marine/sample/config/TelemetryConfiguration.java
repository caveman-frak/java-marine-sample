package uk.co.bluegecko.marine.sample.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Configuration for Open Telemetry.
 */
@Component
@Slf4j
public class TelemetryConfiguration {

	/**
	 * Unbind the Open Telemetry {@link MeterRegistry} from the {@link Metrics#globalRegistry} and
	 * return as a bean to ensure it is available to all Metrics instances.
	 *
	 * @return the Open Telemetry registry if found, otherwise null.
	 */
	@Bean
	@ConditionalOnClass(name = "io.opentelemetry.javaagent.OpenTelemetryAgent")
	public MeterRegistry otelRegistry() {
		Optional<MeterRegistry> otelRegistry = Metrics.globalRegistry.getRegistries()
				.stream()
				.peek(r -> log.debug("Meter Registry: {}", r.getClass().getName()))
				.filter(r -> r.getClass().getName().contains("OpenTelemetryMeterRegistry"))
				.findAny();
		otelRegistry.ifPresent(Metrics.globalRegistry::remove);
		return otelRegistry.orElse(null);
	}

}
