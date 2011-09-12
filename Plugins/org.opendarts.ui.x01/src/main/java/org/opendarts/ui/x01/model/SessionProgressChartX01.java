package org.opendarts.ui.x01.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYDrawableAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.Drawable;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.model.IElementStats;
import org.opendarts.core.stats.model.IStatsEntry;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.ui.stats.model.IChart;
import org.opendarts.ui.stats.service.IStatsUiService;
import org.opendarts.ui.x01.X01UiPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ProgressChartX01.
 */
public abstract class SessionProgressChartX01<T> implements IChart {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(SessionProgressChartX01.class);

	/** The session. */
	private final ISession session;

	/** The name. */
	private final String name;

	/** The stat key. */
	private final String statKey;

	/** The chart. */
	private JFreeChart chart;

	/** The service. */
	private final IStatsService service;

	/** The player colors. */
	private final Map<IPlayer, Color> playerColors;

	/** The player series. */
	private final Map<IPlayer, XYSeries> playerSeries;

	/** The game index. */
	private final Map<IGame, Integer> gameIndex;

	/** The set index. */
	private final Map<ISet, Integer> setIndex;

	// TODO prefs
	private final Color colorEven = new Color(0, 0, 127, 25);
	private final Color colorOdd = new Color(0, 127, 0, 25);
	private final Color win = Color.green;

	/**
	 * Instantiates a new avg leg chart x01.
	 * 
	 * @param name
	 *            the name
	 * @param statKey
	 *            the stat key
	 * @param session
	 *            the session
	 * @param service
	 *            the service
	 */
	public SessionProgressChartX01(String name, String statKey,
			ISession session, IStatsService service) {
		super();
		this.name = name;
		this.statKey = statKey;
		this.session = session;
		this.service = service;

		this.playerColors = new HashMap<IPlayer, Color>();
		this.playerSeries = new HashMap<IPlayer, XYSeries>();

		this.gameIndex = new HashMap<IGame, Integer>();
		this.setIndex = new HashMap<ISet, Integer>();
	}

	/**
	 * Creates the chart.
	 * 
	 * @return the j free chart
	 */
	protected JFreeChart createChart() {
		XYSeriesCollection dataset = this.createDataset();
		JFreeChart chart = this.buildChart(dataset);
		return chart;
	}

	/**
	 * Creates the dataset.
	 * 
	 * @return the dataset
	 */
	private XYSeriesCollection createDataset() {
		this.playerSeries.clear();
		XYSeriesCollection dataset = new XYSeriesCollection();

		// Set
		int index = 0;
		for (ISet set : this.session.getAllGame()) {
			this.setIndex.put(set, index);
			this.addSetData(set, dataset);
			index += set.getAllGame().size();
		}

		return dataset;
	}

	/**
	 * Adds the set data.
	 * 
	 * @param set
	 *            the set
	 * @param dataset
	 *            the dataset
	 */
	private void addSetData(ISet set, XYSeriesCollection dataset) {
		String gameStatKey = this.statKey.replace("Session", "Game");

		List<IGame> games = set.getAllGame();
		IGame game;
		int index = this.setIndex.get(set);

		for (int i = 0; i < games.size(); i++) {
			game = games.get(i);
			this.gameIndex.put(game, index + i + 1);
			this.addGameData(dataset, gameStatKey, game);
		}
	}

	/**
	 * Adds the game data.
	 * 
	 * @param dataset
	 *            the dataset
	 * @param gameStatKey
	 *            the game stat key
	 * @param game
	 *            the game
	 */
	protected void addGameData(XYSeriesCollection dataset, String gameStatKey,
			IGame game) {
		IElementStats<IGame> eltStats;
		Map<IPlayer, IStatsEntry<T>> entries;
		IStatsEntry<T> stEntry;
		IPlayer player;
		Double val;
		XYSeries series;
		eltStats = this.service.getGameStats(game);
		entries = eltStats.getStatsEntries(gameStatKey);
		for (Entry<IPlayer, IStatsEntry<T>> entry : entries.entrySet()) {
			stEntry = entry.getValue();
			player = entry.getKey();

			series = this.getSeries(dataset, player);

			val = this.getValue(stEntry);

			if (val != null) {
				series.add((double) this.getGameIndex(game), val);
			}
		}
	}

	/**
	 * Gets the game index.
	 * 
	 * @param game
	 *            the game
	 * @return the game index
	 */
	protected int getGameIndex(IGame game) {
		return this.gameIndex.get(game);
	}

	/**
	 * Gets the game value.
	 * 
	 * @param game
	 *            the game
	 * @param player
	 *            the player
	 * @return the game value
	 */
	private Double getGameValue(IGame game, IPlayer player) {
		IElementStats<IGame> eltStats = this.service.getGameStats(game);
		String gameStatKey = this.statKey.replace("Session", "Game");
		Map<IPlayer, IStatsEntry<T>> entries = eltStats
				.getStatsEntries(gameStatKey);
		return this.getValue(entries.get(player));
	}

	/**
	 * Gets the series.
	 * 
	 * @param dataset
	 * 
	 * @param player
	 *            the player
	 * @return the series
	 */
	protected XYSeries getSeries(XYSeriesCollection dataset, IPlayer player) {
		XYSeries series = this.playerSeries.get(player);
		if (series == null) {
			series = new XYSeries(this.getSerieName(player));
			dataset.addSeries(series);
			this.playerSeries.put(player, series);
		}
		return series;
	}

	/**
	 * Gets the serie name.
	 * 
	 * @param player
	 *            the player
	 * @return the serie name
	 */
	private Comparable<String> getSerieName(IPlayer player) {
		return player.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opendarts.ui.stats.model.IChart#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opendarts.ui.stats.model.IChart#getStatKey()
	 */
	@Override
	public String getStatKey() {
		return this.statKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opendarts.ui.stats.model.IChart#getElement()
	 */
	@Override
	public Object getElement() {
		return this.session;
	}

	/**
	 * Gets the category.
	 * 
	 * @param set
	 *            the set
	 * @return the category
	 */
	protected String getCategory(ISet set) {
		int index = this.session.getAllGame().indexOf(set);
		return "Set #" + (1 + index);
	}

	/**
	 * Gets the value.
	 * 
	 * @param stEntry
	 *            the st entry
	 * @return the value
	 */
	protected abstract Double getValue(IStatsEntry<T> stEntry);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opendarts.ui.stats.model.IChart#buildChart()
	 */
	@Override
	public JFreeChart getChart() {
		if (this.chart == null) {
			this.chart = this.createChart();
		}
		return this.chart;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opendarts.ui.stats.model.IChart#getService()
	 */
	@Override
	public IStatsUiService getService() {
		return X01UiPlugin.getStatsUiService();
	}

	/**
	 * Builds the chart.
	 * 
	 * @param dataset
	 *            the dataset
	 * @return the j free chart
	 */
	private JFreeChart buildChart(XYSeriesCollection dataset) {
		// Create chart
		JFreeChart chart = ChartFactory.createXYLineChart(this.getName(),
				"Legs", null, dataset, PlotOrientation.VERTICAL, true, true,
				false);

		// Configure plot
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setForegroundAlpha(0.66F);
		plot.setBackgroundPaint(Color.white);

		// clear x axis
		NumberAxis axis = (NumberAxis) plot.getDomainAxis();
		axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// Create players colors
		this.initializePlayerColors();

		// Better renderer
		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot
				.getRenderer();
		int serieIndex;
		for (Entry<IPlayer, XYSeries> entry : this.playerSeries.entrySet()) {
			serieIndex = dataset.getSeries().indexOf(entry.getValue());
			if (serieIndex >= 0 && dataset.getSeries(serieIndex) != null) {
				renderer.setSeriesShapesVisible(serieIndex, true);
				renderer.setSeriesPaint(serieIndex,
						this.playerColors.get(entry.getKey()));
			} else {
				LOG.warn("Could not retrive series for Player: {}",
						entry.getValue());
			}
		}
		renderer.setDrawOutlines(true);

		// Player avg during set
		this.displaySessionAvg(plot);

		// highlight the winning
		this.displayWinning(plot);

		// category marker on set
		Color color;

		// Sets marker
		double offset = .4d;

		int from = 0;
		int to = 0;
		int i = 0;
		IntervalMarker marker;
		for (ISet set : this.session.getAllGame()) {
			if ((i % 2) == 0) {
				color = this.colorOdd;
			} else {
				color = this.colorEven;
			}

			from = to + 1;
			to = from + (set.getAllGame().size() - 1);

			marker = new IntervalMarker(from - offset, offset + to);
			marker.setLabel("Set #" + (i + 1));

			marker.setLabelPaint(this.playerColors.get(set.getWinner()));

			marker.setLabelAnchor(RectangleAnchor.BOTTOM);
			marker.setLabelTextAnchor(TextAnchor.BOTTOM_CENTER);
			marker.setPaint(color);

			plot.addDomainMarker(marker, Layer.BACKGROUND);

			i++;
		}

		return chart;
	}

	/**
	 * Initialize player colors.
	 */
	private void initializePlayerColors() {
		List<Color> colors = Arrays.asList(Color.cyan, Color.magenta,
				Color.orange, Color.blue, Color.red, Color.yellow, Color.pink);
		int i = 0;
		Color color;
		for (IPlayer player : this.playerSeries.keySet()) {
			if ((i + 1) < colors.size()) {
				color = colors.get(i);
			} else {
				color = Color.lightGray;
			}
			this.playerColors.put(player, color);
			i++;
		}
	}

	/**
	 * Display session avg.
	 * 
	 * @param plot
	 *            the plot
	 * @param colors
	 *            the colors
	 */
	private void displaySessionAvg(XYPlot plot) {
		IElementStats<ISession> eltStats = this.service
				.getSessionStats(this.session);
		Map<IPlayer, IStatsEntry<T>> entries = eltStats.getStatsEntries(this
				.getStatKey());
		ValueMarker marker;
		Double value;
		for (Entry<IPlayer, IStatsEntry<T>> entry : entries.entrySet()) {
			value = this.getValue(entry.getValue());
			if (value != null) {
				marker = new ValueMarker(value);
				marker.setAlpha(0.25F);
				marker.setPaint(this.playerColors.get(entry.getKey()));
				plot.addRangeMarker(marker, Layer.BACKGROUND);
			}
		}
	}

	/**
	 * Display winning.
	 * 
	 * @param plot
	 *            the plot
	 */
	private void displayWinning(XYPlot plot) {
		Drawable drawable;
		XYDrawableAnnotation annotation;
		IPlayer winner;
		Double val;
		for (ISet set : this.session.getAllGame()) {
			for (IGame game : set.getAllGame()) {
				winner = game.getWinner();
				if (winner != null) {
					val = this.getGameValue(game, winner);
					if (val != null) {
						drawable = new CircleDrawer(this.win, new BasicStroke(
								2.0F), null);
						annotation = new XYDrawableAnnotation(
								this.gameIndex.get(game), val, 8, 8, drawable);
						plot.addAnnotation(annotation);
					}
				}
			}
		}
	}
}
