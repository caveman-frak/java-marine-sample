package uk.co.bluegecko.marine.sample.model.data;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static javax.measure.MetricPrefix.CENTI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static systems.uom.ucum.UCUM.METER;
import static systems.uom.ucum.UCUM.TONNE;
import static tech.units.indriya.unit.Units.KILOGRAM;
import static tech.units.indriya.unit.Units.METRE;

class VesselTest {

	@Test
	void testCreateWithUUID() {
		UUID uuid = UUID.randomUUID();
		Vessel vessel = Vessel.builder().id(uuid)
				.identifier(IdentityProvider.NICKNAME, "Test 01").tonnage(0.50).build();

		assertThat(vessel.getId()).isEqualTo(uuid);
		assertThat(vessel.getIdentifier(IdentityProvider.NICKNAME))
				.isPresent()
				.get()
				.isEqualTo("Test 01");
		assertThat(vessel.getTonnage()).isEqualTo(0.5, within(0.01));
	}

	@Test
	void testCreateWithBits() {
		Vessel vessel = Vessel.builder().id(0, 1)
				.identifier(IdentityProvider.NICKNAME, "Test 01").draft(2.5, METER).build();

		assertThat(vessel.getId()).isEqualTo(new UUID(0, 1));
		assertThat(vessel.getIdentifier(IdentityProvider.NICKNAME))
				.isPresent()
				.get()
				.isEqualTo("Test 01");
		assertThat(vessel.getDraft()).isEqualTo(2.5, within(0.01));
	}

	@Test
	void testCreateActive() {
		assertThat(Vessel.builder().id(0, 1)
				.identifier(IdentityProvider.NICKNAME, "Test 01").build()
				.isActive())
				.as("Active default")
				.isTrue();
		assertThat(Vessel.builder().id(0, 1).active(true)
				.identifier(IdentityProvider.NICKNAME, "Test 01").build()
				.isActive())
				.as("Active explicit true")
				.isTrue();
		assertThat(Vessel.builder().id(0, 1).active(false)
				.identifier(IdentityProvider.NICKNAME, "Test 01").build()
				.isActive())
				.as("Active false")
				.isFalse();
	}

	@Test
	void testTonnageConversion() {
		assertThat(Vessel.builder().id(0, 1)
				.identifier(IdentityProvider.NICKNAME, "Test 01")
				.tonnage(1.0, TONNE).build()
				.getTonnage())
				.as("as 1 Tonne")
				.isEqualTo(1.0, within(0.01));
		assertThat(Vessel.builder().id(0, 1)
				.identifier(IdentityProvider.NICKNAME, "Test 01")
				.tonnage(500, KILOGRAM).build()
				.getTonnage())
				.as("as 500 kg")
				.isEqualTo(0.5, within(0.01));
	}

	@Test
	void testDraughtConversion() {
		assertThat(Vessel.builder().id(0, 1)
				.identifier(IdentityProvider.NICKNAME, "Test 01")
				.draft(5.0, METER).build()
				.getDraft())
				.as("as 5 Meters")
				.isEqualTo(5.0, within(0.01));
		assertThat(Vessel.builder().id(0, 1)
				.identifier(IdentityProvider.NICKNAME, "Test 01")
				.draft(150, CENTI(METRE)).build()
				.getDraft())
				.as("as 150 cm")
				.isEqualTo(1.5, within(0.01));
	}

	@Test
	void testNoIdentifiers() {
		assertThat(Vessel.builder().id(0, 1)
				.draft(5.0, METER).build()
				.getIdentifiers())
				.isNotNull()
				.isEmpty();
	}

	@Test
	void testWithIdentifiers() {
		assertThat(Vessel.builder().id(0, 1)
				.identifiers(Map.of(
						IdentityProvider.NICKNAME, "testy",
						IdentityProvider.MMSI, "00000001"))
				.build()
				.getIdentifiers())
				.isNotNull()
				.containsValues("testy", "00000001");
	}

}