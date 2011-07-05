package org.opendarts.prototype.internal.model.player;

/**
 * The Class ComputerPlayer.
 */
public class ComputerPlayer extends Player {

	/**
	 * Instantiates a new computer player.
	 */
	public ComputerPlayer() {
		super("<COM>");
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.player.Player#isComputer()
	 */
	@Override
	public boolean isComputer() {
		return true;
	}

}
