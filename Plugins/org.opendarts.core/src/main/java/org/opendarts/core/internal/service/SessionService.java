package org.opendarts.core.internal.service;

import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.impl.Session;
import org.opendarts.core.service.session.ISessionService;

/**
 * The Class SessionService.
 */
public class SessionService implements ISessionService {

	/** The current session. */
	private Session currentSession;

	/**
	 * Instantiates a new session service.
	 */
	public SessionService() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.ISessionService#getSession()
	 */
	@Override
	public ISession getSession() {
		ISession result = this.currentSession;
		if (result == null) {
			this.currentSession = this.createSession();
			result = this.currentSession;
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.ISessionService#closeSession()
	 */
	@Override
	public void closeSession() {
		if (this.currentSession != null) {
			this.currentSession.finish();
			this.currentSession = null;
		}
	}

	/**
	 * Creates the session.
	 *
	 * @return the i session
	 */
	private Session createSession() {
		Session session= new Session();
		session.init();
		return session;
	}

}
