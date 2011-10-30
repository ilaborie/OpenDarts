package org.opendarts.core.model.session.func;

import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISet;

import com.google.common.base.Function;

/**
 * The Class SetPlayersFunction.
 */
public class SetWinnerFunction implements Function<ISet, String> {

	/* (non-Javadoc)
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	@Override
	public String apply(ISet set) {
		String result;
		IPlayer winner = set.getWinner();
		if (winner != null) {
			result = winner.getName();
		} else {
			result = "-";
		}
		return result;
	}

}
