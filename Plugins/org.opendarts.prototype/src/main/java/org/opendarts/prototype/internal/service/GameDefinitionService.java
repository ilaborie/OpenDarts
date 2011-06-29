package org.opendarts.prototype.internal.service;

import org.opendarts.prototype.internal.model.GameDefinition;
import org.opendarts.prototype.model.IGameDefinition;
import org.opendarts.prototype.service.IGameDefinitionService;

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
