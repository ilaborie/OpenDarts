package org.opendarts.ui.service;

import org.opendarts.core.model.game.IGameDefinition;

/**
 * The Interface IGameUiProvider.
 */
public interface IGameUiProvider {

	/**
	 * Gets the game ui service.
	 *
	 * @param gameDefinition the game definition
	 * @return the game ui service
	 */
	IGameUiService getGameUiService(IGameDefinition gameDefinition);

	/**
	 * Gets the game ui service.
	 *
	 * @param gameDefinition the game definition
	 * @param gameUiService the game ui service
	 * @return the game ui service
	 */
	void registerGameUiService(IGameDefinition gameDefinition,
			IGameUiService gameUiService);

}
