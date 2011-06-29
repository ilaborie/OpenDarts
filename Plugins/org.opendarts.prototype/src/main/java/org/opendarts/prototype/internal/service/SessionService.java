package org.opendarts.prototype.internal.service;

import java.util.Calendar;

import org.opendarts.prototype.internal.model.Session;
import org.opendarts.prototype.model.ISession;
import org.opendarts.prototype.service.ISessionService;

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
		ISession result = currentSession;
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
			this.currentSession.setEnd(Calendar.getInstance());
			this.currentSession = null;
		}
	}

	/**
	 * Creates the session.
	 *
	 * @return the i session
	 */
	private Session createSession() {
		return new Session();
	}

}
