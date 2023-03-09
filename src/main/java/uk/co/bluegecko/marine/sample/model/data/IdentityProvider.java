package uk.co.bluegecko.marine.sample.model.data;

/**
 * Type or source of vessel identity.
 */
public enum IdentityProvider {
	/**
	 * Maritime Mobile Service Identity.
	 */
	MMSI,
	/**
	 * International Maritime Organisation.
	 */
	IMO,
	/**
	 * International Radio Call Sign.
	 */
	IRCS,
	/**
	 * Registered name of vessel.
	 * <p>
	 * Usually includes Port and/or Country of Registration as well as name.
	 */
	REGISTRY,
	/**
	 * User provided nickname
	 */
	NICKNAME,
	/**
	 * Undefined identity provider.
	 */
	OTHER
}
