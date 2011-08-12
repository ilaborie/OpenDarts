package org.opendarts.core.stats.service;

import java.util.List;
import java.util.Set;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;

/**
 * The Interface IStatsProvider.
 */
public interface IStatsProvider {

	/**
	 * Gets the session stats.
	 *
	 * @param session the session
	 * @return the session stats
	 */
	List<IStatsService> getSessionStats(ISession session);

	/**
	 * Gets the sets the stats.
	 *
	 * @param set the set
	 * @return the sets the stats
	 */
	List<IStatsService> getSetStats(ISet set);

	/**
	 * Gets the game stats.
	 *
	 * @param game the game
	 * @return the game stats
	 */
	List<IStatsService> getGameStats(IGame game);

	/**
	 * Register stats service.
	 *
	 * @param session the session
	 * @param statsService the stats service
	 */
	void registerStatsService(ISession session, IStatsService statsService);

	/**
	 * Register stats service.
	 *
	 * @param set the set
	 * @param statsService the stats service
	 */
	void registerStatsService(ISet set, IStatsService statsService);

	/**
	 * Register stats service.
	 *
	 * @param game the game
	 * @param statsService the stats service
	 */
	void registerStatsService(IGame game, IStatsService statsService);

	/**
	 * Gets the all stats service.
	 *
	 * @return the all stats service
	 */
	Set<IStatsService> getAllStatsService();

}
