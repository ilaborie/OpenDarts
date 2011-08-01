package org.opendarts.ui.x01.service;

import org.eclipse.swt.graphics.Image;
import org.opendarts.ui.dialog.IGameDefinitionComposite;
import org.opendarts.ui.service.IGameDefinitionProvider;
import org.opendarts.ui.x01.dialog.SetX01ConfigurationDialog;

/**
 * The Class GameX01DefinitionProvider.
 */
public class GameX01DefinitionProvider implements IGameDefinitionProvider{

	/* (non-Javadoc)
	 * @see org.opendarts.ui.service.IGameDefinitionProvider#createGameDefinitionComposite()
	 */
	@Override
	public IGameDefinitionComposite createGameDefinitionComposite() {
		return new SetX01ConfigurationDialog();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.service.IGameDefinitionProvider#getName()
	 */
	@Override
	public String getName() {
		return "x01 (501, 301, 170, ...)";
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.service.IGameDefinitionProvider#getImage()
	 */
	@Override
	public Image getImage() {
		return null;
	}

}
