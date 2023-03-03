package uk.co.bluegecko.marine.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
public class ApplicationConfiguration {

	@Bean
	public Clock clock() {
		return Clock.systemUTC();
	}

}
