package org.opendarts.prototype.service.session;

import org.opendarts.prototype.model.session.ISession;

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

}
