package org.opendarts.prototype.model.game;

import java.util.List;

import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.model.session.ISet;
import org.opendarts.prototype.service.game.IGameService;
import org.opendarts.prototype.service.stats.IStatsService;

/**
 * The Interface IGameDefinition.
 */
public interface IGameDefinition {
	// Mostly depends on GameX01 type

	/** The N b_ gam e_ t o_ win. */
	String NB_GAME_TO_WIN = "nbGameToWin";

	/** The PLA y_ al l_ game. */
	String PLAY_ALL_GAME = "playAllGame";

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	List<IPlayer> getPlayers();

	/**
	 * Gets the next players.
	 *
	 * @param set the set
	 * @return the next players
	 */
	List<IPlayer> getNextPlayers(ISet set);

	/**
	 * Checks if is end.
	 *
	 * @param set the set
	 * @param player the player
	 * @return true, if is end
	 */
	boolean isPlayerWin(ISet set, IPlayer player);

	/**
	 * Checks if is sets the finished.
	 *
	 * @param set the set
	 * @return true, if is sets the finished
	 */
	boolean isSetFinished(ISet set);

	/**
	 * Gets the game service.
	 *
	 * @return the game service
	 */
	IGameService getGameService();

	/**
	 * Gets the i stats service.
	 *
	 * @return the i stats service
	 */
	IStatsService getIStatsService();
}
