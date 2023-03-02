package uk.co.bluegecko.marine.sample.service.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.togglz.testing.TestFeatureManager;
import uk.co.bluegecko.marine.sample.feature.MeasurementFeatures;
import uk.co.bluegecko.marine.sample.model.data.Vessel;
import uk.co.bluegecko.marine.sample.service.VesselService;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class VesselServiceBaseTest {

	private TestFeatureManager featureManager;
	private VesselService vesselService;

	@BeforeEach
	void setUp() {
		featureManager = new TestFeatureManager(MeasurementFeatures.class);
		featureManager.disableAll();
		vesselService = new VesselServiceBase(featureManager);
	}

	@Test
	void testAll() {
		assertThat(vesselService.all()).hasSize(4)
				.extracting(Vessel::getName)
				.contains("Test 001", "Test 003", "Test 004", "Test 005");
	}

	@SuppressWarnings("DataFlowIssue")
	@Test
	void testFind() {
		assertThat(vesselService.find(new UUID(11, 1)))
				.as("Correct ID")
				.isPresent()
				.contains(vesselService.all().get(0));
		assertThat(vesselService.find(new UUID(0, 1)))
				.as("Incorrect ID")
				.isEmpty();
		assertThatNullPointerException()
				.as("Missing ID")
				.isThrownBy(() -> vesselService.find(null))
				.withMessage("id is marked non-null but is null")
				.withNoCause();
	}

	@Test
	void testFindInImperial() {
		featureManager.enable(MeasurementFeatures.IMPERIAL);
		Optional<Vessel> result = vesselService.find(new UUID(11, 1));
		assertThat(result)
				.as("Vessel found and with correct name")
				.isPresent().get().extracting(Vessel::getName).isEqualTo("Test 001");
		Vessel vessel = result.orElseThrow();
		assertThat(vessel.getId())
				.as("id")
				.isEqualTo(new UUID(11, 1));
		assertThat(vessel.getTonnage())
				.as("tonnage in tons")
				.isEqualTo(1.0160, within(0.0001));
		assertThat(vessel.getWidth())
				.as("width in yards")
				.isEqualTo(2.2859, within(0.0001));
		assertThat(vessel.getLength())
				.as("length in yards")
				.isEqualTo(4.6634, within(0.0001));
		assertThat(vessel.getDraught())
				.as("draught in yards")
				.isEqualTo(0.0, within(0.0001));
	}

	@Test
	void testFindInAmerican() {
		featureManager.enable(MeasurementFeatures.AMERICAN);
		Optional<Vessel> result = vesselService.find(new UUID(11, 1));
		assertThat(result)
				.as("Vessel found and with correct name")
				.isPresent().get().extracting(Vessel::getName).isEqualTo("Test 001");
		Vessel vessel = result.orElseThrow();
		assertThat(vessel.getId())
				.as("id")
				.isEqualTo(new UUID(11, 1));
		assertThat(vessel.getTonnage())
				.as("tonnage in tons")
				.isEqualTo(1.0160, within(0.0001));
		assertThat(vessel.getWidth())
				.as("width in yards")
				.isEqualTo(2.2860, within(0.0001));
		assertThat(vessel.getLength())
				.as("length in yards")
				.isEqualTo(4.6634, within(0.0001));
		assertThat(vessel.getDraught())
				.as("draught in yards")
				.isEqualTo(0.0, within(0.0001));
	}
}