package org.opendarts.prototype.model.stats;

/**
 * The Interface IStatsEntry.
 */
public interface IStatsEntry<T> {

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
