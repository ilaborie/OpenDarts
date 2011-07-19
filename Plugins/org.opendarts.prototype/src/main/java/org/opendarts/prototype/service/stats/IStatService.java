package org.opendarts.prototype.service.stats;

import org.opendarts.prototype.model.dart.IDartsThrow;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.model.session.ISession;
import org.opendarts.prototype.model.session.ISet;
import org.opendarts.prototype.model.stats.IStats;

/**
 * The Interface IStatService.
 */
public interface IStatService {

	/**
	 * Gets the session stats.
	 *
	 * @param session the session
	 * @return the session stats
	 */
	IStats<ISession> getSessionStats(ISession session);
	
	/**
	 * Gets the sets the stats.
	 *
	 * @param set the set
	 * @return the sets the stats
	 */
	IStats<ISet> getSetStats(ISet set);
	
	/**
	 * Gets the game stats.
	 *
	 * @param game the game
	 * @return the game stats
	 */
	IStats<IGame> getGameStats(IGame game);
	
	/**
	 * Update stats.
	 *
	 * @param <T> the generic type
	 * @param <U> the generic type
	 * @param stats the stats
	 * @param key the key
	 * @param value the value
	 */
	<T, U> void updateStats(IStats<T> stats, IPlayer player,
			IDartsThrow dartsThrow);

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
}
