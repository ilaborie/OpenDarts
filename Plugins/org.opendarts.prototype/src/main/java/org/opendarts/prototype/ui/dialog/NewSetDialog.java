package org.opendarts.prototype.ui.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.opendarts.prototype.model.game.IGameDefinition;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.ui.x01.dialog.SetX01ConfigurationDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class NewSetDialog.
 */
public class NewSetDialog extends TitleAreaDialog {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(NewSetDialog.class);

	/** The last game definition. */
	// TODO Store in pref
	private static IGameDefinition lastGameDefinition;

	/** The players. */
	private List<IPlayer> players;

	/** The game definition. */
	private IGameDefinition gameDefinition;

	/** The comp game def. */
	private IGameDefinitionComposite compGameDef;

	/**
	 * Instantiates a new new game dialog.
	 *
	 * @param parentShell the parent shell
	 */
	public NewSetDialog(Shell parentShell) {
		super(parentShell);
		this.setHelpAvailable(false);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("New Set ...");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#isResizable()
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);
		this.getShell().pack();
		return control;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite comp = (Composite) super.createDialogArea(parent);

		// Main composite
		Composite main = new Composite(comp, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(main);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(main);

		// XXX GameX01
		this.compGameDef = new SetX01ConfigurationDialog();
		Composite composite = this.compGameDef.createSetConfiguration(this,
				main, lastGameDefinition);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);

		return comp;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		LOG.debug("NewSet#ok");
		try {
			this.gameDefinition = this.compGameDef.getGameDefinition();
			lastGameDefinition = this.gameDefinition;
			super.okPressed();
		} catch (Exception e) {
			this.setErrorMessage(e.getMessage());
		}
	}

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	public List<IPlayer> getPlayers() {
		return this.players;
	}

	/**
	 * Gets the game definition.
	 *
	 * @return the game definition
	 */
	public IGameDefinition getGameDefinition() {
		return this.gameDefinition;
	}

}
