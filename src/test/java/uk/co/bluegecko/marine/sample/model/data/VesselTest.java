package uk.co.bluegecko.marine.sample.model.data;

import org.junit.jupiter.api.Test;

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
		Vessel vessel = Vessel.builder().id(uuid).name("Test 01").tonnage(0.50).build();

		assertThat(vessel.getId()).isEqualTo(uuid);
		assertThat(vessel.getName()).isEqualTo("Test 01");
		assertThat(vessel.getTonnage()).isEqualTo(0.5, within(0.01));
	}

	@Test
	void testCreateWithBits() {
		Vessel vessel = Vessel.builder().id(0, 1).name("Test 01").draught(2.5, METER).build();

		assertThat(vessel.getId()).isEqualTo(new UUID(0, 1));
		assertThat(vessel.getName()).isEqualTo("Test 01");
		assertThat(vessel.getDraught()).isEqualTo(2.5, within(0.01));
	}

	@Test
	void testCreateActive() {
		assertThat(Vessel.builder().id(0, 1).name("Test 01").build()
				.isActive())
				.as("Active default")
				.isTrue();
		assertThat(Vessel.builder().id(0, 1).active(true).name("Test 01").build()
				.isActive())
				.as("Active explicit true")
				.isTrue();
		assertThat(Vessel.builder().id(0, 1).active(false).name("Test 01").build()
				.isActive())
				.as("Active false")
				.isFalse();
	}

	@Test
	void testTonnageConversion() {
		assertThat(Vessel.builder().id(0, 1)
				.name("Test 01").tonnage(1.0, TONNE).build()
				.getTonnage())
				.as("as 1 Tonne")
				.isEqualTo(1.0, within(0.01));
		assertThat(Vessel.builder().id(0, 1)
				.name("Test 01").tonnage(500, KILOGRAM).build()
				.getTonnage())
				.as("as 500 kg")
				.isEqualTo(0.5, within(0.01));
	}

	@Test
	void testDraughtConversion() {
		assertThat(Vessel.builder().id(0, 1)
				.name("Test 01").draught(5.0, METER).build()
				.getDraught())
				.as("as 5 Meters")
				.isEqualTo(5.0, within(0.01));
		assertThat(Vessel.builder().id(0, 1)
				.name("Test 01").draught(150, CENTI(METRE)).build()
				.getDraught())
				.as("as 150 cm")
				.isEqualTo(1.5, within(0.01));
	}
}