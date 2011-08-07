package org.opendarts.core.service.game;

import java.util.List;

import org.opendarts.core.model.dart.IComputerThrow;
import org.opendarts.core.model.dart.IDart;
import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IComputerPlayer;
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
	void addWinningPlayerThrow(IGame game, IPlayer player, IDartsThrow dartThrow);
	
	/**
	 * Gets the computer darts throw.
	 *
	 * @param game the game
	 * @param player the player
	 * @return the computer darts throw
	 */
	IComputerThrow getComputerDartsThrow(IGame game, IComputerPlayer player); 

	/**
	 * Cancel game.
	 *
	 * @param game the game
	 */
	void cancelGame(IGame game);

	/**
	 * Choose best dart.
	 *
	 * @param player the player
	 * @param score the score
	 * @param nbDart the nb dart
	 * @return the  dart
	 */
	IDart chooseBestDart(IComputerPlayer player, int score, int nbDart);
}
