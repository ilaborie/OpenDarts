package org.opendarts.prototype.ui.editor;

import org.eclipse.ui.IEditorPart;
import org.opendarts.prototype.model.session.ISet;

/**
 */
public interface ISetEditor extends IEditorPart {

	/**
	 * Gets the game input.
	 *
	 * @return the game input
	 */
	SetEditorInput getSetInput();

	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	ISet getSet();

}
