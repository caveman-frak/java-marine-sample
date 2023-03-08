package uk.co.bluegecko.marine.sample.repository;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uk.co.bluegecko.marine.sample.model.data.Vessel;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class VesselRepositoryTest {

	@Autowired
	private VesselRepository vesselRepository;

	@Test
	void testFindById() {
		assertThat(vesselRepository.findById(new UUID(1, 1)))
				.isPresent()
				.get()
				.extracting(Vessel::getName)
				.isEqualTo("Test 001");
	}

	@Test
	void testFindByIds() {
		assertThat(vesselRepository.findAllById(
				List.of(
						new UUID(1, 1),
						new UUID(1, 2))))
				.hasSize(2)
				.extracting(Vessel::getName)
				.containsExactly("Test 001", "Test 002");
	}

	@Test
	void testInsert() {
		Vessel vessel = Vessel.builder().name("Test100").tonnage(15).beam(5).length(20).draft(2.5).build();
		assertThat(vesselRepository.save(vessel))
				.has(allOf(
						new Condition<>(Vessel::isActive, "Active Vessel", vessel),
						new Condition<>(v -> v.getId() != null, "Id generated", vessel)));
		assertThat(vesselRepository.findByName("Test100"))
				.hasSize(1);
	}

	@Test
	void testDelete() {
		vesselRepository.deleteById(new UUID(1, 2));
		assertThat(vesselRepository.findByName("Test002"))
				.hasSize(0);
	}

	@Test
	void testUpdate() {
		UUID id = new UUID(1, 2);
		// get Test002 it should be inactive
		Vessel vessel = vesselRepository.findById(id)
				.orElseThrow();
		assertThat(vessel).extracting(Vessel::isActive).isEqualTo(Boolean.FALSE);
		vessel.setActive(true);
		// set it active and save
		vesselRepository.save(vessel);
		assertThat(vesselRepository.findById(id))
				.isPresent()
				.get()
				.extracting(Vessel::isActive)
				.isEqualTo(Boolean.TRUE);
	}

	@Test
	void testFindByName() {
		assertThat(vesselRepository.findByName("Test 001"))
				.hasSize(1)
				.extracting(Vessel::getName)
				.contains("Test 001");
	}

	@Test
	void testFindByActiveTrue() {
		assertThat(vesselRepository.findByActiveTrue())
				.hasSize(4)
				.extracting(Vessel::getName)
				.containsExactly("Test 001", "Test 003", "Test 004", "Test 005");
	}
}