package org.opendarts.core.stats.model;

import java.util.List;
import java.util.Map;

import org.opendarts.core.model.player.IPlayer;

/**
 * The Interface IStats.
 *
 * @param <T> the generic type
 */
public interface IStats<T> {

	/**
	 * Gets the element.
	 *
	 * @return the element
	 */
	T getElement();

	/**
	 * Gets the player.
	 *
	 * @return the player
	 */
	IPlayer getPlayer();

	/**
	 * Gets the all entries.
	 *
	 * @return the all entries
	 */
	@SuppressWarnings("rawtypes")
	Map<String, IStatsEntry> getAllEntries();

	/**
	 * Gets the entry.
	 *
	 * @param <U> the generic type
	 * @param key the key
	 * @return the entry
	 */
	<U> IStatsEntry<U> getEntry(String key);

	/**
	 * Adds the entry.
	 *
	 * @param <U> the generic type
	 * @param entry the entry
	 */
	<U> void addEntry(IStatsEntry<U> entry);

	/**
	 * Clean stats.
	 * Remove all stats not in list
	 * @param keys the keys
	 */
	void cleanStats(List<String> keys);
}
