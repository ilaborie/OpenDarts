package org.opendarts.core.model.game.func;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;

import com.google.common.base.Function;

/**
 * The Class SetPlayersFunction.
 */
public class GameWinnerFunction implements Function<IGame, String> {

	/* (non-Javadoc)
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	@Override
	public String apply(IGame game) {
		String result;
		IPlayer winner = game.getWinner();
		if (winner != null) {
			result = winner.getName();
		} else {
			result = "-";
		}
		return result;
	}

}
