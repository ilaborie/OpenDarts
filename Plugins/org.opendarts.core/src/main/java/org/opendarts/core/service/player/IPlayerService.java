package org.opendarts.core.service.player;

import java.util.List;

import org.opendarts.core.model.dart.IDart;
import org.opendarts.core.model.player.IPlayer;

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

	/**
	 * Gets the computer dart.
	 *
	 * @param wished the wished
	 * @return the computer dart
	 */
	IDart getComputerDart(IDart wished);

}
