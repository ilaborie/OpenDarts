package org.opendarts.prototype.model.stats;

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

	/**
	 * Gets the value as number.
	 *
	 * @return the value as number
	 */
	Number getValueAsNumber();
}
