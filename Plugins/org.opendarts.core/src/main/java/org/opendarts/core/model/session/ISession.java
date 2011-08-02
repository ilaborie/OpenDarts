package org.opendarts.core.model.session;

import org.opendarts.core.model.player.IPlayer;

/**
 * The Interface ISession.
 */
public interface ISession extends IGameContainer<ISet> {

	/**
	 * Gets the winning set.
	 *
	 * @param player the player
	 * @return the winning set
	 */
	int getWinningSet(IPlayer player);

	/**
	 * Adds the listener.
	 *
	 * @param listener the listener
	 */
	void addListener(ISessionListener listener);

	/**
	 * Removes the listener.
	 *
	 * @param listener the listener
	 */
	void removeListener(ISessionListener listener);

	/**
	 * Initialize the session.
	 */
	void init();
	
	/**
	 * Finish the session.
	 */
	void finish();

}
