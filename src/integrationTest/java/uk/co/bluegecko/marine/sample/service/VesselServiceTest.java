package uk.co.bluegecko.marine.sample.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import uk.co.bluegecko.marine.sample.model.data.Vessel;
import uk.co.bluegecko.marine.sample.repository.VesselRepository;
import uk.co.bluegecko.marine.sample.service.base.VesselServiceBase;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

@DataJpaTest
@Import({VesselServiceBase.class})
class VesselServiceTest {

	@Autowired
	private VesselRepository vesselRepository;
	@Autowired
	private VesselService vesselService;

//	@BeforeEach
//	void setUp() {
//		vesselService = new VesselServiceBase(vesselRepository);
//	}

	@Test
	void testAll() {
		assertThat(vesselService.all()).hasSize(4)
				.extracting(Vessel::getName)
				.containsExactly("Test 001", "Test 003", "Test 004", "Test 005");
	}

	@Test
	@SuppressWarnings("null")
	void testFind() {
		assertThat(vesselService.find(new UUID(1, 1)))
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
		assertThat(vesselService.delete(new UUID(1, 1)))
				.as("Correct ID")
				.isTrue();
		assertThat(vesselService.delete(new UUID(1, 9)))
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
		assertThat(vesselService.delete(new UUID(1, 1)))
				.as("First pass, vessel found")
				.isTrue();
		assertThat(vesselService.delete(new UUID(1, 1)))
				.as("Second pass, vessel not found")
				.isFalse();
		assertThat(vesselService.all()).hasSize(3)
				.extracting(Vessel::getName)
				.containsExactly("Test 003", "Test 004", "Test 005");
	}
}