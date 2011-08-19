package org.opendarts.ui.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.opendarts.core.model.game.IGameDefinition;
import org.opendarts.ui.service.IGameUiProvider;
import org.opendarts.ui.service.IGameUiService;

/**
 * The Class GameUiProvider.
 */
public class GameUiProvider implements IGameUiProvider {

	/** The map. */
	private final Map<IGameDefinition, IGameUiService> map;

	/**
	 * Instantiates a new game ui provider.
	 */
	public GameUiProvider() {
		super();
		this.map = new HashMap<IGameDefinition, IGameUiService>();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.service.IGameUiProvider#getGameUiService(org.opendarts.core.model.game.IGameDefinition)
	 */
	@Override
	public IGameUiService getGameUiService(IGameDefinition gameDefinition) {
		return this.map.get(gameDefinition);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.service.IGameUiProvider#getGameUiService(org.opendarts.core.model.game.IGameDefinition, org.opendarts.ui.service.IGameUiService)
	 */
	@Override
	public void registerGameUiService(IGameDefinition gameDefinition,
			IGameUiService gameUiService) {
		this.map.put(gameDefinition, gameUiService);
	}

}
