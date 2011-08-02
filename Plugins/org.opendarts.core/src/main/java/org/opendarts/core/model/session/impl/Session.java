package org.opendarts.core.model.session.impl;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArraySet;

import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISessionListener;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.model.session.ISetListener;
import org.opendarts.core.model.session.SessionEvent;
import org.opendarts.core.model.session.SetEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class Session.
 */
public class Session extends GameContainer<ISet> implements ISession,
		ISetListener {

	/** The logger. */
	private static final Logger LOG = LoggerFactory.getLogger(Session.class);

	/** The listeners. */
	private final CopyOnWriteArraySet<ISessionListener> listeners;

	/** The player games. */
	private final Map<IPlayer, Integer> playerGames;

	/**
	 * Instantiates a new session.
	 */
	public Session() {
		super();
		this.listeners = new CopyOnWriteArraySet<ISessionListener>();
		this.playerGames = new HashMap<IPlayer, Integer>();
	}

	/**
	 * Inits the.
	 */
	@Override
	public void init() {
		this.setStart(Calendar.getInstance());
		this.fireSessionEvent(SessionEvent.Factory.newSessionInitializedEvent(this));
	}
	
	/* (non-Javadoc)
	 * @see org.opendarts.core.model.session.ISession#finish()
	 */
	@Override
	public void finish() {
		this.setEnd(Calendar.getInstance());
		IPlayer sessionWinner = null;
		int max = 0;
		for (Entry<IPlayer,Integer> entry : this.playerGames.entrySet()) {
			if (entry.getValue()>max) {
				sessionWinner = entry.getKey();
			}
		}
		this.fireSessionEvent(SessionEvent.Factory.newSessionFinishEvent(this, sessionWinner, getCurrentGame()));
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.session.ISession#getWinningSet(org.opendarts.prototype.model.player.IPlayer)
	 */
	@Override
	public int getWinningSet(IPlayer player) {
		int result = 0;
		if (this.playerGames.containsKey(player)) {
			result = this.playerGames.get(player);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result;
		if (this.isFinished()) {
			result = MessageFormat.format(
					"Session [{0,date,medium} - {1,date,medium}]", this
							.getStart().getTime(), this.getEnd().getTime());
		} else {
			result = "Current Session";
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.session.GameContainer#addGame(java.lang.Object)
	 */
	@Override
	public void addGame(ISet set) {
		super.addGame(set);
		set.addListener(this);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.session.ISession#addListener(org.opendarts.prototype.model.session.ISessionListener)
	 */
	@Override
	public void addListener(ISessionListener listener) {
		this.listeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.session.ISetListener#notifySetEvent(org.opendarts.prototype.model.session.SetEvent)
	 */
	@Override
	public void notifySetEvent(SetEvent event) {
		ISet set = event.getSet();
		if (this.getInternalsGame().contains(set)) {
			switch (event.getType()) {
				case SET_CANCELED:
					set.removeListener(this);
					break;
				case SET_FINISHED:
					IPlayer win = set.getWinner();
					int winningSet = this.getWinningSet(win);
					this.playerGames.put(win, winningSet + 1);
					break;
				case SET_INITIALIZED:
					this.setCurrentGame(set);
					this.fireSessionEvent(SessionEvent.Factory.newSessionSetEvent(this, set));
					break;
				case NEW_CURRENT_GAME:
					break;
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.session.ISession#removeListener(org.opendarts.prototype.model.session.ISessionListener)
	 */
	@Override
	public void removeListener(ISessionListener listener) {
		this.listeners.remove(listener);
	}

	/**
	 * Fire session event.
	 *
	 * @param event the event
	 */
	protected void fireSessionEvent(final SessionEvent event) {
		for (final ISessionListener listener : this.listeners) {
			try {
				listener.notifySessionEvent(event);
			} catch (Throwable t) {
				LOG.error("Error when sending game event: " + event, t);
			}
		}
	}
}
