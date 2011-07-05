package org.opendarts.prototype.internal.model.game.x01;

import org.opendarts.prototype.model.game.IGameEntry;

/**
 * The Class DummyX01Entry.
 */
public class DummyX01Entry implements IGameEntry {

	/** The game. */
	private final GameX01 game;

	/**
	 * Instantiates a new dummy x01 entry.
	 *
	 * @param game the game
	 */
	public DummyX01Entry(GameX01 game) {
		super();
		this.game = game;
	}

	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	public GameX01 getGame() {
		return this.game;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.game.IGameEntry#getRound()
	 */
	@Override
	public int getRound() {
		return -1;
	}

}
