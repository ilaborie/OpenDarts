package org.opendarts.prototype.service;

import org.opendarts.prototype.model.IPlayer;

/**
 * The Interface IPlayerService.
 */
public interface IPlayerService {

	/**
	 * Gets the player.
	 *
	 * @param playerName the player name
	 * @return the player
	 */
	IPlayer getPlayer(String playerName);

	/**
	 * Gets the computer player.
	 *
	 * @return the computer player
	 */
	IPlayer getComputerPlayer();

}
