package uk.co.bluegecko.marine.sample.service;

import org.locationtech.spatial4j.shape.Point;
import org.locationtech.spatial4j.shape.Rectangle;

public interface GeospatialService {

	String encode(double latitude, double longitude);

	String encode(Point point);

	Point decode(String hash);

	Rectangle decodeBoundary(String hash);
}
