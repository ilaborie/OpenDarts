package org.opendarts.core.model.player;

/**
 * The Interface IPlayer.
 */
public interface IPlayer {
	
	/**
	 * Gets the uUID.
	 *
	 * @return the uUID
	 */
	String getUuid();
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();

	/**
	 * Checks if is computer.
	 *
	 * @return true, if is computer
	 */
	boolean isComputer();

}
