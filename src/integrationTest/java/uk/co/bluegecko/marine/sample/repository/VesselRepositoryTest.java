package uk.co.bluegecko.marine.sample.repository;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uk.co.bluegecko.marine.sample.model.data.IdentityProvider;
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
				.extracting(v -> v.getIdentifier(IdentityProvider.NICKNAME).orElseThrow())
				.isEqualTo("Test-001");
	}

	@Test
	void testFindByIds() {
		assertThat(vesselRepository.findAllById(
				List.of(
						new UUID(1, 1),
						new UUID(1, 2))))
				.hasSize(2)
				.extracting(v -> v.getIdentifier(IdentityProvider.NICKNAME).orElseThrow())
				.containsExactly("Test-001", "Test-002");
	}

	@Test
	void testInsert() {
		Vessel vessel = Vessel.builder()
				.identifier(IdentityProvider.NICKNAME, "Test100")
				.tonnage(15)
				.beam(5)
				.length(20)
				.draft(2.5)
				.build();
		assertThat(vesselRepository.save(vessel))
				.has(allOf(
						new Condition<>(Vessel::isActive, "Active Vessel"),
						new Condition<>(v -> v.getId() != null, "Id generated")));
		assertThat(vesselRepository.count())
				.isEqualTo(6);
	}

	@Test
	void testDelete() {
		vesselRepository.deleteById(new UUID(1, 2));
		assertThat(vesselRepository.existsById(new UUID(1, 2)))
				.isFalse();
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
	void testFindByActiveTrue() {
		assertThat(vesselRepository.findByActiveTrue())
				.hasSize(4)
				.extracting(v -> v.getIdentifier(IdentityProvider.NICKNAME).orElseThrow())
				.containsExactly("Test-001", "Test-003", "Test-004", "Test-005");
	}

	@Test
	void testIdentityRetrieved() {
		Vessel vessel = vesselRepository.findById(new UUID(1, 1))
				.orElseThrow();
		assertThat(vessel.getIdentifiers())
				.hasSize(1)
				.containsValues("Test-001");
	}

	@Test
	void testAddIdentity() {
		// get Test005 it should have 1 identifier
		Vessel vessel = vesselRepository.findById(new UUID(1, 5))
				.orElseThrow();
		assertThat(vessel.getIdentifiers()).hasSize(1);
		vessel.getIdentifiers().put(IdentityProvider.MMSI, "00000005");
		// add a second identifier and save
		vessel = vesselRepository.save(vessel);
		assertThat(vessel.getIdentifiers())
				.hasSize(2)
				.containsValues("Test-005", "00000005");
	}

	@Test
	void testDeleteIdentity() {
		// get Test004 it should have 1 identifier
		Vessel vessel = vesselRepository.findById(new UUID(1, 4))
				.orElseThrow();
		assertThat(vessel.getIdentifiers()).hasSize(1);
		vessel.getIdentifiers().clear();
		// remove all identifiers and save
		vessel = vesselRepository.save(vessel);
		assertThat(vessel.getIdentifiers())
				.isEmpty();
	}

	@Test
	void testFindByIdent() {
		assertThat(vesselRepository.findByIdentity("0000003"))
				.hasSize(1)
				.extracting(Vessel::getId)
				.contains(new UUID(1, 3));
	}

	@Test
	void testFindByProviderIdent() {
		assertThat(vesselRepository.findByProviderIdentity(IdentityProvider.IRCS, "0000003"))
				.as("correct provider")
				.isPresent()
				.get()
				.extracting(Vessel::getId)
				.isEqualTo(new UUID(1, 3));
		assertThat(vesselRepository.findByProviderIdentity(IdentityProvider.REGISTRY, "0000003"))
				.as("incorrect provider")
				.isEmpty();
	}

}