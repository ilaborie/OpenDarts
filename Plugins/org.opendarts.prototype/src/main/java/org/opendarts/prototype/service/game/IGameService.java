package org.opendarts.prototype.service.game;

import java.util.List;

import org.opendarts.prototype.internal.model.game.x01.GameX01;
import org.opendarts.prototype.model.dart.IDartsThrow;
import org.opendarts.prototype.model.game.IGame;
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
	IGame createGame(ISet set, List<IPlayer> players);

	/**
	 * Start game.
	 *
	 * @param game the game
	 */
	void startGame(IGame game);

	/**
	 * Adds the player throw.
	 *
	 * @param game the game
	 * @param player the player
	 * @param dartThrow the dart throw
	 */
	void addPlayerThrow(GameX01 game, IPlayer player, IDartsThrow dartThrow);

	/**
	 * Adds the winning player throw.
	 *
	 * @param game the game
	 * @param player the player
	 * @param dartThrow the dart throw
	 */
	void addWinningPlayerThrow(GameX01 game, IPlayer player,
			IDartsThrow dartThrow);

}
