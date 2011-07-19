package org.opendarts.prototype.internal.model.stats;

import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.model.session.ISet;

/**
 * The Class SetStats.
 */
public class SetStats extends AbstractStats<ISet> {

	/**
	 * Instantiates a new sets the stats.
	 *
	 * @param set the set
	 * @param player the player
	 */
	public SetStats(ISet set, IPlayer player) {
		super(set, player);
	}

}
