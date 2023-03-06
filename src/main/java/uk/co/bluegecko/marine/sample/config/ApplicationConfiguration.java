package uk.co.bluegecko.marine.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.Random;
import java.util.random.RandomGenerator;

/**
 * Configuration beans for general application use.
 */
@Component
public class ApplicationConfiguration {

	/**
	 * Clock instance.
	 *
	 * @return default to {@link Clock#systemUTC()} .
	 */
	@Bean
	public Clock clock() {
		return Clock.systemUTC();
	}

	/**
	 * Random instance.
	 *
	 * @return default to {@link Random}.
	 */
	@Bean
	public RandomGenerator randomGenerator() {
		return new Random();
	}

}
