package org.opendarts.core.stats.model;

/**
 * The Interface IStatValue.
 */
public interface IStatValue<T> {

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	T getValue();

	/**
	 * Gets the value as string.
	 *
	 * @return the value as string
	 */
	String getValueAsString();

}
