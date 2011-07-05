package org.opendarts.prototype.ui.x01.dialog;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.opendarts.prototype.ProtoPlugin;
import org.opendarts.prototype.internal.model.game.x01.GameX01Definition;
import org.opendarts.prototype.model.game.IGameDefinition;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.service.player.IPlayerService;
import org.opendarts.prototype.ui.dialog.IGameDefinitionComposite;
import org.opendarts.prototype.ui.dialog.NewSetDialog;
import org.opendarts.prototype.ui.player.label.PlayerLabelProvider;

/**
 * The Class SetX01ConfigurationDialog.
 */
public class SetX01ConfigurationDialog implements IGameDefinitionComposite,
		SelectionListener {

	/** The player service. */
	private final IPlayerService playerService;

	/** The players. */
	private final List<IPlayer> players;

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

	/** The lst player combo. */
	private final List<ComboViewer> lstPlayerCombo;

	/**
	 * Instantiates a new sets the x01 configuration dialog.
	 */
	public SetX01ConfigurationDialog() {
		super();
		this.playerService = ProtoPlugin.getService(IPlayerService.class);
		this.players = new ArrayList<IPlayer>();
		this.lstPlayerCombo = new ArrayList<ComboViewer>();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.ui.dialog.IGameDefinitionComposite#createSetConfiguration(org.opendarts.prototype.ui.dialog.NewSetDialog, org.eclipse.swt.widgets.Composite, org.opendarts.prototype.model.game.IGameDefinition)
	 */
	@Override
	public Composite createSetConfiguration(NewSetDialog dialog,
			Composite parent, IGameDefinition lastGameDefinition) {
		// init
		this.startScore = 501;
		this.nbGameToWin = 3;
		this.playAllGames = false;
		this.players.clear();

		// Get last configuration
		if ((lastGameDefinition != null)
				&& (lastGameDefinition instanceof GameX01Definition)) {
			GameX01Definition gameDef = (GameX01Definition) lastGameDefinition;
			this.startScore = gameDef.getStartScore();
			this.playAllGames = gameDef.isPlayAllGames();
			this.nbGameToWin = gameDef.getNbGameToWin();
			this.players.addAll(gameDef.getPlayers());
			Collections.rotate(this.players, 1);
		}

		// Main component
		Composite main = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().margins(5, 5).applyTo(main);

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
		GridLayoutFactory.fillDefaults().margins(5, 5).numColumns(2)
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
		lblData.copy().applyTo(lbl);

		this.spiStartingScore = new Spinner(group, SWT.NONE);
		fieldData.copy().applyTo(this.spiStartingScore);
		this.spiStartingScore.setMinimum(2);
		this.spiStartingScore.setIncrement(1);
		this.spiStartingScore.setMaximum(1000001);
		this.spiStartingScore.setPageIncrement(100);
		this.spiStartingScore.addSelectionListener(this);
		this.spiStartingScore.setSelection(this.startScore);

		// Nb games to win
		lbl = new Label(group, SWT.WRAP);
		lbl.setText("Legs:");
		lblData.copy().applyTo(lbl);

		this.spiNbGame = new Spinner(group, SWT.NONE);
		fieldData.copy().applyTo(this.spiNbGame);
		this.spiNbGame.setMinimum(1);
		this.spiNbGame.setIncrement(1);
		this.spiNbGame.addSelectionListener(this);
		this.spiNbGame.setSelection(this.nbGameToWin);

		// Play all games
		this.btnPlayAll = new Button(group, SWT.CHECK);
		GridDataFactory.fillDefaults().span(2, 1).applyTo(this.btnPlayAll);
		this.btnPlayAll.setText("Play all games");
		this.btnPlayAll.addSelectionListener(this);
		this.btnPlayAll.setSelection(this.playAllGames);

		return group;
	}

	/**
	 * Creates the players area.
	 *
	 * @param main the main
	 * @return the group
	 */
	protected Group createPlayersArea(Composite main) {
		// TODO multi players
		int nbPlayer = 2;

		Group group = new Group(main, SWT.NONE);
		GridLayoutFactory.fillDefaults().margins(5, 5).numColumns(2)
				.applyTo(group);
		group.setText("Players");

		Label lbl;
		GridDataFactory lblData = GridDataFactory.fillDefaults().align(SWT.END,
				SWT.CENTER);
		GridDataFactory fieldData = GridDataFactory.fillDefaults()
				.align(SWT.BEGINNING, SWT.CENTER).grab(true, false);

		this.lstPlayerCombo.clear();
		ComboViewer cbViewer;
		CCombo combo;
		IPlayer player;
		for (int i = 0; i < nbPlayer; i++) {
			// label
			lbl = new Label(group, SWT.WRAP);
			lbl.setText(MessageFormat.format("Player #{0}:", i));
			lblData.copy().applyTo(lbl);

			// Player combo
			combo = new CCombo(group, SWT.BORDER);
			fieldData.copy().minSize(200, SWT.DEFAULT).grab(true, true)
					.applyTo(combo);
			cbViewer = new ComboViewer(combo);
			cbViewer.setLabelProvider(new PlayerLabelProvider());
			cbViewer.setContentProvider(new ArrayContentProvider());
			cbViewer.setInput(this.playerService.getAllPlayers());

			if (this.players.size() > i) {
				player = this.players.get(i);
				cbViewer.setSelection(new StructuredSelection(player));
				//			} else if (i == 0) {
				//				player = playerService.getPlayer(System.getenv("HOME"));
				//			} else {
				//				player = playerService.getComputerPlayer();
			}
			this.lstPlayerCombo.add(cbViewer);
		}
		return group;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.ui.dialog.IGameDefinitionComposite#getGameDefinition()
	 */
	@Override
	public IGameDefinition getGameDefinition() {
		this.players.clear();
		IStructuredSelection sel;
		for (ComboViewer cb : this.lstPlayerCombo) {
			sel = (IStructuredSelection) cb.getSelection();
			if (!sel.isEmpty()) {
				this.players.add((IPlayer) sel.getFirstElement());
			} else {
				this.players.add(this.playerService.getPlayer(cb.getCCombo()
						.getText()));
			}
		}

		if (this.players.isEmpty()) {
			throw new IllegalArgumentException("Not enought players");
		}
		return new GameX01Definition(this.startScore, this.players,
				this.nbGameToWin, this.playAllGames);
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
		}
	}

}
