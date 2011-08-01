package org.opendarts.ui.service;

import org.eclipse.swt.graphics.Image;
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
