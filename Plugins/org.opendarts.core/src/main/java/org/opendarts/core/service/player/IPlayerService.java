package org.opendarts.core.service.player;

import java.util.List;

import org.opendarts.core.model.player.IComputerPlayer;
import org.opendarts.core.model.player.IPlayer;

/**
 * The Interface IPlayerService.
 */
public interface IPlayerService {
	/**
	 * Gets the player.
	 *
	 * @param uuid the uuid
	 * @return the player
	 */
	IPlayer getPlayer(String uuid);

	/**
	 * Gets the computer player.
	 *
	 * @return the computer player
	 */
	IComputerPlayer getComputerPlayer(int lvl);

	/**
	 * Gets the all computer players.
	 *
	 * @return the all computer players
	 */
	List<IComputerPlayer> getAllComputerPlayers();

	/**
	 * Gets the all players.
	 *
	 * @return the all players
	 */
	List<IPlayer> getAllPlayers();

	/**
	 * Creates the computer.
	 *
	 * @param level the level
	 * @return the i computer player
	 */
	IComputerPlayer createComputer(int level);

	/**
	 * Creates the player.
	 *
	 * @param name the name
	 * @return the i player
	 */
	IPlayer createPlayer(String name);

}
