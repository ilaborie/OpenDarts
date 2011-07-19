package org.opendarts.prototype.internal.model.stats;

import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.model.session.ISession;

/**
 * The Class SessionStats.
 */
public class SessionStats extends AbstractStats<ISession> {

	/**
	 * Instantiates a new session stats.
	 *
	 * @param session the session
	 * @param player the player
	 */
	public SessionStats(ISession session, IPlayer player) {
		super(session, player);
	}

}
