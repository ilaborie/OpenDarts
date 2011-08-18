package org.opendarts.core.service.session;

import org.opendarts.core.model.game.IGameDefinition;
import org.opendarts.core.model.session.ISession;

/**
 * The Interface SessionService.
 */
public interface ISessionService {

	/**
	 * Close session.
	 *
	 * @param session the session
	 */
	void closeSession();

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	ISession getSession();

	/**
	 * Creates the new session.
	 *
	 * @param nbSets the nb sets
	 * @return the i session
	 */
	ISession createNewSession(int nbSets,IGameDefinition gameDefinition);

}
