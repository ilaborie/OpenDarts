package org.opendarts.prototype.service;

import java.util.List;

import org.opendarts.prototype.model.IGame;
import org.opendarts.prototype.model.IGameDefinition;
import org.opendarts.prototype.model.IPlayer;
import org.opendarts.prototype.model.ISet;

/**
 * The Interface IGameService.
 */
public interface IGameService {

	/**
	 * Creates the game.
	 *
	 * @param set the set
	 * @param gameDefinition the game definition
	 * @return the i game
	 */
	IGame createGame(ISet set, IGameDefinition gameDefinition,
			List<IPlayer> players);

	/**
	 * Start game.
	 *
	 * @param game the game
	 */
	void startGame(IGame game);

}
