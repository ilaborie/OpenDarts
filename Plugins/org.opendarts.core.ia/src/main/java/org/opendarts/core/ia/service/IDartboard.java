package org.opendarts.core.ia.service;

import org.opendarts.core.ia.model.DartboardProperties;
import org.opendarts.core.model.dart.IDart;
import org.opendarts.core.model.player.IComputerPlayer;

/**
 * The Interface IDartboard.
 */
public interface IDartboard {
	
	/**
	 * Gets the dart.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the dart
	 */
	IDart getDart(double x, double y);
	
	/**
	 * Gets the x.
	 *
	 * @param dart the dart
	 * @return the x
	 */
	double getX(IDart dart);
	
	/**
	 * Gets the y.
	 *
	 * @param dart the dart
	 * @return the y
	 */
	double getY(IDart dart);

	/**
	 * Gets the dartboard.
	 *
	 * @return the dartboard
	 */
	DartboardProperties getDartboard();

	/**
	 * Gets the player factor.
	 *
	 * @param player the player
	 * @return the player factor
	 */
	double getPlayerFactor(IComputerPlayer player);

}
