package org.opendarts.ui.utils.comp;

import org.opendarts.core.model.game.IGame;
import org.opendarts.ui.service.IGameUiService;

import com.google.common.base.Function;

/**
 * The Class GameResultFunction.
 */
public class GameResultFunction implements Function<IGame, String> {

	/** The game ui provider. */
	private final IGameUiService gameUiService;

	public GameResultFunction(IGameUiService gameUiService) {
		super();
		this.gameUiService = gameUiService;
	}

	/* (non-Javadoc)
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	@Override
	public String apply(IGame game) {
		String result;
		if (this.gameUiService != null) {
			result = this.gameUiService.getGameResult(game);
		} else {
			result = "";
		}
		return result;
	}

}
