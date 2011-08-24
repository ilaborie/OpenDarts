package org.opendarts.ui.service;

import org.eclipse.swt.graphics.Image;
import org.opendarts.core.model.game.IGameDefinition;
import org.opendarts.ui.dialog.IGameDefinitionComposite;

/**
 * The Interface IGameDefinitionProvider.
 */
public interface IGameDefinitionProvider {

	/**
	 * Creates the game definition composite.
	 *
	 * @return the i game definition composite
	 */
	IGameDefinitionComposite createGameDefinitionComposite();
	
	/**
	 * Gets the game definition as string.
	 *
	 * @return the game definition as string
	 */
	String getGameDefinitionAsString(IGameDefinition gameDefinition);
	
	/**
	 * Gets the game definition from string.
	 *
	 * @param def the def
	 * @return the game definition from string
	 */
	IGameDefinition getGameDefinitionFromString(String def);

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	Image getImage();

}
