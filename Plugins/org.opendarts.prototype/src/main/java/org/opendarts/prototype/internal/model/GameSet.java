package org.opendarts.prototype.internal.model;

import org.opendarts.prototype.model.IGame;
import org.opendarts.prototype.model.ISession;
import org.opendarts.prototype.model.ISet;

/**
 * The Class GameSet.
 */
public class GameSet extends GameContainer<IGame> implements ISet {

	/** The session. */
	private final ISession session;

	/** The nb game. */
	private final int nbGame;

	/**
	 * Instantiates a new game set.
	 *
	 * @param session the session
	 * @param nbGame the nb game
	 */
	public GameSet(ISession session, int nbGame) {
		super();
		this.session = session;
		this.nbGame = nbGame;
	}

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	public ISession getSession() {
		return this.session;
	}

	/**
	 * Gets the nb game.
	 *
	 * @return the nb game
	 */
	public int getNbGame() {
		return this.nbGame;
	}

}
