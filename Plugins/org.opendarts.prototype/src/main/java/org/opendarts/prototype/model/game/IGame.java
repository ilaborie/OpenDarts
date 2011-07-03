package org.opendarts.prototype.model.game;

import java.util.List;

import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.model.session.IAbstractGame;
import org.opendarts.prototype.model.session.ISet;

/**
 * The Interface IGame.
 */
public interface IGame extends IAbstractGame {

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	List<IPlayer> getPlayers();

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	String getDescription();

	/**
	 * Gets the sets the.
	 *
	 * @return the sets the
	 */
	ISet getParentSet();

	/**
	 * Gets the game entries.
	 *
	 * @return the game entries
	 */
	List<? extends IGameEntry> getGameEntries();

	/**
	 * Checks if is started.
	 *
	 * @return true, if is started
	 */
	boolean isStarted();

	/**
	 * Adds the listener.
	 *
	 * @param listener the listener
	 */
	void addListener(IGameListener listener);

	/**
	 * Removes the listener.
	 *
	 * @param listener the listener
	 */
	void removeListener(IGameListener listener);

	/**
	 * Gets the first player.
	 *
	 * @return the first player
	 */
	IPlayer getFirstPlayer();

}
