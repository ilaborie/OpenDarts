package org.opendarts.ui.x01.test.dialog;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
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
	
	/** The Constant NUMBER_FORMAT. */
	private static final NumberFormat NUMBER_FORMAT = new DecimalFormat("0.##");

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

	/** The job. */
	private final Job job;

	/**
	 * Instantiates a new computer level ui tester.
	 */
	public ComputerLevelUITester() {
		super();

		this.job = new Job("Process Computer Games") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				final ComputerLevelUITester tester = ComputerLevelUITester.this;

				ISession session = sessionService.getSession();
				List<IPlayer> players = new ArrayList<IPlayer>();
				players.add(tester.player);
				players.add(new IPlayer() {
					@Override
					public String toString() {
						return "x";
					}

					@Override
					public boolean isComputer() {
						return false;
					}

					@Override
					public String getUuid() {
						return null;
					}

					@Override
					public String getName() {
						return "Dumbo";
					}
				});

				IGameDefinition gameDefinition = new GameX01Definition(501,
						players, tester.nbGames, false);
				GameSet set = (GameSet) setService.createNewSet(session,
						gameDefinition);
				tester.gameService = set.getGameService();

				// init stats
				tester.min = Integer.MAX_VALUE;
				tester.count = 0;
				tester.max = 0;
				tester.played = 0;

				// UI
				tester.display.asyncExec(new Runnable() {
					@Override
					public void run() {
						tester.viewer.getControl().setEnabled(false);
						tester.spinner.setEnabled(false);
						tester.btn.setEnabled(false);
						tester.progress.setMaximum(tester.nbGames);
						tester.progress.setSelection(tester.played);
					}
				});

				int current;
				tester.setService.startSet(set);
				while (tester.played < nbGames) {
					tester.game = (GameX01) set.getCurrentGame();
					tester.gameService.startGame(tester.game);

					tester.playGame();

					// update stats
					tester.played++;
					current = tester.game.getNbDartToFinish();
					tester.min = Math.min(tester.min, current);
					tester.max = Math.max(tester.max, current);
					tester.count += current;

					// Update UI
					tester.display.asyncExec(new Runnable() {
						@Override
						public void run() {
							tester.progress.setSelection(tester.played);
							tester.txtBest.setText(String.valueOf(tester.min));
							double avg = ((double) tester.count)
							/ ((double) tester.played);
							tester.txtAvg.setText(NUMBER_FORMAT.format(avg));
							tester.txtWorst.setText(String.valueOf(tester.max));
						}
					});
					tester.game = null;
				}
				// UI reactivation
				tester.display.asyncExec(new Runnable() {
					@Override
					public void run() {
						tester.viewer.getControl().setEnabled(true);
						tester.spinner.setEnabled(true);
						tester.btn.setEnabled(true);
					}
				});
				return Status.OK_STATUS;
			}
		};
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
			this.job.schedule();
		} else if (this.spinner.equals(src)) {
			this.nbGames = this.spinner.getSelection();
		}
	}

	/**
	 * Run.
	 */

	/**
	 * Play game.
	 */
	private void playGame() {
		IDartsThrow dartsThrow;
		IPlayer p;
		while (!this.game.isFinished()) {
			p = this.game.getCurrentPlayer();

			dartsThrow = this.gameService.getComputerDartsThrow(this.game,
					(IComputerPlayer) player).getDartsThrow();
			if (dartsThrow instanceof WinningX01DartsThrow) {
				this.gameService
						.addWinningPlayerThrow(this.game, p, dartsThrow);
			} else {
				this.gameService.addPlayerThrow(this.game, p, dartsThrow);
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
		this.viewer = new ComboViewer(this.shell, SWT.BORDER | SWT.READ_ONLY);
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
		GridLayoutFactory.fillDefaults().numColumns(6).equalWidth(true)
				.applyTo(main);

		Label lbl;

		// Best
		lbl = new Label(main, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(lbl);
		lbl.setText("Best leg:");

		this.txtBest = new Text(main, SWT.READ_ONLY);
		this.txtBest.setText("-");
		GridDataFactory.fillDefaults().hint(100, SWT.DEFAULT)
				.applyTo(this.txtBest);

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
