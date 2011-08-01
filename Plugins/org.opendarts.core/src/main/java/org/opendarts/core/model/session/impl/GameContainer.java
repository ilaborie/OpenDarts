package org.opendarts.core.model.session.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.IGameContainer;

/**
 * The Class GameContainer.
 *
 * @param <T> the generic type
 */
public class GameContainer<T> implements IGameContainer<T> {

	/** The start. */
	private Calendar start;

	/** The end. */
	private Calendar end;

	/** The winner. */
	private IPlayer winner;

	/** The current game. */
	private T currentGame;

	/** The games. */
	private final List<T> games;

	/**
	 * Instantiates a new game container.
	 */
	public GameContainer() {
		super();
		this.games = new ArrayList<T>();
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

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.IAbstractGame#getWinner()
	 */
	@Override
	public IPlayer getWinner() {
		return this.winner;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.IGameContainer#getCurrentGame()
	 */
	@Override
	public T getCurrentGame() {
		return this.currentGame;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.IGameContainer#getAllGame()
	 */
	@Override
	public List<T> getAllGame() {
		return Collections.unmodifiableList(this.games);
	}

	/**
	 * Gets the internals game.
	 *
	 * @return the internals game
	 */
	protected List<T> getInternalsGame() {
		return this.games;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.IGameContainer#addGame(java.lang.Object)
	 */
	@Override
	public void addGame(T t) {
		this.games.add(t);
	}

	/**
	 * Sets the end.
	 *
	 * @param end the new end
	 */
	public void setEnd(Calendar end) {
		this.end = end;
	}

	/**
	 * Sets the winner.
	 *
	 * @param winner the new winner
	 */
	protected void setWinner(IPlayer winner) {
		this.winner = winner;
	}

	/**
	 * Sets the current game.
	 *
	 * @param currentGame the new current game
	 */
	protected void setCurrentGame(T currentGame) {
		this.currentGame = currentGame;
	}

}
