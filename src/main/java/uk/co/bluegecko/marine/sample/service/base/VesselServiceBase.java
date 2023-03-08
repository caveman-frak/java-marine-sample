package uk.co.bluegecko.marine.sample.service.base;


import io.micrometer.core.annotation.Timed;
import lombok.NonNull;
import lombok.Value;
import org.springframework.stereotype.Service;
import uk.co.bluegecko.marine.sample.model.data.Vessel;
import uk.co.bluegecko.marine.sample.repository.VesselRepository;
import uk.co.bluegecko.marine.sample.service.VesselService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Value
public class VesselServiceBase implements VesselService {

	VesselRepository vesselRepository;

	@Timed("vessel.all")
	@Override
	public List<Vessel> all() {
		return vesselRepository.findByActiveTrue();
	}

	@Timed("vessel.get")
	@Override
	public Optional<Vessel> find(@NonNull UUID id) {
		return vesselRepository.findById(id);
	}

	@Timed("vessel.delete")
	@Override
	public boolean delete(@NonNull UUID id) {
		if (vesselRepository.existsById(id)) {
			vesselRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
}
