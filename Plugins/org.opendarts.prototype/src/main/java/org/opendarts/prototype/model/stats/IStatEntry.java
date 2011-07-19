package org.opendarts.prototype.model.stats;

/**
 * The Interface IStatEntry.
 */
public interface IStatEntry<T> {

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	String getKey();

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	IStatValue<T> getValue();
}
