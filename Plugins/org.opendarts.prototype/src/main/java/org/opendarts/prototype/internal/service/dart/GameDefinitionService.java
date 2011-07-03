package org.opendarts.prototype.internal.service.dart;

import java.util.List;
import java.util.Map;

import org.opendarts.prototype.internal.model.game.GameDefinition;
import org.opendarts.prototype.model.game.IGameDefinition;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.service.game.IGameDefinitionService;

/**
 * The Class GameDefinitionService.
 */
public class GameDefinitionService implements IGameDefinitionService {

	/**
	 * Instantiates a new game definition service.
	 */
	public GameDefinitionService() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.IGameDefinitionService#createGameDefinition()
	 */
	@Override
	public IGameDefinition createGameDefinition(List<IPlayer> players,
			Map<String, Object> params) {
		int nbGameWin = (Integer) params.get(IGameDefinition.NB_GAME_TO_WIN);
		boolean playAll = Boolean.TRUE.equals(params
				.get(IGameDefinition.PLAY_ALL_GAME));
		return new GameDefinition(players, nbGameWin, playAll);
	}

}
