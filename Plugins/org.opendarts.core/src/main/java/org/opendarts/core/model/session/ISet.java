package org.opendarts.core.model.session;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameDefinition;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.service.game.IGameService;

/**
 * The Interface ISet.
 */
public interface ISet extends IGameContainer<IGame> {

	/**
	 * Inits the set.
	 */
	void initSet();

	/**
	 * Gets the game definition.
	 *
	 * @return the game definition
	 */
	IGameDefinition getGameDefinition();

	/**
	 * Gets the winning game.
	 *
	 * @param player the player
	 * @return the winning game
	 */
	int getWinningGames(IPlayer player);

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	ISession getParentSession();

	/**
	 * Adds the listener.
	 *
	 * @param listener the listener
	 */
	void addListener(ISetListener listener);

	/**
	 * Removes the listener.
	 *
	 * @param listener the listener
	 */
	void removeListener(ISetListener listener);

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	String getDescription();

	/**
	 * Gets the winning message.
	 *
	 * @return the winning message
	 */
	String getWinningMessage();

	/**
	 * Gets the game service.
	 *
	 * @return the game service
	 */
	IGameService getGameService();

}
