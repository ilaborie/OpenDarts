package org.opendarts.core.stats.model.impl;

import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISession;

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
