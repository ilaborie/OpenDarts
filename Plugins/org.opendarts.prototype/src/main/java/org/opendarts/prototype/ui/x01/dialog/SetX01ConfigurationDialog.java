package org.opendarts.prototype.ui.x01.dialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.opendarts.prototype.ProtoPlugin;
import org.opendarts.prototype.internal.model.game.x01.GameX01Definition;
import org.opendarts.prototype.model.game.IGameDefinition;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.service.player.IPlayerService;
import org.opendarts.prototype.ui.ISharedImages;
import org.opendarts.prototype.ui.dialog.IGameDefinitionComposite;
import org.opendarts.prototype.ui.dialog.NewSetDialog;
import org.opendarts.prototype.ui.dialog.ValidationEntry;
import org.opendarts.prototype.ui.player.label.PlayerLabelProvider;

/**
 * The Class SetX01ConfigurationDialog.
 */
public class SetX01ConfigurationDialog implements IGameDefinitionComposite,
		SelectionListener, ISelectionChangedListener {

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

	/** The current player. */
	private IPlayer currentPlayer;

	/** The btn user add. */
	private Button btnUserAdd;

	/** The btn user del. */
	private Button btnUserDel;

	/** The btn up. */
	private Button btnUp;

	/** The btn down. */
	private Button btnDown;

	/** The btn user new. */
	private Button btnUserNew;

	/** The table players. */
	private TableViewer tablePlayers;

	/** The parent dialog. */
	private NewSetDialog parentDialog;

	/**
	 * Instantiates a new sets the x01 configuration dialog.
	 *
	 */
	public SetX01ConfigurationDialog() {
		super();
		this.playerService = ProtoPlugin.getService(IPlayerService.class);
		this.players = new ArrayList<IPlayer>();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.ui.dialog.IGameDefinitionComposite#createSetConfiguration(org.opendarts.prototype.ui.dialog.NewSetDialog, org.eclipse.swt.widgets.Composite, org.opendarts.prototype.model.game.IGameDefinition)
	 */
	@Override
	public Composite createSetConfiguration(NewSetDialog dialog,
			Composite parent, IGameDefinition lastGameDefinition) {
		// init
		this.parentDialog = dialog;
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
		Group group = new Group(main, SWT.NONE);
		GridLayoutFactory.fillDefaults().margins(5, 5).numColumns(2)
				.applyTo(group);
		group.setText("Players");

		this.tablePlayers = new TableViewer(group, SWT.V_SCROLL);
		GridDataFactory.fillDefaults().hint(100, 60).grab(true, true)
				.applyTo(this.tablePlayers.getTable());
		this.tablePlayers.setLabelProvider(new PlayerLabelProvider());
		this.tablePlayers.setContentProvider(new ArrayContentProvider());
		this.tablePlayers.setInput(this.players);
		this.tablePlayers.addSelectionChangedListener(this);

		// buttons
		Composite cmpBtn = new Composite(group, SWT.NONE);
		GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.BEGINNING)
				.applyTo(cmpBtn);
		GridLayoutFactory.fillDefaults().applyTo(cmpBtn);

		// Add
		this.btnUserAdd = new Button(cmpBtn, SWT.PUSH);
		this.btnUserAdd.setImage(ProtoPlugin
				.getImage(ISharedImages.IMG_USER_ADD));
		GridDataFactory.fillDefaults().applyTo(this.btnUserAdd);
		this.btnUserAdd.addSelectionListener(this);

		// Remove
		this.btnUserDel = new Button(cmpBtn, SWT.PUSH);
		this.btnUserDel.setImage(ProtoPlugin
				.getImage(ISharedImages.IMG_USER_DELETE));
		GridDataFactory.fillDefaults().applyTo(this.btnUserDel);
		this.btnUserDel.setEnabled(this.currentPlayer != null);
		this.btnUserDel.addSelectionListener(this);

		new Label(cmpBtn, SWT.HORIZONTAL);

		// New
		this.btnUserNew = new Button(cmpBtn, SWT.PUSH);
		this.btnUserNew.setImage(ProtoPlugin
				.getImage(ISharedImages.IMG_USER_NEW));
		GridDataFactory.fillDefaults().applyTo(this.btnUserNew);
		this.btnUserNew.addSelectionListener(this);

		new Label(cmpBtn, SWT.HORIZONTAL);

		// up & down
		this.btnUp = new Button(cmpBtn, SWT.PUSH);
		this.btnUp.setImage(ProtoPlugin.getImage(ISharedImages.IMG_UP));
		GridDataFactory.fillDefaults().applyTo(this.btnUp);
		this.btnUp.setEnabled(this.currentPlayer != null);
		this.btnUp.addSelectionListener(this);

		this.btnDown = new Button(cmpBtn, SWT.PUSH);
		this.btnDown.setImage(ProtoPlugin.getImage(ISharedImages.IMG_DOWN));
		GridDataFactory.fillDefaults().applyTo(this.btnDown);
		this.btnDown.setEnabled(this.currentPlayer != null);
		this.btnDown.addSelectionListener(this);
		return group;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.ui.dialog.IGameDefinitionComposite#getGameDefinition()
	 */
	@Override
	public IGameDefinition getGameDefinition() {
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
		} else if (obj.equals(this.btnUserAdd)) {
			this.addPlayer();
		} else if (obj.equals(this.btnUserDel)) {
			boolean remove = this.players.remove(this.currentPlayer);
			if (remove) {
				this.tablePlayers.remove(this.currentPlayer);
				this.tablePlayers.setSelection(StructuredSelection.EMPTY);
			}
		} else if (obj.equals(this.btnUserNew)) {
			this.newPlayer();
		} else if (obj.equals(this.btnUp)) {
			int index = this.players.indexOf(this.currentPlayer);
			Collections.swap(this.players, index, index - 1);
			this.tablePlayers.setInput(this.players);
			this.updateButtonsState();
		} else if (obj.equals(this.btnDown)) {
			int index = this.players.indexOf(this.currentPlayer);
			Collections.swap(this.players, index, index + 1);
			this.tablePlayers.setInput(this.players);
			this.updateButtonsState();
		}
		this.parentDialog.notifyUpdate();
	}

	/**
	 * New player.
	 */
	private void newPlayer() {
		InputDialog dialog = new InputDialog(this.parentDialog.getShell(),
				"New user", "Enter the user name:", "<name>",
				new IInputValidator() {

					/* (non-Javadoc)
					 * @see org.eclipse.jface.dialogs.IInputValidator#isValid(java.lang.String)
					 */
					@Override
					public String isValid(String newText) {
						return "".equals(newText) ? "Name should not being empty"
								: null;
					}
				});
		if (dialog.open() == Window.OK) {
			IPlayer player = this.playerService.getPlayer(dialog.getValue());
			if (player != null) {
				this.players.add(player);
				this.tablePlayers.add(player);
				this.tablePlayers.setSelection(new StructuredSelection(player));
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection sel = (IStructuredSelection) this.tablePlayers
				.getSelection();
		if (!sel.isEmpty()) {
			this.currentPlayer = (IPlayer) sel.getFirstElement();
		} else {
			this.currentPlayer = null;
		}
		this.updateButtonsState();

	}

	/**
	 * Update buttons state.
	 */
	private void updateButtonsState() {
		this.btnUserDel.setEnabled(this.currentPlayer != null);

		int index = this.players.indexOf(this.currentPlayer);
		this.btnUp.setEnabled(this.currentPlayer != null && (index > 0));
		this.btnDown.setEnabled(this.currentPlayer != null
				&& (index < (this.players.size() - 1)));
	}

	/**
	 * Adds the player.
	 */
	private void addPlayer() {
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				this.parentDialog.getShell(), new PlayerLabelProvider());
		dialog.setImage(ProtoPlugin.getImage(ISharedImages.IMG_OBJ_USER));
		dialog.setTitle("");
		dialog.setElements(this.playerService.getAllPlayers().toArray());
		dialog.setEmptyListMessage("Please select at least one player");
		dialog.setHelpAvailable(false);
		dialog.setMessage("Choose player(s)");
		dialog.setMultipleSelection(true);

		if (dialog.open() == Window.OK) {
			IPlayer player;
			List<IPlayer> added = new ArrayList<IPlayer>();
			for (Object obj : dialog.getResult()) {
				player = (IPlayer) obj;
				if (!this.players.contains(player) && this.players.add(player)) {
					this.tablePlayers.add(player);
					added.add(player);
				}
				this.tablePlayers.setSelection(new StructuredSelection(added),
						true);
			}

		}
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.ui.dialog.IGameDefinitionComposite#validate()
	 */
	@Override
	public List<ValidationEntry> validate() {
		List<ValidationEntry> result = new ArrayList<ValidationEntry>();
		if (players.isEmpty()) {
			result.add(new ValidationEntry(IMessageProvider.ERROR,
					"Need at least two players"));
		} else if (players.size() == 1) {
			result.add(new ValidationEntry(IMessageProvider.WARNING,
					"Playing alone is boring !"));
		}
		return result;
	}

}
