package org.opendarts.ui.utils.shortcut;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;

/**
 * The Interface IShortcut.
 */
public interface IShortcut {

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	String getLabel();

	/**
	 * Gets the key label.
	 *
	 * @return the key label
	 */
	String getKeyLabel();

	/**
	 * Apply.
	 *
	 * @param keyCode the key code
	 * @param stateMask the state mask
	 * @return true, if successful
	 */
	boolean apply(int keyCode, int stateMask);

	/**
	 * Execute.
	 *
	 * @param parentShell the parent shell
	 * @param inputText the input text
	 * @param game the game
	 * @param player the player
	 * @param dec the dec
	 */
	void execute(Shell parentShell, Text inputText, IGame game, IPlayer player,
			ControlDecoration dec);
}
