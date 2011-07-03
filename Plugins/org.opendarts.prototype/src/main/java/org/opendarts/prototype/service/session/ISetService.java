package org.opendarts.prototype.service.session;

import org.opendarts.prototype.model.game.IGameDefinition;
import org.opendarts.prototype.model.session.ISession;
import org.opendarts.prototype.model.session.ISet;

/**
 * The Interface ISetService.
 */
public interface ISetService {

	/**
	 * Creates the new set.
	 *
	 * @param session the session
	 * @param gameDefinition the game definition
	 * @return the i set
	 */
	ISet createNewSet(ISession session, IGameDefinition gameDefinition);

}
