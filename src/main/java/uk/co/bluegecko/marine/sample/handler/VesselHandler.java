package uk.co.bluegecko.marine.sample.handler;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.ServerResponse;
import uk.co.bluegecko.marine.sample.model.data.Vessel;
import uk.co.bluegecko.marine.sample.service.VesselService;

import java.util.Optional;
import java.util.UUID;

/**
 * Handler for all {@link Vessel} related REST requests.
 */
@Service
@Value
public class VesselHandler {

	VesselService vesselService;

	/**
	 * Find all active {@link Vessel}s.
	 *
	 * @return {@link ServerResponse} of {@link HttpStatus#OK} response with list of active vessels.
	 */
	public ServerResponse all() {
		return ServerResponse.ok().body(vesselService.all());
	}

	/**
	 * Find and return the {@link Vessel} with the unique id.
	 *
	 * @param uuid the unique {@link UUID} of the vessel.
	 * @return {@link ServerResponse} of {@link HttpStatus#OK} response with the vessel if it exists
	 * otherwise {@link HttpStatus#NOT_FOUND}.
	 */
	public ServerResponse find(String uuid) {
		Optional<Vessel> result = vesselService.find(UUID.fromString(uuid));
		return result.map(vessel -> ServerResponse.ok().body(vessel))
				.orElse(ServerResponse.notFound().build());
	}

	/**
	 * Find and delete the {@link Vessel} with the unique id.
	 *
	 * @param uuid the unique {@link UUID} of the vessel.
	 * @return {@link ServerResponse} of {@link HttpStatus#NO_CONTENT} if the vessel was found and deleted,
	 * otherwise {@link HttpStatus#NOT_FOUND}.
	 */
	public ServerResponse delete(String uuid) {
		if (vesselService.delete(UUID.fromString(uuid)))
			return ServerResponse.noContent().build();
		else
			return ServerResponse.notFound().build();
	}

}
