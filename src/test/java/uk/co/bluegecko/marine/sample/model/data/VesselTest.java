package uk.co.bluegecko.marine.sample.model.data;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class VesselTest {

	@Test
	void testCreateWithUUID() {
		UUID uuid = UUID.randomUUID();
		Vessel vessel = Vessel.builder().id(uuid).name("Test 01").tonnage(0.50f).build();

		assertThat(vessel.getId()).isEqualTo(uuid);
		assertThat(vessel.getName()).isEqualTo("Test 01");
		assertThat(vessel.getTonnage()).isEqualTo(0.5f, within(0.01f));
	}

	@Test
	void testCreateWithBits() {
		Vessel vessel = Vessel.builder().id(0, 1).name("Test 01").tonnage(0.50f).build();

		assertThat(vessel.getId()).isEqualTo(new UUID(0, 1));
		assertThat(vessel.getName()).isEqualTo("Test 01");
		assertThat(vessel.getTonnage()).isEqualTo(0.5f, within(0.01f));
	}

	@Test
	void testCreateActive() {
		assertThat(Vessel.builder().id(0, 1).name("Test 01").tonnage(0.50f).build()
				.isActive())
				.as("Active default")
				.isTrue();
		assertThat(Vessel.builder().id(0, 1).active(true).name("Test 01").tonnage(0.50f).build()
				.isActive())
				.as("Active explicit true")
				.isTrue();
		assertThat(Vessel.builder().id(0, 1).active(false).name("Test 01").tonnage(0.50f).build()
				.isActive())
				.as("Active false")
				.isFalse();
	}
}