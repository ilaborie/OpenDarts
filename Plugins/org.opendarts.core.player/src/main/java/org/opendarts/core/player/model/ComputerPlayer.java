package org.opendarts.core.player.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.opendarts.core.model.player.IComputerPlayer;

/**
 * The Class ComputerPlayer.
 */
@Entity
@NamedQueries({
	@NamedQuery(name="ComputerPlayer.byLevel", query="Select cp From ComputerPlayer cp Where cp.level = :level"),
	@NamedQuery(name="ComputerPlayer.all", query="Select cp From ComputerPlayer cp"),
	})
public class ComputerPlayer extends Player implements IComputerPlayer {
	
	/** The level. */
	@Column(unique=true)
	private int level;

	/**
	 * Instantiates a new computer player.
	 */
	public ComputerPlayer() {
		super();
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
