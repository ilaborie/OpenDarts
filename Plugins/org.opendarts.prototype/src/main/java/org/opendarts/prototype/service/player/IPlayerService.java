package org.opendarts.prototype.service.player;

import java.util.List;

import org.opendarts.prototype.model.player.IPlayer;

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

	/**
	 * Gets the all players.
	 *
	 * @return the all players
	 */
	List<IPlayer> getAllPlayers();

}
