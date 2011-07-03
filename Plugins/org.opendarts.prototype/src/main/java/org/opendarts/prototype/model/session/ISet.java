package org.opendarts.prototype.model.session;

import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.game.IGameDefinition;


/**
 * The Interface ISet.
 */
public interface ISet extends IGameContainer<IGame> {

	/**
	 * Gets the game definition.
	 *
	 * @return the game definition
	 */
	IGameDefinition getGameDefinition();
}
