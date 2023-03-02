package uk.co.bluegecko.marine.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
public class ApplicationConfig {

	@Bean
	public Clock getClock() {
		return Clock.systemUTC();
	}
}
