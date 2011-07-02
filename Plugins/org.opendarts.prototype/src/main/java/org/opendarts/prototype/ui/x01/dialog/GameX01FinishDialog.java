package org.opendarts.prototype.ui.x01.dialog;

import java.text.MessageFormat;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.opendarts.prototype.internal.model.game.x01.GameX01;
import org.opendarts.prototype.model.player.IPlayer;

/**
 * The Class GameX01FinishDialog.
 */
public class GameX01FinishDialog extends MessageDialog {

	/** The dialog button labels. */
	private static String[] dialogButtonLabels = new String[] { "&Broken",
			"&1 dart", "&2 darts", "&3 darts" };

	/** The nb dart. */
	private int nbDarts;

	/**
	 * Instantiates a new game x501 finish dialog.
	 *
	 * @param parentShell the parent shell
	 * @param game the game
	 * @param player the player
	 */
	public GameX01FinishDialog(Shell parentShell, GameX01 game, IPlayer player) {
		super(parentShell, createDialogTitle(game, player), null,
				createDialogMessage(game, player), MessageDialog.QUESTION,
				dialogButtonLabels, 0);
	}

	/**
	 * Creates the dialog title.
	 *
	 * @param game the game
	 * @param player the player
	 * @return the string
	 */
	private static String createDialogTitle(GameX01 game, IPlayer player) {
		return MessageFormat.format("{0} Finished ?", game, player);
	}

	/**
	 * Creates the dialog message.
	 *
	 * @param game the game
	 * @param player the player
	 * @return the string
	 */
	private static String createDialogMessage(GameX01 game, IPlayer player) {
		return MessageFormat
				.format("{1} has finished the game ?", game, player);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.MessageDialog#buttonPressed(int)
	 */
	@Override
	protected void buttonPressed(int buttonId) {
		this.nbDarts = buttonId;
		super.buttonPressed(buttonId);
	}

	/**
	 * Gets the nb darts.
	 *
	 * @return the nb darts
	 */
	public int getNbDarts() {
		return this.nbDarts;
	}

}
