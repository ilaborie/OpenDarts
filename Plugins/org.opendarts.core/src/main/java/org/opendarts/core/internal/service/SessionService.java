package org.opendarts.core.internal.service;

import java.util.concurrent.atomic.AtomicReference;

import org.opendarts.core.model.game.IGameDefinition;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISessionListener;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.model.session.ISetListener;
import org.opendarts.core.model.session.SessionEvent;
import org.opendarts.core.model.session.SetEvent;
import org.opendarts.core.model.session.impl.Session;
import org.opendarts.core.service.session.ISessionService;
import org.opendarts.core.service.session.ISetService;

/**
 * The Class SessionService.
 */
public class SessionService implements ISessionService, ISetListener,
		ISessionListener {

	/** The current session. */
	private Session currentSession;

	/** The set service. */
	private final AtomicReference<ISetService> setService;

	/**
	 * Instantiates a new session service.
	 */
	public SessionService() {
		super();
		this.setService = new AtomicReference<ISetService>();
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

	/* (non-Javadoc)
	 * @see org.opendarts.core.service.session.ISessionService#createNewSession(int)
	 */
	@Override
	public ISession createNewSession(int nbSets, IGameDefinition gameDefinition) {
		this.closeSession();
		Session session = new Session(nbSets, gameDefinition);
		session.addListener(this);
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
			case NEW_CURRENT_SET:
				ISet set = event.getSet();
				set.addListener(this);
				break;
			case SESSION_CANCELED:
			case SESSION_FINISHED:
				session.removeListener(this);
				break;
			case SESSION_INITIALIZED:
			default:
				break;
		}
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.model.session.ISetListener#notifySetEvent(org.opendarts.core.model.session.SetEvent)
	 */
	@Override
	public void notifySetEvent(SetEvent event) {
		ISet set = event.getSet();
		switch (event.getType()) {
			case SET_FINISHED:
				set.removeListener(this);
				Session session = (Session) set.getParentSession();
				int winningSet = session.getWinningSet(event.getPlayer());
				if ((session.getNbSetToWin() > 0)
						&& (winningSet < session.getNbSetToWin())) {
					ISetService service = this.setService.get();
					ISet newSet = service.createNewSet(session,
							session.getGameDefinition());
					newSet.addListener(this);
					service.startSet(newSet);
				}
				break;
			case SET_INITIALIZED:
			case SET_CANCELED:
			default:
				break;
		}
	}

	/**
	 * Creates the session.
	 *
	 * @return the i session
	 */
	private Session createSession() {
		Session session = new Session();
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
