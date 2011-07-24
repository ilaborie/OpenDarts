package org.opendarts.prototype.service.stats;

import java.util.Map;

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

}
