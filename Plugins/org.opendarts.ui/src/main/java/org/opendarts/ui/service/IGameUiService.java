package org.opendarts.ui.service;

import org.eclipse.swt.widgets.Composite;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameDefinition;

/**
 * The Interface IGameUiService.
 */
public interface IGameUiService {

	/**
	 * Gets the game result.
	 *
	 * @param game the game
	 * @return the game result
	 */
	String getGameResult(IGame game);

	/**
	 * Gets the game detail.
	 *
	 * @param game the game
	 * @return the game detail
	 */
	Composite getGameDetail(Composite parent, IGame game);
	
	/**
	 * Gets the game editor.
	 *
	 * @param gameDefinition the game definition
	 * @return the game editor
	 */
	String getGameEditor(IGameDefinition gameDefinition);

}
