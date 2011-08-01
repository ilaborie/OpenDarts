package org.opendarts.core.model.session;

import java.util.Calendar;

import org.opendarts.core.model.player.IPlayer;

/**
 * The Interface IAbstractGame.
 */
public interface IAbstractGame {

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	Calendar getStart();

	/**
	 * Checks if is finished.
	 *
	 * @return true, if is finished
	 */
	boolean isFinished();

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	Calendar getEnd();

	/**
	 * Gets the winner.
	 *
	 * @return the winner
	 */
	IPlayer getWinner();

}
