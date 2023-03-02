package uk.co.bluegecko.marine.sample.feature;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.togglz.core.activation.ActivationStrategyProvider;
import org.togglz.core.manager.EnumBasedFeatureProvider;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.manager.FeatureManagerBuilder;
import org.togglz.core.repository.mem.InMemoryStateRepository;
import org.togglz.core.spi.FeatureProvider;
import org.togglz.core.user.NoOpUserProvider;

import java.time.Clock;
import java.util.List;

@Component
@Slf4j
public class FeatureConfig {

	@SuppressWarnings("unchecked")
	@Bean
	public FeatureProvider featureProvider() {
		return new EnumBasedFeatureProvider(MeasurementFeatures.class);
	}

	@Bean
	public ActivationStrategyProvider activationStrategyProvider(Clock clock) {
		log.info("creating activationStrategyProvider");
		return () -> List.of(new SampleActivationStrategy(clock));
	}

	@Bean
	public FeatureManager featureManager(FeatureProvider featureProvider,
	                                     ActivationStrategyProvider activationStrategyProvider) {
		log.info("using activationStrategyProvider {}", activationStrategyProvider.getClass().getName());
		return FeatureManagerBuilder
				.begin()
				.featureProvider(featureProvider)
				.activationStrategyProvider(activationStrategyProvider)
				.stateRepository(new InMemoryStateRepository())
				.userProvider(new NoOpUserProvider())
				.name("Sample Feature Manager")
				.build();
	}

}
