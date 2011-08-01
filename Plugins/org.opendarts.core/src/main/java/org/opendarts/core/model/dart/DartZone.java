package org.opendarts.core.model.dart;

/**
 * The Enum DartZone.
 */
public enum DartZone {

	/** The SINGLE. */
	SINGLE,
	/** The DOUBLE. */
	DOUBLE,
	/** The TRIPLE. */
	TRIPLE,
	/** The NONE. */
	NONE;

	/**
	 * Gets the multiplier.
	 *
	 * @return the multiplier
	 */
	public int getMultiplier() {
		int result;
		switch (this) {
			case SINGLE:
				result = 1;
				break;
			case DOUBLE:
				result = 2;
				break;
			case TRIPLE:
				result = 3;
				break;
			case NONE:
			default:
				result = 0;
				break;
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		String result;
		switch (this) {
			case DOUBLE:
				result = "D";
				break;
			case TRIPLE:
				result = "T";
				break;
			case SINGLE:
			case NONE:
			default:
				result = "";
				break;
		}
		return result;
	}
}
