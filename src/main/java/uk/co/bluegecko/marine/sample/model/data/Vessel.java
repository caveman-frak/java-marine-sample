package uk.co.bluegecko.marine.sample.model.data;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class Vessel {
	UUID id;

	@Builder.Default
	boolean active = true;
	String name;
	float tonnage;
	float width;
	float length;

	@SuppressWarnings("unused")
	public static final class VesselBuilder {
		public VesselBuilder id(UUID uuid) {
			id = uuid;
			return this;
		}

		public VesselBuilder id(long most, long least) {
			return id(new UUID(most, least));
		}
	}
}
