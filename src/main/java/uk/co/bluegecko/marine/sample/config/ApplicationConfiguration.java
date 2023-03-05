package uk.co.bluegecko.marine.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.Random;
import java.util.random.RandomGenerator;

@Component
public class ApplicationConfiguration {

	@Bean
	public Clock clock() {
		return Clock.systemUTC();
	}

	@Bean
	public RandomGenerator randomGenerator() {
		return new Random();
	}

}
