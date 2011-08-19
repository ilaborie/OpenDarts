package org.opendarts.core.model.session.impl;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import org.opendarts.core.internal.service.SessionService;
import org.opendarts.core.model.game.IGameDefinition;
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

	/** The nb set to win. */
	private final int nbSetToWin;

	/** The game definition. */
	private final IGameDefinition gameDefinition;

	/** The session service. */
	private final SessionService sessionService;

	/**
	 * Instantiates a new session.
	 *
	 * @param sessionService the session service
	 */
	public Session(SessionService sessionService) {
		this(sessionService, -1, null);
	}

	/**
	 * Instantiates a new session.
	 *
	 * @param sessionService the session service
	 * @param nbSet the nb set
	 * @param gameDefinition the game definition
	 */
	public Session(SessionService sessionService, int nbSet,
			IGameDefinition gameDefinition) {
		super();
		this.sessionService = sessionService;
		this.nbSetToWin = nbSet;
		this.gameDefinition = gameDefinition;
		this.listeners = new CopyOnWriteArraySet<ISessionListener>();
		this.playerGames = new HashMap<IPlayer, Integer>();
	}

	/**
	 * Inits the.
	 */
	@Override
	public void init() {
		this.setStart(Calendar.getInstance());
		this.fireSessionEvent(SessionEvent.Factory
				.newSessionInitializedEvent(this));
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.model.session.ISession#finish()
	 */
	@Override
	public void finish(IPlayer winner) {
		this.setEnd(Calendar.getInstance());
		this.setWinner(winner);
		this.fireSessionEvent(SessionEvent.Factory.newSessionFinishEvent(this,
				this.getWinner(), this.getCurrentGame()));
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
					"Session [{0,time,medium} - {1,time,medium}]", this
							.getStart().getTime(), this.getEnd().getTime());
		} else {
			result = MessageFormat.format(
					"Current Session [{0,time,medium} - ...]", this.getStart()
							.getTime());
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
				case SET_INITIALIZED:
					this.setCurrentGame(set);
					this.fireSessionEvent(SessionEvent.Factory
							.newSessionSetEvent(this, set));
					break;
				case SET_FINISHED:
				case NEW_CURRENT_GAME:
					break;
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.model.session.ISession#handleSetFinished(org.opendarts.core.model.session.ISet)
	 */
	@Override
	public void handleSetFinished(ISet set) {
		set.removeListener(this);
		IPlayer win = set.getWinner();
		int winningSet = this.getWinningSet(win);
		winningSet++;
		this.playerGames.put(win, winningSet);
		this.sessionService.handleFinishedSet(set);
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

	/**
	 * Gets the nb set to win.
	 *
	 * @return the nb set to win
	 */
	public int getNbSetToWin() {
		return this.nbSetToWin;
	}

	/**
	 * Gets the game definition.
	 *
	 * @return the game definition
	 */
	public IGameDefinition getGameDefinition() {
		return this.gameDefinition;
	}

}
