package org.opendarts.prototype.internal.model.game;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.opendarts.prototype.internal.model.session.GameSet;
import org.opendarts.prototype.model.game.GameEvent;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.game.IGameEntry;
import org.opendarts.prototype.model.game.IGameListener;
import org.opendarts.prototype.model.player.IPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractGame implements IGame {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(AbstractGame.class);

	/** The listeners. */
	private final CopyOnWriteArraySet<IGameListener> listeners;

	/** The winner. */
	private IPlayer winner;

	/** The current player. */
	private IPlayer currentPlayer;

	/** The current entry. */
	private IGameEntry currentEntry;

	/** The players. */
	private final List<IPlayer> players;

	/** The set. */
	private final GameSet set;

	/** The start. */
	private Calendar start;

	/** The end. */
	private Calendar end;

	/**
	 * Instantiates a new abstract game.
	 */
	public AbstractGame(GameSet set, List<IPlayer> players) {
		super();
		this.set = set;
		this.players = new ArrayList<IPlayer>(players);
		this.listeners = new CopyOnWriteArraySet<IGameListener>();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.IAbstractGame#getStart()
	 */
	@Override
	public Calendar getStart() {
		return this.start;
	}

	/**
	 * Sets the start.
	 *
	 * @param start the new start
	 */
	protected void setStart(Calendar start) {
		this.start = start;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.game.IGame#isStarted()
	 */
	@Override
	public boolean isStarted() {
		return (this.start != null);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.IAbstractGame#isFinished()
	 */
	@Override
	public boolean isFinished() {
		return (this.end != null);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.IAbstractGame#getEnd()
	 */
	@Override
	public Calendar getEnd() {
		return this.end;
	}

	/**
	 * End.
	 *
	 * @param player the player
	 */
	protected void end(IPlayer player) {
		this.winner = player;
		this.end = Calendar.getInstance();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.IAbstractGame#getWinner()
	 */
	@Override
	public IPlayer getWinner() {
		return this.winner;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.IGame#getPlayers()
	 */
	@Override
	public List<IPlayer> getPlayers() {
		return Collections.unmodifiableList(this.players);
	}

	/**
	 * Gets the sets the.
	 *
	 * @return the sets the
	 */
	public GameSet getSet() {
		return this.set;
	}
	
	/**
	 * Gets the current player.
	 *
	 * @return the current player
	 */
	public IPlayer getCurrentPlayer() {
		return this.currentPlayer;
	}

	/**
	 * Sets the current player.
	 *
	 * @param player the new current player
	 */
	protected void setCurrentPlayer(IPlayer player) {
		this.currentPlayer = player;
		this.fireGameEvent(GameEvent.Factory.newCurrentPlayerEvent(this,
				this.currentPlayer, this.currentEntry));
	}

	/**
	 * Gets the current entry.
	 *
	 * @return the current entry
	 */
	public IGameEntry getCurrentEntry() {
		return this.currentEntry;
	}

	/**
	 * Sets the current entry.
	 *
	 * @param entry the new current entry
	 */
	protected void setCurrentEntry(IGameEntry entry) {
		this.currentEntry = entry;
		this.fireGameEvent(GameEvent.Factory.newGameEntryCreatedEvent(this,
				entry));
	}

	/**
	 * Fire game event.
	 *
	 * @param event the event
	 */
	protected void fireGameEvent(final GameEvent event) {
		for (final IGameListener listener : listeners) {
			SafeRunner.run(new ISafeRunnable() {

				/* (non-Javadoc)
				 * @see org.eclipse.core.runtime.ISafeRunnable#run()
				 */
				@Override
				public void run() throws Exception {
					listener.notifyGameEvent(event);
				}

				/* (non-Javadoc)
				 * @see org.eclipse.core.runtime.ISafeRunnable#handleException(java.lang.Throwable)
				 */
				@Override
				public void handleException(Throwable e) {
					LOG.error("Error when sending game event: " + event, e);
				}
			});
		}
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.game.IGame#addListener(org.opendarts.prototype.model.game.IGameListener)
	 */
	@Override
	public void addListener(IGameListener listener) {
		this.listeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.game.IGame#removeListener(org.opendarts.prototype.model.game.IGameListener)
	 */
	@Override
	public void removeListener(IGameListener listener) {
		this.listeners.remove(listener);
	}

}