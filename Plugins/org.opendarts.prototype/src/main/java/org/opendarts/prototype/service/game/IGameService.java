package org.opendarts.prototype.service.game;

import java.util.List;

import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.game.IGameDefinition;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.model.session.ISet;

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
