package uk.co.bluegecko.marine.sample.feature;

import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;

public enum MeasurementFeatures implements Feature {

	@Label("Metric")
	METRIC,

	@EnabledByDefault
	@Label("Imperial")
	IMPERIAL,

	@Label("American")
	AMERICAN
}
