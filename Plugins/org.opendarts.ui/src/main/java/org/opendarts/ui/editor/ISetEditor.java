package org.opendarts.ui.editor;

import org.eclipse.ui.IEditorPart;
import org.opendarts.core.model.session.ISet;

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
