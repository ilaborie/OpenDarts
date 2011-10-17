package org.opendarts.ui.x01.defi.service;

import org.eclipse.swt.widgets.Composite;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameDefinition;
import org.opendarts.core.x01.defi.model.GameX01Defi;
import org.opendarts.ui.service.IGameUiService;
import org.opendarts.ui.x01.defi.editor.DefiX01Editor;
import org.opendarts.ui.x01.defi.service.comp.GameDefiDetailComposite;

/**
 * The Class GameX01DefiUiService.
 */
public class GameX01DefiUiService implements IGameUiService {

	/**
	 * Instantiates a new game x01 ui service.
	 */
	public GameX01DefiUiService() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.service.IGameUiService#getGameResult(org.opendarts.core.model.game.IGame)
	 */
	@Override
	public String getGameResult(IGame igame) {
		String result;
		if (igame != null) {
			GameX01Defi game = (GameX01Defi) igame;
			if (game.isFinished()) {
				result = String.valueOf(game.getNbDartToFinish());
			} else {
				result = "-";
			}
		} else {
			result = "";
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.service.IGameUiService#getGameDetail(org.opendarts.core.model.game.IGame)
	 */
	@Override
	public Composite getGameDetail(Composite parent, IGame game) {
		return new GameDefiDetailComposite(parent, game);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.service.IGameUiService#getGameEditor(org.opendarts.core.model.game.IGameDefinition)
	 */
	@Override
	public String getGameEditor(IGameDefinition gameDefinition) {
		return DefiX01Editor.ID;
	}

}
