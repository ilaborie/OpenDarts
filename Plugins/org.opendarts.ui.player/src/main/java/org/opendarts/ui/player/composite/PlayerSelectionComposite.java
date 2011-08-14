package org.opendarts.ui.player.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.jface.dialogs.IInputValidator;
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
import org.eclipse.swt.widgets.Label;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.service.player.IPlayerService;
import org.opendarts.ui.OpenDartsUiPlugin;
import org.opendarts.ui.player.ISharedImages;
import org.opendarts.ui.player.OpenDartsPlayerUiPlugin;
import org.opendarts.ui.player.dialog.PlayerSelectionDialog;
import org.opendarts.ui.player.label.PlayerLabelProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerSelectionComposite extends Composite implements
		ISelectionChangedListener, SelectionListener {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(PlayerSelectionComposite.class);

	/** The player service. */
	private final IPlayerService playerService;

	/** The players. */
	private final List<IPlayer> players;

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

	/** The listeners. */
	private final CopyOnWriteArraySet<IPlayerSelectionListener> listeners;

	/**
	 * Instantiates a new player selection composite.
	 */
	public PlayerSelectionComposite(Composite parent, List<IPlayer> players) {
		super(parent, SWT.NONE);

		this.players = new ArrayList<IPlayer>(players);
		this.playerService = OpenDartsPlayerUiPlugin
				.getService(IPlayerService.class);
		this.listeners = new CopyOnWriteArraySet<IPlayerSelectionListener>();

		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(this);
		this.createContents(this);
	}

	/**
	 * Adds the listener.
	 *
	 * @param listener the listener
	 */
	public void addListener(IPlayerSelectionListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * Removes the listener.
	 *
	 * @param listener the listener
	 */
	public void removeListener(IPlayerSelectionListener listener) {
		this.listeners.remove(listener);
	}

	/**
	 * Fire players updated.
	 */
	private void firePlayersUpdated() {
		List<IPlayer> list = Collections.unmodifiableList(this.players);
		for (IPlayerSelectionListener listener : this.listeners) {
			try {
				listener.notifySelectionChange(list);
			} catch (Throwable e) {
				LOG.warn("Error in listener", e);
			}
		}
	}

	/**
	 * Instantiates a new player selection composite.
	 *
	 * @param parent the parent
	 */
	public PlayerSelectionComposite(Composite parent) {
		this(parent, new ArrayList<IPlayer>());
	}

	/**
	 * Creates the contents.
	 *
	 * @param parent the parent
	 */
	private void createContents(Composite parent) {

		this.tablePlayers = new TableViewer(parent, SWT.V_SCROLL);
		GridDataFactory.fillDefaults().hint(100, 60).grab(true, true)
				.applyTo(this.tablePlayers.getTable());
		this.tablePlayers.setLabelProvider(new PlayerLabelProvider());
		this.tablePlayers.setContentProvider(new ArrayContentProvider());
		this.tablePlayers.setInput(this.players);
		this.tablePlayers.addSelectionChangedListener(this);

		// buttons
		Composite cmpBtn = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.BEGINNING)
				.applyTo(cmpBtn);
		GridLayoutFactory.fillDefaults().applyTo(cmpBtn);

		// Add
		this.btnUserAdd = new Button(cmpBtn, SWT.PUSH);
		this.btnUserAdd.setImage(OpenDartsPlayerUiPlugin
				.getImage(ISharedImages.IMG_USER_ADD));
		GridDataFactory.fillDefaults().applyTo(this.btnUserAdd);
		this.btnUserAdd.addSelectionListener(this);

		// Remove
		this.btnUserDel = new Button(cmpBtn, SWT.PUSH);
		this.btnUserDel.setImage(OpenDartsPlayerUiPlugin
				.getImage(ISharedImages.IMG_USER_DELETE));
		GridDataFactory.fillDefaults().applyTo(this.btnUserDel);
		this.btnUserDel.setEnabled(this.currentPlayer != null);
		this.btnUserDel.addSelectionListener(this);

		new Label(cmpBtn, SWT.HORIZONTAL);

		// New
		this.btnUserNew = new Button(cmpBtn, SWT.PUSH);
		this.btnUserNew.setImage(OpenDartsPlayerUiPlugin
				.getImage(ISharedImages.IMG_USER_NEW));
		GridDataFactory.fillDefaults().applyTo(this.btnUserNew);
		this.btnUserNew.addSelectionListener(this);

		new Label(cmpBtn, SWT.HORIZONTAL);

		// up & down
		this.btnUp = new Button(cmpBtn, SWT.PUSH);
		this.btnUp.setImage(OpenDartsUiPlugin
				.getImage(org.opendarts.ui.utils.ISharedImages.IMG_UP));
		GridDataFactory.fillDefaults().applyTo(this.btnUp);
		this.btnUp.setEnabled(this.currentPlayer != null);
		this.btnUp.addSelectionListener(this);

		this.btnDown = new Button(cmpBtn, SWT.PUSH);
		this.btnDown.setImage(OpenDartsUiPlugin
				.getImage(org.opendarts.ui.utils.ISharedImages.IMG_DOWN));
		GridDataFactory.fillDefaults().applyTo(this.btnDown);
		this.btnDown.setEnabled(this.currentPlayer != null);
		this.btnDown.addSelectionListener(this);
	}

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	public List<IPlayer> getPlayers() {
		return Collections.unmodifiableList(this.players);
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
		if (obj.equals(this.btnUserAdd)) {
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
		this.firePlayersUpdated();
	}

	/**
	 * New player.
	 */
	private void newPlayer() {
		InputDialog dialog = new InputDialog(this.getShell(), "New user",
				"Enter the user name:", "<name>", new IInputValidator() {

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
			IPlayer player = this.playerService.createPlayer(dialog.getValue());
			if (player != null) {
				this.players.add(player);
				this.tablePlayers.add(player);
				this.tablePlayers.setSelection(new StructuredSelection(player));
			}
		}
	}

	/**
	 * Update buttons state.
	 */
	private void updateButtonsState() {
		this.btnUserDel.setEnabled(this.currentPlayer != null);

		int index = this.players.indexOf(this.currentPlayer);
		this.btnUp.setEnabled((this.currentPlayer != null) && (index > 0));
		this.btnDown.setEnabled((this.currentPlayer != null)
				&& (index < (this.players.size() - 1)));
	}

	/**
	 * Adds the player.
	 */
	private void addPlayer() {
		PlayerSelectionDialog dialog = new PlayerSelectionDialog(
				this.getShell());
		if (dialog.open() == Window.OK) {
			List<IPlayer> added = new ArrayList<IPlayer>(dialog.getPlayers());
			for (IPlayer player : added) {
				if (!this.players.contains(player) && this.players.add(player)) {
					this.tablePlayers.add(player);
				}
				this.tablePlayers.setSelection(new StructuredSelection(added),
						true);
			}

		}
	}

}
