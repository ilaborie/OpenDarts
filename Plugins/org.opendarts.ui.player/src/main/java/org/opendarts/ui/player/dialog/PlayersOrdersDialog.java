package org.opendarts.ui.player.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.ui.player.composite.PlayesOrderComposite;

/**
 * The Class PlayersOrdersDialog.
 */
public class PlayersOrdersDialog extends Dialog {

	/** The players. */
	private List<IPlayer> players;
	private PlayesOrderComposite cmpPlayers;

	/**
	 * Instantiates a new players orders dialog.
	 *
	 * @param shell the shell
	 */
	public PlayersOrdersDialog(Shell shell, List<IPlayer> players) {
		super(shell);
		this.players = players;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Change players order");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		GridLayoutFactory.fillDefaults().applyTo(parent);
		cmpPlayers = new PlayesOrderComposite(parent, this.players);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(cmpPlayers);

		// Separator
		Label lbl = new Label(parent, SWT.HORIZONTAL);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(lbl);
		return cmpPlayers;
	}

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	public List<IPlayer> getPlayers() {
		return this.players;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		if (this.cmpPlayers != null && !this.cmpPlayers.isDisposed()) {
			this.players = new ArrayList<IPlayer>(this.cmpPlayers.getPlayers());
		}
		super.okPressed();
	}

}
