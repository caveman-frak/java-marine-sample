package uk.co.bluegecko.marine.sample.model.data;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import tech.units.indriya.quantity.Quantities;

import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import java.util.UUID;

import static systems.uom.ucum.UCUM.METER;
import static systems.uom.ucum.UCUM.TONNE;

/**
 * Simplified representation of a marine vessel.
 * <p>
 * Stores measurements in Metric ({@link systems.uom.ucum.UCUM#METER} and {@link systems.uom.ucum.UCUM#TONNE},
 * but can be entered in any standard unit using the builder.
 */
@Value
@Builder
public class Vessel {
	@NonNull
	UUID id;
	@Builder.Default
	boolean active = true;
	@NonNull
	String name;
	double tonnage;
	double width;
	double length;
	double draught;

	public Vessel convertTo(Unit<Mass> massUnit, Unit<Length> lengthUnit) {
		return Vessel.builder()
				.id(getId())
				.name(getName())
				.tonnage(getTonnage(), massUnit)
				.width(getWidth(), lengthUnit)
				.length(getLength(), lengthUnit)
				.draught(getDraught(), lengthUnit)
				.build();
	}

	@SuppressWarnings("unused")
	public static final class VesselBuilder {
		public VesselBuilder id(UUID uuid) {
			id = uuid;
			return this;
		}

		public VesselBuilder tonnage(double quantity, Unit<Mass> unit) {
			tonnage = Quantities.getQuantity(quantity, unit).to(TONNE).getValue().doubleValue();
			return this;
		}

		public VesselBuilder tonnage(double tonnage) {
			this.tonnage = tonnage;
			return this;
		}

		public VesselBuilder width(double quantity, Unit<Length> unit) {
			width = Quantities.getQuantity(quantity, unit).to(METER).getValue().doubleValue();
			return this;
		}

		public VesselBuilder width(double width) {
			this.width = width;
			return this;
		}

		public VesselBuilder length(double quantity, Unit<Length> unit) {
			length = Quantities.getQuantity(quantity, unit).to(METER).getValue().doubleValue();
			return this;
		}

		public VesselBuilder length(double length) {
			this.length = length;
			return this;
		}

		public VesselBuilder draught(double quantity, Unit<Length> unit) {
			draught = Quantities.getQuantity(quantity, unit).to(METER).getValue().doubleValue();
			return this;
		}

		public VesselBuilder draught(double draught) {
			this.draught = draught;
			return this;
		}

		public VesselBuilder id(long most, long least) {
			return id(new UUID(most, least));
		}
	}
}
