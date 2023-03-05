package uk.co.bluegecko.marine.sample.controller;

import lombok.NonNull;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.*;

@TestConfiguration
class TestApplicationConfiguration {

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
	public Clock clock(@NonNull Instant instant, @NonNull ZoneId zone) {
		return Clock.fixed(instant, zone);
	}
}
