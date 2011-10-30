package org.opendarts.core.model.game.func;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.func.PlayerToStringFunction;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

/**
 * The Class SetPlayersFunction.
 */
public class GamePlayersFunction implements Function<IGame, String> {
	/* (non-Javadoc)
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	@Override
	public String apply(IGame game) {
		Joiner joiner = Joiner.on(", ");
		return joiner.join(Lists.transform(game.getPlayers(),
				new PlayerToStringFunction()));
	}
}
