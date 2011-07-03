package org.opendarts.prototype.service.game;

import java.util.List;
import java.util.Map;

import org.opendarts.prototype.model.game.IGameDefinition;
import org.opendarts.prototype.model.player.IPlayer;

/**
 * The Interface IGameDefinitionService.
 */
public interface IGameDefinitionService {

	/**
	 * Creates the game definition.
	 *
	 * @param players the players
	 * @return the game definition
	 */
	IGameDefinition createGameDefinition(List<IPlayer> players,
			Map<String, Object> params);
}
