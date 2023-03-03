package uk.co.bluegecko.marine.sample.service.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.bluegecko.marine.sample.model.data.Vessel;
import uk.co.bluegecko.marine.sample.service.VesselService;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class VesselServiceBaseTest {

	private VesselService vesselService;

	@BeforeEach
	void setUp() {
		vesselService = new VesselServiceBase();
	}

	@Test
	void testAll() {
		assertThat(vesselService.all()).hasSize(4)
				.extracting(Vessel::getName)
				.contains("Test 001", "Test 003", "Test 004", "Test 005");
	}

	@Test
	@SuppressWarnings("null")
	void testFind() {
		assertThat(vesselService.find(new UUID(11, 1)))
				.as("Correct ID")
				.isPresent()
				.contains(vesselService.all().get(0));
		assertThat(vesselService.find(new UUID(0, 1)))
				.as("Incorrect ID")
				.isEmpty();
		assertThatNullPointerException()
				.as("Null ID")
				.isThrownBy(() -> vesselService.find(null))
				.withMessage("id is marked non-null but is null")
				.withNoCause();
	}

	@Test
	@SuppressWarnings("null")
	void testDelete() {
		assertThat(vesselService.delete(new UUID(11, 1)))
				.as("Correct ID")
				.isTrue();
		assertThat(vesselService.delete(new UUID(0, 1)))
				.as("Incorrect ID")
				.isFalse();
		assertThatNullPointerException()
				.as("Null ID")
				.isThrownBy(() -> vesselService.delete(null))
				.withMessage("id is marked non-null but is null")
				.withNoCause();
	}

	@Test
	void testDeleteEffects() {
		assertThat(vesselService.delete(new UUID(11, 1)))
				.as("First pass")
				.isTrue();
		assertThat(vesselService.delete(new UUID(11, 1)))
				.as("Second pass, vessel deleted")
				.isFalse();
		assertThat(vesselService.all()).hasSize(3)
				.extracting(Vessel::getName)
				.contains("Test 003", "Test 004", "Test 005");
	}
}