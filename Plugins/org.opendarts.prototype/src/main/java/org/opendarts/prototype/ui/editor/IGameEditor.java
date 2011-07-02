package org.opendarts.prototype.ui.editor;

import org.eclipse.ui.IEditorPart;
import org.opendarts.prototype.model.game.IGame;

/**
 * The Interface IGameEditor.
 */
public interface IGameEditor extends IEditorPart {

	/**
	 * Gets the game input.
	 *
	 * @return the game input
	 */
	GameEditorInput getGameInput();

	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	IGame getGame();

}
