package org.opendarts.ui.x01.dialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.opendarts.core.model.game.IGameDefinition;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.x01.model.GameX01Definition;
import org.opendarts.ui.dialog.IGameDefinitionComposite;
import org.opendarts.ui.dialog.INewContainerDialog;
import org.opendarts.ui.dialog.ValidationEntry;
import org.opendarts.ui.player.composite.IPlayerSelectionListener;
import org.opendarts.ui.player.composite.PlayerSelectionComposite;
import org.opendarts.ui.service.IGameUiProvider;
import org.opendarts.ui.x01.X01UiPlugin;

/**
 * The Class SetX01ConfigurationDialog.
 */
public class SetX01ConfigurationDialog implements IGameDefinitionComposite,
		SelectionListener, IPlayerSelectionListener {

	private final IGameUiProvider gameUiProvider;

	/** The score start. */
	private int startScore;

	/** The nb game to win. */
	private int nbGameToWin;

	/** The play all games. */
	private boolean playAllGames;

	/** The starting score spinner. */
	private Spinner spiStartingScore;

	/** The spi nb game. */
	private Spinner spiNbGame;

	/** The btn play all. */
	private Button btnPlayAll;

	/** The lbl nb set. */
	private Label lblNbSet;

	/** The parent dialog. */
	private INewContainerDialog parentDialog;

	/** The players. */
	private final ArrayList<IPlayer> players;

	/** The players composite. */
	private PlayerSelectionComposite playersComposite;

	/**
	 * Instantiates a new sets the x01 configuration dialog.
	 */
	public SetX01ConfigurationDialog() {
		super();
		this.gameUiProvider = X01UiPlugin.getService(IGameUiProvider.class);
		this.players = new ArrayList<IPlayer>();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.ui.dialog.IGameDefinitionComposite#createSetConfiguration(org.opendarts.prototype.ui.dialog.NewSetDialog, org.eclipse.swt.widgets.Composite, org.opendarts.prototype.model.game.IGameDefinition)
	 */
	@Override
	public Composite createSetConfiguration(INewContainerDialog dialog,
			Composite parent, IGameDefinition lastGameDefinition) {
		// init
		this.parentDialog = dialog;
		this.startScore = 501;
		this.nbGameToWin = 3;
		this.playAllGames = false;
		this.players.clear();

		// Get last configuration
		GameX01Definition gameDef = null;
		boolean rotate = false;
		if (lastGameDefinition == null) {
			gameDef = new GameX01Definition(501, new ArrayList<IPlayer>(), 3,
					false);
		} else if ((lastGameDefinition != null)
				&& (lastGameDefinition instanceof GameX01Definition)) {
			gameDef = (GameX01Definition) lastGameDefinition;
			rotate = true;
		}

		// Initial game definition
		if (gameDef != null) {
			this.startScore = gameDef.getStartScore();
			this.playAllGames = gameDef.isPlayAllGames();
			this.nbGameToWin = gameDef.getNbGameToWin();
			this.players.addAll(gameDef.getPlayers());
			if (rotate) {
				Collections.rotate(this.players, 1);
			}
		}

		// Main component
		Composite main = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).margins(5, 5)
				.applyTo(main);

		// X01 Description
		Group grpGameDesc = this.createGameDescriptionArea(main);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(grpGameDesc);

		// Players selections
		Group grpPlayers = this.createPlayersArea(main);
		GridDataFactory.fillDefaults().applyTo(grpPlayers);

		return main;
	}

	/**
	 * Creates the game description area.
	 *
	 * @param parent the main
	 * @return the group
	 */
	protected Group createGameDescriptionArea(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().margins(5, 5).numColumns(3)
				.applyTo(group);
		group.setText("X01 Description");

		Label lbl;
		GridDataFactory lblData = GridDataFactory.fillDefaults().align(SWT.END,
				SWT.CENTER);
		GridDataFactory fieldData = GridDataFactory.fillDefaults()
				.align(SWT.BEGINNING, SWT.CENTER).grab(true, false);
		// Starting score
		lbl = new Label(group, SWT.WRAP);
		lbl.setText("Start with:");
		lblData.copy().span(2, 1).applyTo(lbl);

		this.spiStartingScore = new Spinner(group, SWT.NONE);
		fieldData.copy().applyTo(this.spiStartingScore);
		this.spiStartingScore.setMinimum(2);
		this.spiStartingScore.setIncrement(1);
		this.spiStartingScore.setMaximum(1000001);
		this.spiStartingScore.setPageIncrement(100);
		this.spiStartingScore.addSelectionListener(this);
		this.spiStartingScore.setSelection(this.startScore);

		// Play all games
		this.btnPlayAll = new Button(group, SWT.CHECK);
		GridDataFactory.fillDefaults().applyTo(this.btnPlayAll);
		this.btnPlayAll.addSelectionListener(this);
		this.btnPlayAll.setSelection(this.playAllGames);

		// Nb games to win
		this.lblNbSet = new Label(group, SWT.WRAP);
		if (this.playAllGames) {
			this.lblNbSet.setText("Best of: ");
		} else {
			this.lblNbSet.setText("First at: ");
		}
		lblData.copy().applyTo(this.lblNbSet);

		this.spiNbGame = new Spinner(group, SWT.NONE);
		fieldData.copy().applyTo(this.spiNbGame);
		this.spiNbGame.setMinimum(1);
		this.spiNbGame.setIncrement(1);
		this.spiNbGame.addSelectionListener(this);
		this.spiNbGame.setSelection(this.nbGameToWin);

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
		GridLayoutFactory.fillDefaults().margins(5, 5).numColumns(2)
				.applyTo(group);
		group.setText("Players");

		this.playersComposite = new PlayerSelectionComposite(group,
				this.players);
		this.playersComposite.addListener(this);

		return group;
	}

	/**
	 * Sets the focus.
	 */
	@Override
	public void setFocus() {
		this.playersComposite.setFocus();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.ui.dialog.IGameDefinitionComposite#getGameDefinition()
	 */
	@Override
	public IGameDefinition getGameDefinition() {
		if (this.playersComposite.getPlayers().isEmpty()) {
			throw new IllegalArgumentException("Not enought players");
		}
		GameX01Definition def = new GameX01Definition(this.startScore,
				this.playersComposite.getPlayers(), this.nbGameToWin,
				this.playAllGames);
		this.gameUiProvider.registerGameUiService(def,
				X01UiPlugin.getGameX01UiService());
		return def;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// Nothing to do
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(this.spiStartingScore)) {
			this.startScore = this.spiStartingScore.getSelection();
		} else if (obj.equals(this.spiNbGame)) {
			this.nbGameToWin = this.spiNbGame.getSelection();
		} else if (obj.equals(this.btnPlayAll)) {
			this.playAllGames = this.btnPlayAll.getSelection();
			if (this.playAllGames) {
				this.lblNbSet.setText("Best of: ");
			} else {
				this.lblNbSet.setText("First at: ");
			}
		}
		this.parentDialog.notifyUpdate();
	}

	/**
	 * Notify selection change.
	 *
	 * @param players the players
	 */
	@Override
	public void notifySelectionChange(List<IPlayer> players) {
		this.players.clear();
		this.players.addAll(players);
		this.parentDialog.notifyUpdate();

		if (players.size() != 2) {
			this.btnPlayAll.setSelection(false);
			this.btnPlayAll.setEnabled(false);
			this.playAllGames = false;
			this.lblNbSet.setText("First at: ");
		} else {
			this.btnPlayAll.setEnabled(true);
		}
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.ui.dialog.IGameDefinitionComposite#validate()
	 */
	@Override
	public List<ValidationEntry> validate() {
		List<ValidationEntry> result = new ArrayList<ValidationEntry>();
		List<IPlayer> list = this.playersComposite.getPlayers();
		if (list.isEmpty()) {
			result.add(new ValidationEntry(IMessageProvider.ERROR,
					"Need at least two players"));
		} else if (list.size() == 1) {
			result.add(new ValidationEntry(IMessageProvider.WARNING,
					"Playing alone is boring !"));
		}
		return result;
	}

}
