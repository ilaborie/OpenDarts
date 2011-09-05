package org.opendarts.ui.x01.test.dialog;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.experimental.chart.swt.ChartComposite;
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
import org.opendarts.ui.player.label.PlayerLabelProvider;

/**
 * The Class
 */
public class ComputerLevelUITester implements ISelectionChangedListener,
		SelectionListener {

	private final class CopyPlayer implements IPlayer {
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
	}

	/** The Constant NUMBER_FORMAT. */
	private static final NumberFormat NUMBER_FORMAT = new DecimalFormat("0.##");

	// Model
	/** The number of games played. */
	private int nbGames = 100;

	/** The stats. */
	private final Map<IPlayer, PlayerStats> stats;

	/** The start. */
	private int start = 501;

	/** The game. */
	private GameX01 game;

	/** The player. */
	private final List<IComputerPlayer> players;

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
	private TableViewer viewer;

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

	/** The cmp chart. */
	private Composite cmpChart;

	/** The job. */
	private final Job job;

	/** The spinner start. */
	private Spinner spinnerStart;

	/**
	 * Instantiates a new computer level ui tester.
	 */
	public ComputerLevelUITester() {
		super();
		this.stats = new HashMap<IPlayer, PlayerStats>();
		this.players = new ArrayList<IComputerPlayer>();
		this.job = new Job("Process Computer Games") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				final ComputerLevelUITester tester = ComputerLevelUITester.this;

				ISession session = sessionService.getCurrentSession();
				tester.stats.clear();
				for (IPlayer player : tester.players) {

					List<IPlayer> players = new ArrayList<IPlayer>();
					players.add(player);
					players.add(new CopyPlayer());

					IGameDefinition gameDefinition = new GameX01Definition(
							tester.start, players, tester.nbGames, false);
					GameSet set = (GameSet) setService.createNewSet(session,
							gameDefinition);
					tester.gameService = set.getGameService();

					// init stats
					final PlayerStats stats = new PlayerStats(tester.nbGames);
					tester.stats.put(player, stats);

					// UI
					tester.display.asyncExec(new Runnable() {
						@Override
						public void run() {
							tester.viewer.getControl().setEnabled(false);
							tester.spinner.setEnabled(false);
							tester.spinnerStart.setEnabled(false);
							tester.btn.setEnabled(false);
							tester.progress.setMaximum(tester.nbGames);
							tester.progress.setSelection(0);

							if (tester.cmpChart != null
									&& !tester.cmpChart.isDisposed()) {
								// Clear current
								for (Control ctrl : tester.cmpChart
										.getChildren()) {
									ctrl.dispose();
								}
							}
						}
					});

					int current;
					tester.setService.startSet(set);
					while (stats.getPlayed() < nbGames) {
						tester.game = (GameX01) set.getCurrentGame();
						tester.gameService.startGame(tester.game);

						tester.playGame((IComputerPlayer) player);

						// update stats
						current = tester.game.getNbDartToFinish();
						stats.update(current);

						// Update UI
						tester.display.asyncExec(new Runnable() {
							@Override
							public void run() {
								tester.progress.setSelection(stats.getPlayed());
								tester.txtBest.setText(String.valueOf(stats
										.getMin()));
								tester.txtAvg.setText(NUMBER_FORMAT
										.format(stats.getAverage()));
								tester.txtWorst.setText(String.valueOf(stats
										.getMax()));
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
							tester.spinnerStart.setEnabled(true);
							tester.btn.setEnabled(true);
							tester.updateChart();
						}
					});
				}
				return Status.OK_STATUS;
			}
		};
	}

	/**
	 * Update chart.
	 */
	protected void updateChart() {
		if (this.cmpChart != null && !this.cmpChart.isDisposed()) {
			// Retrieve the dataset
			HistogramDataset dataset = this.createHistogramDataset();
			JFreeChart chart = this.buildChart(dataset);

			ChartComposite frame = new ChartComposite(this.cmpChart, SWT.NONE,
					chart, true);
			GridDataFactory.fillDefaults().grab(true, true).applyTo(frame);

			this.cmpChart.redraw();
			this.cmpChart.getParent().layout(true, true);
		}
	}

	/**
	 * Builds the chart.
	 *
	 * @param dataset the dataset
	 * @return the j free chart
	 */
	private JFreeChart buildChart(HistogramDataset dataset) {
		JFreeChart result = ChartFactory.createHistogram("Darts", null, null,
				dataset, PlotOrientation.VERTICAL, true, true, false);

		XYPlot plot = (XYPlot) result.getPlot();
		plot.setDomainPannable(true);
		plot.setRangePannable(true);
		plot.setForegroundAlpha(0.85F);

		NumberAxis axis = (NumberAxis) plot.getRangeAxis();
		axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
		renderer.setBarPainter(new StandardXYBarPainter());
		renderer.setShadowVisible(false);
		return result;
	}

	/**
	 * Creates the histogram dataset.
	 *
	 * @return the histogram dataset
	 */
	private HistogramDataset createHistogramDataset() {
		HistogramDataset result = new HistogramDataset();
		PlayerStats st;
		for (Entry<IPlayer, PlayerStats> entry : this.stats.entrySet()) {
			st = entry.getValue();
			result.addSeries(entry.getKey().getName(), st.getDistribution(),
					100, st.getMin(), st.getMax());
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(
	 * org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection sel = (IStructuredSelection) this.viewer
				.getSelection();
		this.players.clear();
		if (!sel.isEmpty()) {
			for (Object obj : sel.toArray()) {
				this.players.add((IComputerPlayer) obj);
			}
		}
		this.btn.setEnabled(!sel.isEmpty());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse
	 * .swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// Nothing to do
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt
	 * .events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		Object src = e.getSource();
		if (this.btn.equals(src)) {
			this.job.schedule();
		} else if (this.spinner.equals(src)) {
			this.nbGames = this.spinner.getSelection();
		} else if (this.spinnerStart.equals(src)) {
			this.start = this.spinnerStart.getSelection();
		}
	}

	/**
	 * Run.
	 */

	/**
	 * Play game.
	 * @param player 
	 */
	private void playGame(IComputerPlayer player) {
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
				.equalWidth(true).applyTo(shell);

		Composite main = new Composite(shell, SWT.NONE);
		GridDataFactory.fillDefaults().grab(false, true).applyTo(main);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(main);

		Label lbl;
		// Player
		lbl = new Label(main, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(lbl);
		lbl.setText("Computer: ");
		this.viewer = new TableViewer(main, SWT.BORDER | SWT.MULTI);
		GridDataFactory.fillDefaults().grab(true, false)
				.applyTo(this.viewer.getControl());
		this.viewer.setLabelProvider(new PlayerLabelProvider());
		this.viewer.setContentProvider(new ArrayContentProvider());
		this.viewer.addSelectionChangedListener(this);
		this.viewer.setInput(this.getComputerPlayers());

		// Start
		lbl = new Label(main, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(lbl);
		lbl.setText("Start at: ");
		this.spinnerStart = new Spinner(main, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(this.spinnerStart);
		this.spinnerStart.setMaximum(Integer.MAX_VALUE);
		this.spinnerStart.setMinimum(2);
		this.spinnerStart.addSelectionListener(this);
		this.spinnerStart.setSelection(this.start);

		// Nb Games
		lbl = new Label(main, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(lbl);
		lbl.setText("Games to play: ");
		this.spinner = new Spinner(main, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(this.spinner);
		this.spinner.setMaximum(1000000);
		this.spinner.setMinimum(1);
		this.spinner.addSelectionListener(this);
		this.spinner.setSelection(this.nbGames);
		// Button
		lbl = new Label(main, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(lbl);
		this.btn = new Button(main, SWT.PUSH);
		GridDataFactory.fillDefaults().align(SWT.END, SWT.CENTER)
				.applyTo(this.btn);
		this.btn.setText("Launch");
		this.btn.setEnabled(false);
		this.btn.addSelectionListener(this);
		// Separator
		lbl = new Label(main, SWT.HIDE_SELECTION);
		GridDataFactory.fillDefaults().span(2, 1).grab(true, false)
				.applyTo(lbl);
		// Progress bar
		this.progress = new ProgressBar(main, SWT.HORIZONTAL | SWT.SMOOTH);
		GridDataFactory.fillDefaults().span(2, 1).grab(true, false)
				.applyTo(this.progress);
		// Stats
		Composite stats = this.buildStats(main);
		GridDataFactory.fillDefaults().span(2, 1).grab(true, false)
				.applyTo(stats);

		this.cmpChart = new Composite(shell, SWT.BORDER);
		GridLayoutFactory.fillDefaults().applyTo(this.cmpChart);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(this.cmpChart);

		lbl = new Label(this.cmpChart, SWT.WRAP);
		GridDataFactory.fillDefaults().hint(400, 200).applyTo(lbl);

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
	 * @param parent
	 *            the parent
	 * @return the composite
	 */
	private Composite buildStats(Composite parent) {
		Composite main = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(main);
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
	 * @param playerService
	 *            the new player service
	 */
	public void setPlayerService(IPlayerService playerService) {
		this.playerService = playerService;
	}

	/**
	 * Sets the session service.
	 * 
	 * @param sessionService
	 *            the new session service
	 */
	public void setSessionService(ISessionService sessionService) {
		this.sessionService = sessionService;
	}

	/**
	 * Sets the sets the service.
	 * 
	 * @param setService
	 *            the new sets the service
	 */
	public void setSetService(ISetService setService) {
		this.setService = setService;
	}
}
