package org.opendarts.prototype.internal.model.session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.model.session.ISession;
import org.opendarts.prototype.model.session.ISessionListener;
import org.opendarts.prototype.model.session.ISet;
import org.opendarts.prototype.model.session.SessionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class Session.
 */
public class Session extends GameContainer<ISet> implements ISession {

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
	 * @see org.opendarts.prototype.model.session.ISession#addListener(org.opendarts.prototype.model.session.ISessionListener)
	 */
	@Override
	public void addListener(ISessionListener listener) {
		this.listeners.add(listener);
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
