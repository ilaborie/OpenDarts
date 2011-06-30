package org.opendarts.prototype.ui.dialog;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.opendarts.prototype.ProtoPlugin;
import org.opendarts.prototype.model.IGameDefinition;
import org.opendarts.prototype.model.IPlayer;
import org.opendarts.prototype.service.IGameDefinitionService;
import org.opendarts.prototype.service.IPlayerService;
import org.opendarts.prototype.ui.utils.OpenDartsFormsToolkit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class NewGameDialog.
 */
public class NewGameDialog extends TitleAreaDialog {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(NewGameDialog.class);

	/** The players. */
	private List<IPlayer> players;

	/** The nb set games. */
	private int nbSetGames;

	/** The game definition. */
	private IGameDefinition gameDefinition;

	/** The toolkit. */
	private OpenDartsFormsToolkit toolkit = OpenDartsFormsToolkit.getToolkit();

	/**
	 * Instantiates a new new game dialog.
	 *
	 * @param parentShell the parent shell
	 */
	public NewGameDialog(Shell parentShell) {
		super(parentShell);
		this.setHelpAvailable(false);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("New Game501 ...");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#isResizable()
	 */
	@Override
	protected boolean isResizable() {
		return true;
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

		// Game501 Description
		Group grpGameDesc = this.createGameDescriptionArea(main);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(grpGameDesc);

		// Set Definition
		Group grpSet = this.createSetArea(main);
		GridDataFactory.fillDefaults().applyTo(grpSet);

		// Players selections
		Group grpPlayers = this.createPlayersArea(main);
		GridDataFactory.fillDefaults().applyTo(grpPlayers);

		return comp;
	}

	/**
	 * Creates the game description area.
	 *
	 * @param main the main
	 * @return the group
	 */
	protected Group createGameDescriptionArea(Composite main) {
		Group group = new Group(main, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(group);
		group.setText("Game501 Description");

		// XXX prototype 
		Label label = toolkit.createDummyLabel(group,
				"Prototype is only for 501");
		GridDataFactory.fillDefaults().grab(true, false).applyTo(label);

		return group;
	}

	/**
	 * Creates the set area.
	 *
	 * @param main the main
	 * @return the group
	 */
	protected Group createSetArea(Composite main) {
		Group group = new Group(main, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(group);
		group.setText("Legs");

		// XXX prototype 
		Label label = toolkit.createDummyLabel(group,
				"Prototype is only for 'Best Of 5'");
		GridDataFactory.fillDefaults().grab(true, false).applyTo(label);

		return group;
	}

	/**
	 * Creates the players area.
	 *
	 * @param main the main
	 * @return the group
	 */
	protected Group createPlayersArea(Composite main) {
		Group group = new Group(main, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(group);
		group.setText("Players");

		// XXX prototype 
		Label label = toolkit.createDummyLabel(group,
				"Prototype is only for 'User vs Computer'");
		GridDataFactory.fillDefaults().grab(true, false).applyTo(label);

		return group;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		LOG.debug("NewGame#ok");
		IPlayerService playerService = ProtoPlugin
				.getService(IPlayerService.class);

		// XXX prototype
		IGameDefinitionService gameDefService = ProtoPlugin
				.getService(IGameDefinitionService.class);
		this.gameDefinition = gameDefService.createGameDefinition();
		this.nbSetGames = 5;
		this.players = Arrays.asList(
				playerService.getPlayer(System.getenv("USER")),
				playerService.getComputerPlayer());

		super.okPressed();
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
	 * Gets the number set games.
	 *
	 * @return the number set games
	 */
	public int getNumberSetGames() {
		return this.nbSetGames;
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
