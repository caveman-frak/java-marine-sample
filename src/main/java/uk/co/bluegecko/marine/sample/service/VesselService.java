package uk.co.bluegecko.marine.sample.service;


import lombok.NonNull;
import uk.co.bluegecko.marine.sample.model.data.Vessel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Vessel related services.
 */
public interface VesselService {

	/**
	 * Generate a list of all active Vessels.
	 *
	 * @return All active vessels.
	 */
	List<Vessel> all();

	/**
	 * Find a vessel by unique UUID.
	 *
	 * @param id the unique UUID for a vessel, can't be null.
	 * @return the vessel if found or None.
	 */
	Optional<Vessel> find(@NonNull UUID id);

	/**
	 * Delete a vessel by unique UUID.
	 *
	 * @param id the unique UUID for a vessel, can't be null.
	 * @return true if the vessel was found.
	 */
	boolean delete(@NonNull UUID id);
}
