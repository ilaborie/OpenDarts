package org.opendarts.core.service.game;

import java.util.List;

import org.opendarts.core.model.dart.IDart;
import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISet;

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
	void addPlayerThrow(IGame game, IPlayer player, IDartsThrow dartThrow);

	/**
	 * Adds the winning player throw.
	 *
	 * @param game the game
	 * @param player the player
	 * @param dartThrow the dart throw
	 */
	void addWinningPlayerThrow(IGame game, IPlayer player,
			IDartsThrow dartThrow);

	/**
	 * Choose best dart.
	 *
	 * @param score the score
	 * @param nbDartLeft the number of dart left
	 * @return the i dart
	 */
	IDart chooseBestDart(int score, int nbDartLeft);

	/**
	 * Cancel game.
	 *
	 * @param game the game
	 */
	void cancelGame(IGame game);
}
