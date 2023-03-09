package uk.co.bluegecko.marine.sample.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import uk.co.bluegecko.marine.sample.model.data.IdentityProvider;
import uk.co.bluegecko.marine.sample.model.data.Vessel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VesselRepository extends CrudRepository<Vessel, UUID> {

	@Query(value = "SELECT v FROM Vessel v JOIN v.identifiers i WHERE i = :identity")
	List<Vessel> findByIdentity(@Param("identity") String identity);

	@Query(value = """
			SELECT v.* FROM vessel v
				WHERE v.id = (
				SELECT i.vessel FROM identifier i
				WHERE i.provider = :#{#provider.name()} AND i.ident = :identity)
				""",
			nativeQuery = true)
	Optional<Vessel> findByProviderIdentity(@Param("provider") IdentityProvider provider, @Param("identity") String identity);

	List<Vessel> findByActiveTrue();

}
