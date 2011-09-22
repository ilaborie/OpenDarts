package org.opendarts.ui.x01.defi.service;

import org.eclipse.swt.graphics.Image;
import org.opendarts.core.model.game.IGameDefinition;
import org.opendarts.core.x01.defi.model.GameX01DefiDefinition;
import org.opendarts.ui.dialog.IGameDefinitionComposite;
import org.opendarts.ui.service.IGameDefinitionProvider;
import org.opendarts.ui.x01.defi.dialog.SetX01DefiConfigurationDialog;
import org.opendarts.ui.x01.defi.pref.PreferencesConverterUtils;

/**
 * The Class GameX01DefinitionProvider.
 */
public class GameX01DefiDefinitionProvider implements IGameDefinitionProvider {

	/* (non-Javadoc)
	 * @see org.opendarts.ui.service.IGameDefinitionProvider#createGameDefinitionComposite()
	 */
	@Override
	public IGameDefinitionComposite createGameDefinitionComposite() {
		return new SetX01DefiConfigurationDialog();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.service.IGameDefinitionProvider#getName()
	 */
	@Override
	public String getName() {
		return "Defi 100 001";
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
				.getGameDefinitionAsString((GameX01DefiDefinition) gameDefinition);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.service.IGameDefinitionProvider#getImage()
	 */
	@Override
	public Image getImage() {
		return null;
	}

}
