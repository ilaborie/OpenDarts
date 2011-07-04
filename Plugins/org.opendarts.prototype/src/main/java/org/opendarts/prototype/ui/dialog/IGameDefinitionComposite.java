package org.opendarts.prototype.ui.dialog;

import org.eclipse.swt.widgets.Composite;
import org.opendarts.prototype.model.game.IGameDefinition;

/**
 * The Interface IGameDefinitionComposite.
 */
public interface IGameDefinitionComposite {

	/**
	 * Creates the set configuration.
	 *
	 * @param dialog the dialog
	 * @param parent the parent
	 * @param lastGameDefinition the last game definition
	 * @return the composite
	 */
	Composite createSetConfiguration(NewSetDialog dialog, Composite parent,
			IGameDefinition lastGameDefinition);

	/**
	 * Gets the game definition.
	 *
	 * @return the game definition
	 */
	IGameDefinition getGameDefinition();
}
