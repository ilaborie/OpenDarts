package org.opendarts.core.player.model;

/**
 * The Class ComputerPlayer.
 */
public class ComputerPlayer extends Player {

	/**
	 * A counter
	 */
	private static int count = 1;

	/**
	 * Instantiates a new computer player.
	 */
	public ComputerPlayer() {
		super("AI_" + count++);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.player.Player#isComputer()
	 */
	@Override
	public boolean isComputer() {
		return true;
	}

}
