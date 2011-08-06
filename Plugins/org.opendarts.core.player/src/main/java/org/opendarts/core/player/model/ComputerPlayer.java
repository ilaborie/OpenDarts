package org.opendarts.core.player.model;

import javax.persistence.Entity;

import org.opendarts.core.model.player.IComputerPlayer;

/**
 * The Class ComputerPlayer.
 */
@Entity
public class ComputerPlayer extends Player implements IComputerPlayer {
	
	/** The level. */
	private int level;

	/**
	 * Instantiates a new computer player.
	 */
	public ComputerPlayer() {
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.player.Player#isComputer()
	 */
	@Override
	public boolean isComputer() {
		return true;
	}

	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	@Override
	public int getLevel() {
		return this.level;
	}

	/**
	 * Sets the level.
	 *
	 * @param level the new level
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
	

}
