package uk.co.bluegecko.marine.sample.config;

import lombok.NonNull;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.*;
import java.util.Random;
import java.util.random.RandomGenerator;

@TestConfiguration
public class TestApplicationConfiguration {

	@Bean
	public ZoneId zone() {
		return ZoneOffset.UTC;
	}

	@Bean
	public LocalDate date() {
		return LocalDate.of(2000, Month.JANUARY, 1);
	}

	@Bean
	public LocalTime time() {
		return LocalTime.of(12, 30, 0);
	}

	@Bean
	public ZonedDateTime dateTime(@NonNull LocalDate date, @NonNull LocalTime time, @NonNull ZoneId zone) {
		return ZonedDateTime.of(date, time, zone);
	}

	@Bean
	public Instant instant(@NonNull ZonedDateTime dateTime) {
		return dateTime.toInstant();
	}

	@Bean
	@Primary
	public Clock clock(@NonNull Instant instant, @NonNull ZoneId zone) {
		return Clock.fixed(instant, zone);
	}

	@Bean
	@Primary
	public RandomGenerator randomGenerator() {
		return new Random();
	}

}
