package uk.co.bluegecko.marine.sample.service.base;

import lombok.Value;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.io.GeohashUtils;
import org.locationtech.spatial4j.shape.Point;
import org.locationtech.spatial4j.shape.Rectangle;
import org.springframework.stereotype.Service;
import uk.co.bluegecko.marine.sample.service.GeospatialService;

@Service
@Value
public class GeospatialServiceBase implements GeospatialService {

	SpatialContext context;
	int precision;

	public GeospatialServiceBase() {
		context = SpatialContext.GEO;
		precision = 5;
	}

	@Override
	public String encode(double latitude, double longitude) {
		return GeohashUtils.encodeLatLon(latitude, longitude, getPrecision());
	}

	@Override
	public String encode(Point point) {
		return encode(point.getLat(), point.getLon());
	}

	@Override
	public Point decode(String hash) {
		return GeohashUtils.decode(hash, getContext());
	}

	@Override
	public Rectangle decodeBoundary(String hash) {
		return GeohashUtils.decodeBoundary(hash, getContext());
	}

}
