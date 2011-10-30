package org.opendarts.core.model.session.func;

import org.opendarts.core.model.player.func.PlayerToStringFunction;
import org.opendarts.core.model.session.ISet;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

/**
 * The Class SetPlayersFunction.
 */
public class SetPlayersFunction implements Function<ISet, String> {

	/* (non-Javadoc)
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	@Override
	public String apply(ISet set) {
		Joiner joiner = Joiner.on(", ");
		return joiner.join(Lists.transform(set.getGameDefinition().getInitialPlayers(),
				new PlayerToStringFunction()));
	}

}
