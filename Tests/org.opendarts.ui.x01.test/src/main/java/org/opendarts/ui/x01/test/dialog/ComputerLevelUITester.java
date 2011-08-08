package org.opendarts.ui.x01.test.dialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.game.IGameDefinition;
import org.opendarts.core.model.player.IComputerPlayer;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.impl.GameSet;
import org.opendarts.core.service.game.IGameService;
import org.opendarts.core.service.player.IPlayerService;
import org.opendarts.core.service.session.ISessionService;
import org.opendarts.core.service.session.ISetService;
import org.opendarts.core.x01.model.GameX01;
import org.opendarts.core.x01.model.GameX01Definition;
import org.opendarts.core.x01.model.WinningX01DartsThrow;
import org.opendarts.ui.label.PlayerLabelProvider;

/**
 * The Class 
 */
public class ComputerLevelUITester implements ISelectionChangedListener,
		SelectionListener {

	// Model
	/** The number of games played. */
	private int nbGames = 100;

	/** The min. */
	private int min;

	/** The max. */
	private int max;

	/** The count. */
	private int count;

	/** The played. */
	private int played;

	/** The game. */
	private GameX01 game;

	/** The player. */
	private IComputerPlayer player;

	// Services
	/** The session service. */
	private ISessionService sessionService;

	/** The player service. */
	private IPlayerService playerService;

	/** The set service. */
	private ISetService setService;

	/** The game service. */
	private IGameService gameService;

	// UI
	/** The viewer. */
	private ComboViewer viewer;

	/** The display. */
	private Display display;

	/** The shell. */
	private Shell shell;

	/** The spinner. */
	private Spinner spinner;

	/** The btn. */
	private Button btn;

	/** The progress. */
	private ProgressBar progress;

	/** The txt best. */
	private Text txtBest;

	/** The txt avg. */
	private Text txtAvg;

	/** The txt worst. */
	private Text txtWorst;

	/**
	 * Instantiates a new computer level ui tester.
	 */
	public ComputerLevelUITester() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection sel = (IStructuredSelection) this.viewer
				.getSelection();
		if (sel.isEmpty()) {
			this.player = null;
		} else {
			this.player = (IComputerPlayer) sel.getFirstElement();
			;
		}
		this.btn.setEnabled(!sel.isEmpty());
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
		Object src = e.getSource();
		if (this.btn.equals(src)) {
			this.run();
		} else if (this.spinner.equals(src)) {
			this.nbGames = this.spinner.getSelection();
		}
	}

	/**
	 * Run.
	 */
	private void run() {
		ISession session = sessionService.getSession();
		List<IPlayer> players = Collections
				.singletonList((IPlayer) this.player);

		IGameDefinition gameDefinition = new GameX01Definition(501, players,
				this.nbGames, false);
		GameSet set = (GameSet) setService
				.createNewSet(session, gameDefinition);
		this.gameService = set.getGameService();

		// init stats
		this.min = Integer.MAX_VALUE;
		this.count = 0;
		this.max = 0;
		this.played = 0;

		// UI
		this.viewer.getControl().setEnabled(false);
		this.spinner.setEnabled(false);
		this.btn.setEnabled(false);
		this.progress.setMaximum(this.nbGames);
		this.progress.setSelection(this.played);

		int current;
		this.setService.startSet(set);
		while (this.played < nbGames) {
			this.game = (GameX01) set.getCurrentGame();
			this.gameService.startGame(this.game);

			this.playGame();

			// update stats
			this.played++;
			current = this.game.getNbDartToFinish();
			this.min = Math.min(this.min, current);
			this.max = Math.max(this.max, current);
			this.count += current;

			// Update UI
			this.progress.setSelection(this.played);
			this.txtBest.setText(String.valueOf(this.min));
			this.txtAvg.setText(String.valueOf(((double) this.count)
					/ ((double) this.played)));
			this.txtWorst.setText(String.valueOf(this.max));

			this.game = null;
		}

		this.viewer.getControl().setEnabled(true);
		this.spinner.setEnabled(true);
		this.btn.setEnabled(true);
	}

	/**
	 * Play game.
	 */
	private void playGame() {
		IDartsThrow dartsThrow;
		while (!this.game.isFinished()) {
			dartsThrow = this.gameService.getComputerDartsThrow(this.game,
					this.player).getDartsThrow();
			if (dartsThrow instanceof WinningX01DartsThrow) {
				this.gameService.addWinningPlayerThrow(this.game, this.player,
						dartsThrow);
			} else {
				this.gameService.addPlayerThrow(this.game, this.player,
						dartsThrow);
			}
		}
	}

	/**
	 * Inits the UI.
	 */
	public Shell startup() {
		this.display = Display.getDefault();
		this.shell = new Shell(this.display, SWT.SHELL_TRIM);
		this.shell.setText("Test computer player");
		GridLayoutFactory.fillDefaults().margins(5, 5).numColumns(2)
				.applyTo(shell);
		Label lbl;
		// Player
		lbl = new Label(this.shell, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(lbl);
		lbl.setText("Computer: ");
		this.viewer = new ComboViewer(this.shell, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false)
				.applyTo(this.viewer.getControl());
		this.viewer.setLabelProvider(new PlayerLabelProvider());
		this.viewer.setContentProvider(new ArrayContentProvider());
		this.viewer.addSelectionChangedListener(this);
		this.viewer.setInput(this.getComputerPlayers());
		// Nb Games
		lbl = new Label(this.shell, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(lbl);
		lbl.setText("Games to play: ");
		this.spinner = new Spinner(this.shell, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(this.spinner);
		this.spinner.setMaximum(1000000);
		this.spinner.setMinimum(1);
		this.spinner.addSelectionListener(this);
		this.spinner.setSelection(this.nbGames);
		// Button
		lbl = new Label(this.shell, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(lbl);
		this.btn = new Button(this.shell, SWT.PUSH);
		GridDataFactory.fillDefaults().align(SWT.END, SWT.CENTER)
				.applyTo(this.btn);
		this.btn.setText("Launch");
		this.btn.setEnabled(false);
		this.btn.addSelectionListener(this);
		// Separator
		lbl = new Label(this.shell, SWT.HIDE_SELECTION);
		GridDataFactory.fillDefaults().span(2, 1).grab(true, false)
				.applyTo(lbl);
		// Progress bar
		this.progress = new ProgressBar(shell, SWT.HORIZONTAL | SWT.SMOOTH);
		GridDataFactory.fillDefaults().span(2, 1).grab(true, false)
				.applyTo(this.progress);
		// Stats
		Composite stats = this.buildStats(this.shell);
		GridDataFactory.fillDefaults().span(2, 1).grab(true, false)
				.applyTo(stats);
		// Opening
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		return shell;
	}

	/**
	 * Builds the stats.
	 *
	 * @param parent the parent
	 * @return the composite
	 */
	private Composite buildStats(Composite parent) {
		Composite main = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(6).equalWidth(true).applyTo(main);

		Label lbl;

		// Best
		lbl = new Label(main, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(lbl);
		lbl.setText("Best leg:");

		this.txtBest = new Text(main, SWT.READ_ONLY);
		this.txtBest.setText("-");
		GridDataFactory.fillDefaults().hint(100, SWT.DEFAULT).applyTo(this.txtBest);

		// Average
		lbl = new Label(main, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(lbl);
		lbl.setText("Avg. leg:");

		this.txtAvg = new Text(main, SWT.READ_ONLY);
		this.txtAvg.setText("-");
		GridDataFactory.fillDefaults().applyTo(this.txtAvg);

		// Worst
		lbl = new Label(main, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(lbl);
		lbl.setText("Worst leg:");

		this.txtWorst = new Text(main, SWT.READ_ONLY);
		this.txtWorst.setText("-");
		GridDataFactory.fillDefaults().applyTo(this.txtWorst);

		return main;
	}

	/**
	 * Gets the computer players.
	 *
	 * @return the computer players
	 */
	private List<IComputerPlayer> getComputerPlayers() {
		List<IComputerPlayer> result = new ArrayList<IComputerPlayer>();
		for (IPlayer player : this.playerService.getAllPlayers()) {
			if (player instanceof IComputerPlayer) {
				result.add((IComputerPlayer) player);
			}
		}
		return result;
	}

	/**
	 * Sets the player service.
	 *
	 * @param playerService the new player service
	 */
	public void setPlayerService(IPlayerService playerService) {
		this.playerService = playerService;
	}

	/**
	 * Sets the session service.
	 *
	 * @param sessionService the new session service
	 */
	public void setSessionService(ISessionService sessionService) {
		this.sessionService = sessionService;
	}

	/**
	 * Sets the sets the service.
	 *
	 * @param setService the new sets the service
	 */
	public void setSetService(ISetService setService) {
		this.setService = setService;
	}
}
