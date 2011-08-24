package org.opendarts.ui.x01.service;

import org.eclipse.swt.graphics.Image;
import org.opendarts.core.model.game.IGameDefinition;
import org.opendarts.core.x01.model.GameX01Definition;
import org.opendarts.ui.dialog.IGameDefinitionComposite;
import org.opendarts.ui.service.IGameDefinitionProvider;
import org.opendarts.ui.x01.dialog.SetX01ConfigurationDialog;
import org.opendarts.ui.x01.pref.PreferencesConverterUtils;

/**
 * The Class GameX01DefinitionProvider.
 */
public class GameX01DefinitionProvider implements IGameDefinitionProvider {

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
	 * @see org.opendarts.ui.service.IGameDefinitionProvider#getGameDefinitionFromString(java.lang.String)
	 */
	@Override
	public IGameDefinition getGameDefinitionFromString(String def) {
		return PreferencesConverterUtils.getStringAsGameDefinition(def);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.service.IGameDefinitionProvider#getGameDefinitionAsString(org.opendarts.core.model.game.IGameDefinition)
	 */
	@Override
	public String getGameDefinitionAsString(IGameDefinition gameDefinition) {
		return PreferencesConverterUtils
				.getGameDefinitionAsString((GameX01Definition) gameDefinition);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.service.IGameDefinitionProvider#getImage()
	 */
	@Override
	public Image getImage() {
		return null;
	}

}
