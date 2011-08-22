package org.opendarts.core.internal.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class SessionService.
 */
public class SessionService implements ISessionService, ISessionListener {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(SessionService.class);

	/** The current session. */
	private Session currentSession;

	/** The sessions. */
	private final List<ISession> sessions;

	/** The set service. */
	private final AtomicReference<ISetService> setService;

	/** The listeners. */
	private final CopyOnWriteArraySet<ISessionListener> listeners;

	/**
	 * Instantiates a new session service.
	 */
	public SessionService() {
		super();
		this.sessions = new ArrayList<ISession>();
		this.setService = new AtomicReference<ISetService>();
		this.listeners = new CopyOnWriteArraySet<ISessionListener>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opendarts.prototype.model.session.ISession#addListener(org.opendarts
	 * .prototype.model.session.ISessionListener)
	 */
	@Override
	public void addListener(ISessionListener listener) {
		this.listeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opendarts.prototype.model.session.ISession#removeListener(org.opendarts
	 * .prototype.model.session.ISessionListener)
	 */
	@Override
	public void removeListener(ISessionListener listener) {
		this.listeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opendarts.prototype.service.ISessionService#getSession()
	 */
	@Override
	public ISession getCurrentSession() {
		if (this.currentSession == null) {
			Session session = new Session(this);
			this.initializeSession(session);
		}
		return this.currentSession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opendarts.core.service.session.ISessionService#getAllSessions()
	 */
	@Override
	public List<ISession> getAllSessions() {
		return Collections.unmodifiableList(this.sessions);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opendarts.prototype.service.ISessionService#closeSession()
	 */
	@Override
	public void closeSession() {
		if (this.currentSession != null) {
			this.currentSession.finish(null);
			this.currentSession = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opendarts.core.service.session.ISessionService#createNewSession(int)
	 */
	@Override
	public ISession createNewSession(int nbSets, IGameDefinition gameDefinition) {
		this.closeSession();
		Session session = new Session(this, nbSets, gameDefinition);
		this.initializeSession(session);
		return session;
	}

	/**
	 * Initialize session.
	 *
	 * @param session the session
	 */
	private void initializeSession(Session session) {
		this.sessions.add(session);
		this.currentSession = session;
		this.currentSession.addListener(this);
		this.currentSession.init();
		this.fireSessionCreated(this.currentSession);
	}

	/**
	 * Notify session event.
	 * 
	 * @param event
	 *            the event
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
	 * @param event
	 *            the event
	 * @param set
	 *            the set
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
	 * Fire session created.
	 * 
	 * @param session
	 *            the session
	 */
	private void fireSessionCreated(Session session) {
		for (final ISessionListener listener : this.listeners) {
			try {
				listener.sessionCreated(session);
			} catch (Throwable t) {
				LOG.error("Error when sending session created event: "
						+ session, t);
			}
		}
	}

	/**
	 * Sets the sets the service.
	 * 
	 * @param setService
	 *            the new sets the service
	 */
	public void setSetService(ISetService setService) {
		this.setService.set(setService);
	}

	/**
	 * Unset set service.
	 * 
	 * @param setService
	 *            the set service
	 */
	public void unsetSetService(ISetService setService) {
		this.setService.compareAndSet(setService, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opendarts.core.model.session.ISessionListener#sessionCreated(org.
	 * opendarts.core.model.session.ISession)
	 */
	@Override
	public void sessionCreated(ISession session) {
		// Nothing to do
	}
}
