package org.opendarts.ui.x01.defi.dialog;

import org.eclipse.swt.widgets.Shell;
import org.opendarts.core.model.player.IComputerPlayer;
import org.opendarts.core.x01.model.GameX01;
import org.opendarts.core.x01.model.GameX01Entry;
import org.opendarts.ui.x01.dialog.DartsComputerX01Dialog;

/**
 * The Class DartsComputerX01DefiDialog.
 */
public class DartsComputerX01DefiDialog extends DartsComputerX01Dialog{

	/** The delay. */
	private final int delay;

	/**
	 * Instantiates a new darts computer x01 defi dialog.
	 *
	 * @param parentShell the parent shell
	 * @param delay the delay
	 * @param player the player
	 * @param game the game
	 * @param entry the entry
	 */
	public DartsComputerX01DefiDialog(Shell parentShell,int delay,
			IComputerPlayer player, GameX01 game, GameX01Entry entry) {
		super(parentShell, player, game, entry);
		this.delay =delay;
	}
	
	/* (non-Javadoc)
	 * @see org.opendarts.ui.x01.dialog.DartsComputerX01Dialog#getDelay()
	 */
	@Override
	protected int getDelay() {
		return this.delay/4;
	}

}
