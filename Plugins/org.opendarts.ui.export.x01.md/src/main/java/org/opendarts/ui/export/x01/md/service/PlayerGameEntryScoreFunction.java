package org.opendarts.ui.export.x01.md.service;

import java.util.Map;

import org.opendarts.core.model.dart.impl.ThreeDartsThrow;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.x01.model.GameX01Entry;

import com.google.common.base.Function;

/**
 * The Class PlayerGameEntryScoreFunction.
 */
public class PlayerGameEntryScoreFunction implements Function<IPlayer, Object> {

	private final Map<IPlayer, ThreeDartsThrow> playerThrow;

	/**
	 * Instantiates a new player entry value function.
	 *
	 * @param e the e
	 */
	public PlayerGameEntryScoreFunction(GameX01Entry e) {
		this.playerThrow = e.getPlayerThrow();
	}

	/* (non-Javadoc)
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	@Override
	public Object apply(IPlayer p) {
		Object result;
		ThreeDartsThrow dartsThrow = this.playerThrow.get(p);
		if (dartsThrow != null) {
			result = Integer.valueOf(dartsThrow.getScore());
		} else {
			result = "";
		}
		return result;
	}
}