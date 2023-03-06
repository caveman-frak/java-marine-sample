package uk.co.bluegecko.marine.sample.service;


import lombok.NonNull;
import uk.co.bluegecko.marine.sample.model.data.Vessel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * {@link Vessel} related services.
 */
public interface VesselService {

	/**
	 * Generate a list of all active {@link Vessel}s.
	 *
	 * @return All active vessels.
	 */
	List<Vessel> all();

	/**
	 * Find a {@link Vessel} by unique id.
	 *
	 * @param id the unique {@link UUID} for a vessel, can't be null.
	 * @return the vessel if found or None.
	 */
	Optional<Vessel> find(@NonNull UUID id);

	/**
	 * Delete a {@link Vessel} by unique id.
	 *
	 * @param id the unique {@link UUID} for a vessel, can't be null.
	 * @return true if the vessel was found and deleted, otherwise false.
	 */
	boolean delete(@NonNull UUID id);

}
