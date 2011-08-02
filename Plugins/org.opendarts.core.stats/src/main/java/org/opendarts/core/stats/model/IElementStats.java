package org.opendarts.core.stats.model;

import java.util.List;

import org.opendarts.core.model.player.IPlayer;

/**
 * The Interface SessionStats.
 *
 * @param <E> the element
 */
public interface IElementStats<E> {

	public interface IEntry<E> {
		String getKey();
		
		IStatsEntry<E> getPlayerEntry(IPlayer player);
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
	 * @return the stats entry
	 */
	IStatsEntry<E> getStatsEntry(IPlayer player,String key);
	
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
