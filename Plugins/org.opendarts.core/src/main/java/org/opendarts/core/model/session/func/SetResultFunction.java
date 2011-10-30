package org.opendarts.core.model.session.func;

import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISet;

import com.google.common.base.Function;

/**
 * The Class SetPlayersFunction.
 */
public class SetResultFunction implements Function<ISet, String> {

	/* (non-Javadoc)
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	@Override
	public String apply(ISet set) {
		StringBuilder sb = new StringBuilder();
		boolean isFist = true;
		for (IPlayer player : set.getGameDefinition().getPlayers()) {
			if (isFist) {
				isFist = false;
			} else {
				sb.append(", ");
			}
			sb.append(set.getWinningGames(player));
		}
		return sb.toString();
	}

}
