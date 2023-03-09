package uk.co.bluegecko.marine.sample.model.data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

/**
 * Record of vessel identity.
 * <p>
 * A vessel may have multiple different types of identity, but at most one of each.
 * The identity within a particular source should be unique.
 */
@Embeddable
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Identifier {

	@Column
	@NonNull
	private IdentityProvider provider;

	@Column
	@NonNull
	private String ident;

}
