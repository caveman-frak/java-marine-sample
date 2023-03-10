package uk.co.bluegecko.marine.sample.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import uk.co.bluegecko.marine.sample.model.data.IdentityProvider;
import uk.co.bluegecko.marine.sample.model.data.Vessel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for vessel related operations and queries.
 */
public interface VesselRepository extends CrudRepository<Vessel, UUID> {

	/**
	 * List all active vessels that use the supplied identity from any provider.
	 *
	 * @param identity the identity to search for.
	 * @return list of matching vessels.
	 */
	@Query(value = "SELECT v FROM Vessel v JOIN v.identifiers i WHERE i = :identity AND v.active = TRUE")
	List<Vessel> findByIdentity(@Param("identity") String identity);

	/**
	 * Find an active vessel that is using the identifier with that provider, at most one will exist.
	 *
	 * @param provider the provider to check.
	 * @param identity the identity to search for.
	 * @return the vessel if it is found and active, otherwise {@link Optional#empty()}.
	 */
	@Query(value = """
			SELECT v.* FROM vessel v WHERE v.id = (
				SELECT i.vessel FROM identifier i
				WHERE i.provider = :#{#provider.name()} AND i.ident = :identity AND v.active = TRUE)""",
			nativeQuery = true)
	Optional<Vessel> findByProviderIdentity(@Param("provider") IdentityProvider provider, @Param("identity") String identity);

	/**
	 * List all active vessels.
	 *
	 * @return list of active vessels.
	 */
	List<Vessel> findByActiveTrue();

}
