package org.opendarts.ui.dialog;

import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.opendarts.core.model.game.IGameDefinition;

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
	Composite createSetConfiguration(INewContainerDialog dialog,
			Composite parent, IGameDefinition lastGameDefinition);

	/**
	 * Gets the game definition.
	 *
	 * @return the game definition
	 */
	IGameDefinition getGameDefinition();

	/**
	 * Validate.
	 *
	 * @return the list
	 */
	List<ValidationEntry> validate();

	/**
	 * Gets the editor id.
	 *
	 * @return the editor id
	 */
	String getEditorId();

	/**
	 * Creates the buttons for button bar.
	 *
	 * @param parent the parent
	 */
	void createButtonsForButtonBar(Composite parent);

	/**
	 * Sets the focus.
	 */
	void setFocus();
}
