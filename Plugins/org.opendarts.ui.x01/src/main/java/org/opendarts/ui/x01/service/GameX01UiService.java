package org.opendarts.ui.x01.service;

import org.eclipse.swt.widgets.Composite;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.x01.model.GameX01;
import org.opendarts.ui.service.IGameUiService;
import org.opendarts.ui.x01.utils.comp.GameDetailComposite;

/**
 * The Class GameX01UiService.
 */
public class GameX01UiService implements IGameUiService {

	/**
	 * Instantiates a new game x01 ui service.
	 */
	public GameX01UiService() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.service.IGameUiService#getGameResult(org.opendarts.core.model.game.IGame)
	 */
	@Override
	public String getGameResult(IGame igame) {
		String result;
		if (igame != null) {
			GameX01 game = (GameX01) igame;
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
		return new GameDetailComposite(parent, game);
	}

}
