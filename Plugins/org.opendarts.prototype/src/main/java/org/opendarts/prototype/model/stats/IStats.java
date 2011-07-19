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
	Map<String, IStatEntry<?>> getAllEntries();

	/**
	 * Gets the entry.
	 *
	 * @param key the key
	 * @return the entry
	 */
	<U> IStatEntry<U> getEntry(String key);
}
