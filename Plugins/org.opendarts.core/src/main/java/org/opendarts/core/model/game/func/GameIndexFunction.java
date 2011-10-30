package org.opendarts.core.model.game.func;

import java.text.MessageFormat;

import org.opendarts.core.model.game.IGame;

import com.google.common.base.Function;

/**
 * The Class SetPlayersFunction.
 */
public class GameIndexFunction implements Function<IGame, String> {

	/* (non-Javadoc)
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	@Override
	public String apply(IGame game) {
		int index = game.getParentSet().getAllGame().indexOf(game);
		return MessageFormat.format("#{0}", index + 1);
	}

}
