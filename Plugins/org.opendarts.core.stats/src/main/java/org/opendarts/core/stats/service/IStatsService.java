package org.opendarts.core.stats.service;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.model.IElementStats;
import org.opendarts.core.stats.model.IStatsEntry;

/**
 * The Interface IStatsService.
 */
public interface IStatsService {

	/**
	 * Update stats.
	 *
	 * @param key the key
	 * @param value the value
	 */
	void updateStats(IPlayer player, IGame game, IGameEntry entry);

	/**
	 * Undo stats.
	 *
	 * @param player the player
	 * @param game the game
	 * @param entry the entry
	 */
	void undoStats(IPlayer player, IGame game, IGameEntry entry);

	/**
	 * Adds the stats listener.
	 *
	 * @param <T> the generic type
	 * @param listener the listener
	 */
	<T> void addStatsListener(IStatsListener<T> listener);

	/**
	 * Removes the stats listener.
	 *
	 * @param <T> the generic type
	 * @param listener the listener
	 */
	<T> void removeStatsListener(IStatsListener<T> listener);

	/**
	 * Gets the sets the stat entry.
	 *
	 * @param player the player
	 * @param statsKey the stats key
	 * @return the sets the stat entry
	 */
	@SuppressWarnings("rawtypes")
	IStatsEntry getSetStatEntry(IPlayer player, String statsKey);
	/**
	 * Gets the stats entry.
	 *
	 * @param session the session
	 * @param set the set
	 * @param game the game
	 * @param player the player
	 * @param statsKey the stats key
	 * @return the stats entry
	 */
	@SuppressWarnings("rawtypes")
	IStatsEntry getStatsEntry(ISession session, ISet set, IGame game,
			IPlayer player, String statsKey);

	/**
	 * Gets the session stats.
	 *
	 * @param session the session
	 * @return the session stats
	 */
	IElementStats<ISession> getSessionStats(ISession session);
	
	/**
	 * Gets the sets the stats.
	 *
	 * @param set the set
	 * @return the sets the stats
	 */
	IElementStats<ISet> getSetStats(ISet set);
	
	/**
	 * Gets the game stats.
	 *
	 * @param game the game
	 * @return the game stats
	 */
	IElementStats<IGame> getGameStats(IGame game);
}
