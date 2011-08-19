package org.opendarts.core.ia.service;

import org.opendarts.core.model.dart.IDart;
import org.opendarts.core.model.player.IComputerPlayer;

public interface IComputerPlayerDartService {

	/**
	 * Gets the computer dart.
	 *
	 * @param player the player
	 * @param wished the wished
	 * @return the computer dart
	 */
	IDart getComputerDart(IComputerPlayer player, IDart wished);

}
