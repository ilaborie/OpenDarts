package org.opendarts.core.x01.service;

import org.opendarts.core.model.dart.IDart;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;

/**
 * The Interface IBestDart.
 */
public interface IBestDart {

	/**
	 * Gets the best dart.
	 *
	 * @param player the player
	 * @param score the score
	 * @param game the game
	 * @return the best dart
	 */
	IDart getBestDart(IPlayer player, int score, IGame game);

}
