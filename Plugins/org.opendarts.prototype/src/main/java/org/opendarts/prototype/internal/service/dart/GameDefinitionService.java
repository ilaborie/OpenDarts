package org.opendarts.prototype.internal.service.dart;

import org.opendarts.prototype.internal.model.game.GameDefinition;
import org.opendarts.prototype.model.game.IGameDefinition;
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
	public IGameDefinition createGameDefinition() {
		return new GameDefinition();
	}

}
