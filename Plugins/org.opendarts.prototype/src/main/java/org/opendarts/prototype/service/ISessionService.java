package org.opendarts.prototype.service;

import org.opendarts.prototype.model.ISession;

/**
 * The Interface SessionService.
 */
public interface ISessionService {

	/**
	 * Close session.
	 *
	 * @param session the session
	 */
	void closeSession(ISession session);

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	ISession getSession();


}
