package org.opendarts.prototype.internal.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.opendarts.prototype.model.IGameContainer;
import org.opendarts.prototype.model.IPlayer;

/**
 * The Class GameContainer.
 *
 * @param <T> the generic type
 */
public class GameContainer<T> implements IGameContainer<T> {

	/** The start. */
	private final Calendar start;

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
		this.start = Calendar.getInstance();
		this.games = new ArrayList<T>();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.IAbstractGame#getStart()
	 */
	@Override
	public Calendar getStart() {
		return this.start;
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
	public void setWinner(IPlayer winner) {
		this.winner = winner;
	}

	/**
	 * Sets the current game.
	 *
	 * @param currentGame the new current game
	 */
	public void setCurrentGame(T currentGame) {
		this.currentGame = currentGame;
	}

}
