package org.opendarts.prototype.internal.model.stats;

import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.player.IPlayer;

/**
 * The Class GameStats.
 */
public class GameStats extends AbstractStats<IGame> {

	/**
	 * Instantiates a new game stats.
	 *
	 * @param game the game
	 * @param player the player
	 */
	public GameStats(IGame game, IPlayer player) {
		super(game, player);
	}

}
