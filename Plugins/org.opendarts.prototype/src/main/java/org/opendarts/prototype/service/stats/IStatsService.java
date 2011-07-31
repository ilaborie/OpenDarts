package org.opendarts.prototype.service.stats;

import java.util.Map;

import org.opendarts.prototype.internal.model.game.x01.GameX01;
import org.opendarts.prototype.internal.model.session.GameSet;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.game.IGameEntry;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.model.session.ISession;
import org.opendarts.prototype.model.session.ISet;
import org.opendarts.prototype.model.stats.IStats;
import org.opendarts.prototype.model.stats.IStatsEntry;

/**
 * The Interface IStatsService.
 */
public interface IStatsService {

	/**
	 * Gets the session stats.
	 *
	 * @param session the session
	 * @return the session stats
	 */
	Map<IPlayer, IStats<ISession>> getSessionStats(ISession session);

	/**
	 * Gets the sets the stats.
	 *
	 * @param set the set
	 * @return the sets the stats
	 */
	Map<IPlayer, IStats<ISet>> getSetStats(ISet set);

	/**
	 * Gets the game stats.
	 *
	 * @param game the game
	 * @return the game stats
	 */
	Map<IPlayer, IStats<IGame>> getGameStats(IGame game);

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
	IStatsEntry getStatsEntry(ISession session, GameSet set, GameX01 game,
			IPlayer player, String statsKey);

}
