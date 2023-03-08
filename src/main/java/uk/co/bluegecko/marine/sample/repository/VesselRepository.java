package uk.co.bluegecko.marine.sample.repository;

import org.springframework.data.repository.CrudRepository;
import uk.co.bluegecko.marine.sample.model.data.Vessel;

import java.util.List;
import java.util.UUID;

public interface VesselRepository extends CrudRepository<Vessel, UUID> {

	List<Vessel> findByName(String name);

	List<Vessel> findByActiveTrue();

}
