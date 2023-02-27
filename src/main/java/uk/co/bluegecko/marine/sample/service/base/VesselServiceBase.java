package uk.co.bluegecko.marine.sample.service.base;


import lombok.NonNull;
import org.springframework.stereotype.Service;
import uk.co.bluegecko.marine.sample.model.data.Vessel;
import uk.co.bluegecko.marine.sample.service.VesselService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VesselServiceBase implements VesselService {


	private final List<Vessel> vessels = List.of(
			Vessel.builder().id(11, 1).name("Test 001").tonnage(1.0F).width(2.5F).length(5.1f).build(),
			Vessel.builder().id(12, 2).active(false).name("Test 002").tonnage(1.1F).width(2.4F).length(5.2f).build(),
			Vessel.builder().id(13, 3).name("Test 003").tonnage(1.2F).width(2.3f).length(5.3f).build(),
			Vessel.builder().id(14, 4).name("Test 004").tonnage(1.3F).width(2.2f).length(5.4F).build(),
			Vessel.builder().id(15, 5).name("Test 005").tonnage(1.4F).width(2.1F).length(5.5F).build()
	);

	@Override
	public List<Vessel> all() {
		return vessels.stream().filter(Vessel::isActive).toList();
	}

	@Override
	public Optional<Vessel> find(@NonNull UUID id) {
		return vessels.stream().filter(v -> v.getId().equals(id)).findFirst();
	}

	@Override
	public boolean delete(@NonNull UUID id) {
		return false;
	}
}
