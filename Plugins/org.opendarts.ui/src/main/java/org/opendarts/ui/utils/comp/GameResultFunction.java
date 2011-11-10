package org.opendarts.ui.utils.comp;

import org.opendarts.core.model.game.IGame;
import org.opendarts.ui.service.IGameUiProvider;
import org.opendarts.ui.service.IGameUiService;

import com.google.common.base.Function;

/**
 * The Class GameResultFunction.
 */
public class GameResultFunction implements Function<IGame, String> {

	/** The game ui provider. */
	private final IGameUiProvider gameUiProvider;

	/**
	 * Instantiates a new game result function.
	 *
	 * @param gameUiProvider the game ui provider
	 */
	public GameResultFunction(IGameUiProvider gameUiProvider) {
		super();
		this.gameUiProvider = gameUiProvider;
	}

	/* (non-Javadoc)
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	@Override
	public String apply(IGame game) {
		String result;
		IGameUiService gameUiService = gameUiProvider.getGameUiService(game
				.getParentSet().getGameDefinition());
		if (gameUiService != null) {
			result = gameUiService.getGameResult(game);
		} else {
			result = "";
		}
		return result;
	}

}
