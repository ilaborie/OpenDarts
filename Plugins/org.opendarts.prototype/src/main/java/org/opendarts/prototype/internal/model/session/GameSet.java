package org.opendarts.prototype.internal.model.session;

import org.opendarts.prototype.internal.model.game.GameDefinition;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.game.IGameDefinition;
import org.opendarts.prototype.model.session.ISession;
import org.opendarts.prototype.model.session.ISet;

/**
 * The Class GameSet.
 */
public class GameSet extends GameContainer<IGame> implements ISet {

	/** The session. */
	private final ISession session;

	/** The game definition. */
	private final GameDefinition gameDefinition;


	/**
	 * Instantiates a new game set.
	 *
	 * @param session the session
	 * @param nbGame the nb game
	 */
	public GameSet(ISession session, GameDefinition gameDefinition) {
		super();
		this.session = session;
		this.gameDefinition = gameDefinition;
	}

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	public ISession getSession() {
		return this.session;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.session.ISet#getGameDefinition()
	 */
	@Override
	public IGameDefinition getGameDefinition() {
		return this.gameDefinition;
	}

}
