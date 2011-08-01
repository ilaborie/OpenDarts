package org.opendarts.core.stats.model.impl;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;

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
