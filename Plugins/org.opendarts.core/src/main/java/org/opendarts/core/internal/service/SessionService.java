package org.opendarts.core.internal.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.opendarts.core.model.game.IGameDefinition;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISessionListener;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.model.session.SessionEvent;
import org.opendarts.core.model.session.impl.Session;
import org.opendarts.core.service.session.ISessionService;
import org.opendarts.core.service.session.ISetService;

/**
 * The Class SessionService.
 */
public class SessionService implements ISessionService, ISessionListener {

	/** The current session. */
	private Session currentSession;

	/** The sessions. */
	private final List<ISession> sessions;

	/** The set service. */
	private final AtomicReference<ISetService> setService;

	/**
	 * Instantiates a new session service.
	 */
	public SessionService() {
		super();
		this.sessions = new ArrayList<ISession>();
		this.setService = new AtomicReference<ISetService>();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.ISessionService#getSession()
	 */
	@Override
	public ISession getCurrentSession() {
		ISession result = this.currentSession;
		if (result == null) {
			this.currentSession = this.createSession();
			result = this.currentSession;
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.service.session.ISessionService#getAllSessions()
	 */
	@Override
	public List<ISession> getAllSessions() {
		return Collections.unmodifiableList(this.sessions);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.ISessionService#closeSession()
	 */
	@Override
	public void closeSession() {
		if (this.currentSession != null) {
			this.currentSession.finish(null);
			this.currentSession = null;
		}
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.service.session.ISessionService#createNewSession(int)
	 */
	@Override
	public ISession createNewSession(int nbSets, IGameDefinition gameDefinition) {
		this.closeSession();
		Session session = new Session(this, nbSets, gameDefinition);
		this.sessions.add(session);
		session.addListener(this);
		this.currentSession = session;
		session.init();
		return session;
	}

	/**
	 * Notify session event.
	 *
	 * @param event the event
	 */
	@Override
	public void notifySessionEvent(SessionEvent event) {
		ISession session = event.getSession();
		switch (event.getType()) {
			case SESSION_CANCELED:
			case SESSION_FINISHED:
				session.removeListener(this);
				break;
			case NEW_CURRENT_SET:
			case SESSION_INITIALIZED:
			default:
				break;
		}
	}

	/**
	 * Handle finished set.
	 *
	 * @param event the event
	 * @param set the set
	 */
	public void handleFinishedSet(ISet set) {
		Session session = (Session) set.getParentSession();
		IPlayer winner = set.getWinner();
		int winningSet = session.getWinningSet(winner);
		if (session.getNbSetToWin() <= 0) {
			// Do nothing
		} else if (winningSet < session.getNbSetToWin()) {
			ISetService service = this.setService.get();
			ISet newSet = service.createNewSet(session,
					session.getGameDefinition());
			service.startSet(newSet);
		} else if (winningSet < session.getNbSetToWin()) {
			session.finish(winner);
		}
	}

	/**
	 * Creates the session.
	 *
	 * @return the i session
	 */
	private Session createSession() {
		Session session = new Session(this);
		this.sessions.add(session);
		session.init();
		return session;
	}

	/**
	 * Sets the sets the service.
	 *
	 * @param setService the new sets the service
	 */
	public void setSetService(ISetService setService) {
		this.setService.set(setService);
	}

	/**
	 * Unset set service.
	 *
	 * @param setService the set service
	 */
	public void unsetSetService(ISetService setService) {
		this.setService.compareAndSet(setService, null);
	}
}
