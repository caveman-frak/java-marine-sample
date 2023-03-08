package uk.co.bluegecko.marine.sample.model.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
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
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vessel {
	@Id
	@GeneratedValue
	UUID id;
	@Column(nullable = false)
	@Setter
	@Builder.Default
	boolean active = true;
	@Column(nullable = false)
	@NonNull
	String name;
	@Column
	double tonnage;
	@Column
	double beam;
	@Column(name = "len")
	double length;
	@Column
	double draft;

	public Vessel convertTo(Unit<Mass> massUnit, Unit<Length> lengthUnit) {
		return Vessel.builder()
				.id(getId())
				.name(getName())
				.tonnage(getTonnage(), massUnit)
				.beam(getBeam(), lengthUnit)
				.length(getLength(), lengthUnit)
				.draft(getDraft(), lengthUnit)
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

		public VesselBuilder beam(double quantity, Unit<Length> unit) {
			beam = Quantities.getQuantity(quantity, unit).to(METER).getValue().doubleValue();
			return this;
		}

		public VesselBuilder beam(double beam) {
			this.beam = beam;
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

		public VesselBuilder draft(double quantity, Unit<Length> unit) {
			draft = Quantities.getQuantity(quantity, unit).to(METER).getValue().doubleValue();
			return this;
		}

		public VesselBuilder draft(double draught) {
			this.draft = draught;
			return this;
		}

		public VesselBuilder id(long most, long least) {
			return id(new UUID(most, least));
		}
	}
}
