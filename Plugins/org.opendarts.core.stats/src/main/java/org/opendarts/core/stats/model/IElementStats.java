package org.opendarts.core.stats.model;

import java.util.List;
import java.util.Map;

import org.opendarts.core.model.player.IPlayer;

/**
 * The Interface SessionStats.
 *
 * @param <E> the element
 */
public interface IElementStats<E> {

	/**
	 * The Interface IEntry.
	 *
	 * @param <V> the value type
	 */
	public interface IEntry<V> {

		/**
		 * Gets the key.
		 *
		 * @return the key
		 */
		String getKey();

		/**
		 * Gets the player entry.
		 *
		 * @param player the player
		 * @return the player entry
		 */
		IStatsEntry<V> getPlayerEntry(IPlayer player);

		/**
		 * Gets the best value.
		 *
		 * @return the best value
		 */
		IStatValue<V> getBestValue();

		/**
		 * Checks if is visible.
		 *
		 * @return true, if is visible
		 */
		boolean isVisible();
	}

	/**
	 * Gets the element.
	 *
	 * @return the element
	 */
	E getElement();

	/**
	 * Gets the stats keys.
	 *
	 * @return the stats keys
	 */
	List<String> getStatsKeys();

	/**
	 * Gets the stats entry.
	 *
	 * @param player the player
	 * @param key the key
	 * @return the stats entry
	 */
	<V> IStatsEntry<V> getStatsEntry(IPlayer player, String key);
	
	/**
	 * Gets the stats entries.
	 *
	 * @param key the key
	 * @return the stats entries
	 */
	<V> Map<IPlayer,IStatsEntry<V>> getStatsEntries(String key);

	/**
	 * Gets the player stats.
	 *
	 * @param player the player
	 * @return the player stats
	 */
	IStats<E> getPlayerStats(IPlayer player);

	/**
	 * Gets the stats entries.
	 *
	 * @return the stats entries
	 */
	List<IEntry<E>> getStatsEntries();

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	List<IPlayer> getPlayers();

}
