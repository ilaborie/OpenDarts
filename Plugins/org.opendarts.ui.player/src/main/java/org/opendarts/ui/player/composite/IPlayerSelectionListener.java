package org.opendarts.ui.player.composite;

import java.util.List;

import org.opendarts.core.model.player.IPlayer;

/**
 * Listener for selection change listener
 */
public interface IPlayerSelectionListener {

	/**
	 * Notify selection change.
	 *
	 * @param players the players
	 */
	void notifySelectionChange(List<IPlayer> players);

}
