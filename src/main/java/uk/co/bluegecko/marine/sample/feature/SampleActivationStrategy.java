package uk.co.bluegecko.marine.sample.feature;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.togglz.core.Feature;
import org.togglz.core.activation.Parameter;
import org.togglz.core.repository.FeatureState;
import org.togglz.core.spi.ActivationStrategy;
import org.togglz.core.user.FeatureUser;

import java.time.Clock;
import java.time.temporal.ChronoField;

@Slf4j
@Value
public class SampleActivationStrategy implements ActivationStrategy {

	Clock clock;

	@Override
	public String getId() {
		return "sample";
	}

	@Override
	public String getName() {
		return "Sample Activation Strategy";
	}

	@Override
	public boolean isActive(FeatureState featureState, FeatureUser user) {
		Feature feature = featureState.getFeature();
		if (feature == MeasurementFeatures.IMPERIAL) {
			int seconds = clock.instant().get(ChronoField.MINUTE_OF_HOUR);
			log.debug("feature = {}, seconds = {}", feature, seconds);
			return seconds % 2 == 0;
		} else {
			log.debug("feature = {}", feature);
			return false;
		}
	}

	@Override
	public Parameter[] getParameters() {
		return new Parameter[0];
	}
}
