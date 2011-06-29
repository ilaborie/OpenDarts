package org.opendarts.prototype.internal.model;

import org.opendarts.prototype.model.IPlayer;

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

}
