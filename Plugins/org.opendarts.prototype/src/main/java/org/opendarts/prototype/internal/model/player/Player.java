package org.opendarts.prototype.internal.model.player;

import org.opendarts.prototype.model.player.IPlayer;

/**
 * The Class Player.
 */
public class Player implements IPlayer {

	/** The name. */
	private final String name;

	/**
	 * Instantiates a new player.
	 */
	public Player(String name) {
		super();
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.IPlayer#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getName();
	}

}
