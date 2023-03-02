package uk.co.bluegecko.marine.sample.service.base;


import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.togglz.core.manager.FeatureManager;
import uk.co.bluegecko.marine.sample.model.data.Vessel;
import uk.co.bluegecko.marine.sample.service.VesselService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static systems.uom.ucum.UCUM.*;
import static uk.co.bluegecko.marine.sample.feature.MeasurementFeatures.AMERICAN;
import static uk.co.bluegecko.marine.sample.feature.MeasurementFeatures.IMPERIAL;

@Service
@Value
@Slf4j
public class VesselServiceBase implements VesselService {

	FeatureManager featureManager;

	List<Vessel> vessels = List.of(
			Vessel.builder().id(11, 1).name("Test 001")
					.tonnage(1.0, TONNE).width(2.5, METER).length(5.1, METER).build(),
			Vessel.builder().id(12, 2).name("Test 002").active(false)
					.tonnage(1.1, TONNE).width(2.4, METER).length(5.2, METER).build(),
			Vessel.builder().id(13, 3).name("Test 003")
					.tonnage(1.2, TONNE).width(2.3, METER).length(5.3, METER).build(),
			Vessel.builder().id(14, 4).name("Test 004")
					.tonnage(1.3, TONNE).width(2.2, METER).length(5.4, METER).build(),
			Vessel.builder().id(15, 5).name("Test 005")
					.tonnage(1.4, TONNE).width(2.1, METER).length(5.5, METER).build()
	);

	@Override
	public List<Vessel> all() {
		return vessels.stream().filter(Vessel::isActive).toList();
	}

	@Override
	public Optional<Vessel> find(@NonNull UUID id) {
		var result = vessels.stream().filter(v -> v.getId().equals(id)).findFirst();
		if (featureManager.isActive(IMPERIAL)) {
			return result.map(vessel -> vessel.convertTo(LONG_TON, YARD_BRITISH));
		} else if (featureManager.isActive(AMERICAN)) {
			return result.map(vessel -> vessel.convertTo(LONG_TON, YARD_US_SURVEY));
		}
		return result;
	}

	@Override
	public boolean delete(@NonNull UUID id) {
		return false;
	}
}
