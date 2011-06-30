package org.opendarts.prototype.model;

import java.util.List;

/**
 * The Interface IGame.
 */
public interface IGame extends IAbstractGame {
	
	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	List<IPlayer> getPlayers();

	/**
	 * Gets the game definition.
	 *
	 * @return the game definition
	 */
	IGameDefinition getGameDefinition();

	String getName();

	String getDescription();

}
