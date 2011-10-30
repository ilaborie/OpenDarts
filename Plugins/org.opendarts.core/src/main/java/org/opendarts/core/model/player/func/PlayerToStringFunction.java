package org.opendarts.core.model.player.func;

import org.opendarts.core.model.player.IPlayer;

import com.google.common.base.Function;

/**
 * The Class PlayerToStringFunction.
 */
public class PlayerToStringFunction implements Function<IPlayer, String> {

	/* (non-Javadoc)
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	@Override
	public String apply(IPlayer player) {
		if (player != null) {
			return player.getName();
		}
		return "?";
	}

}
