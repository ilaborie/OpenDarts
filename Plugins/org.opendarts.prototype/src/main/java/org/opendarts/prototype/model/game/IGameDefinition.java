package org.opendarts.prototype.model.game;

import java.util.List;

import org.opendarts.prototype.model.player.IPlayer;

/**
 * The Interface IGameDefinition.
 */
public interface IGameDefinition {
	// Mostly depends on GameX01 type

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	List<IPlayer> getPlayers();
}
