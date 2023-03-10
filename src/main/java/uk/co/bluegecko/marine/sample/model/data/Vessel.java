package uk.co.bluegecko.marine.sample.model.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import systems.uom.ucum.UCUM;
import tech.units.indriya.quantity.Quantities;

import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static systems.uom.ucum.UCUM.METER;
import static systems.uom.ucum.UCUM.TONNE;

/**
 * Simplified representation of a marine vessel.
 * <p>
 * Stores measurements in Metric
 * (Length in {@link UCUM#METER} and Mass in {@link UCUM#TONNE}),
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
	private UUID id;
	@Column(nullable = false)
	@Setter
	@Builder.Default
	private boolean active = true;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "identifier",
			joinColumns = {@JoinColumn(name = "vessel", referencedColumnName = "id")})
	@MapKeyColumn(name = "provider")
	@Column(name = "ident")
	@NonNull
	@Builder.Default
	private Map<IdentityProvider, String> identifiers = new EnumMap<>(IdentityProvider.class);
	@Column
	private double tonnage;
	@Column
	private double beam;
	@Column(name = "len")
	private double length;
	@Column
	private double draft;

	public Optional<String> getIdentifier(IdentityProvider provider) {
		return Optional.of(getIdentifiers().get(provider));
	}

	public Vessel convertTo(Unit<Mass> massUnit, Unit<Length> lengthUnit) {
		return Vessel.builder()
				.id(getId())
				.identifiers(getIdentifiers())
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

		public VesselBuilder identifier(IdentityProvider provider, String ident) {
			if (!identifiers$set) {
				identifiers$value = new EnumMap<>(IdentityProvider.class);
				identifiers$set = true;
			}
			identifiers$value.put(provider, ident);
			return this;
		}
	}
}
