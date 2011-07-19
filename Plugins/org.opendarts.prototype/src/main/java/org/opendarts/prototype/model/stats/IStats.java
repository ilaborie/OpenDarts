package org.opendarts.prototype.model.stats;

import java.util.Map;

import org.opendarts.prototype.model.player.IPlayer;

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
}
