package org.opendarts.prototype.model.session;

import org.opendarts.prototype.model.player.IPlayer;

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

}
