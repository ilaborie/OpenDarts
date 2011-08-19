package org.opendarts.core.service.session;

import java.util.List;

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
	ISession getCurrentSession();

	/**
	 * Gets the all sessions.
	 *
	 * @return the all sessions
	 */
	List<ISession> getAllSessions();

	/**
	 * Creates the new session.
	 *
	 * @param nbSets the nb sets
	 * @return the i session
	 */
	ISession createNewSession(int nbSets, IGameDefinition gameDefinition);

}
