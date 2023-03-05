package uk.co.bluegecko.marine.sample.handler;

import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.ServerResponse;
import uk.co.bluegecko.marine.sample.model.data.Vessel;
import uk.co.bluegecko.marine.sample.service.VesselService;

import java.util.Optional;
import java.util.UUID;

@Service
@Value
public class VesselHandler {

	VesselService vesselService;

	/**
	 * Find all active vessels.
	 *
	 * @return 200 response with list of active vessels.
	 */
	public ServerResponse all() {
		return ServerResponse.ok().body(vesselService.all());
	}

	/**
	 * Find and return the vessel with the unique id.
	 *
	 * @param uuid the unique UUID of the vessel.
	 * @return 200 response with the vessel if it exists or 404.
	 */
	public ServerResponse find(String uuid) {
		Optional<Vessel> result = vesselService.find(UUID.fromString(uuid));
		return result.map(vessel -> ServerResponse.ok().body(vessel))
				.orElse(ServerResponse.notFound().build());
	}

	public ServerResponse delete(String uuid) {
		if (vesselService.delete(UUID.fromString(uuid)))
			return ServerResponse.noContent().build();
		else
			return ServerResponse.notFound().build();
	}

}
