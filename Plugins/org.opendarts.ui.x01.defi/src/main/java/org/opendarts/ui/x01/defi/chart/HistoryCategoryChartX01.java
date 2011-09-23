package org.opendarts.ui.x01.defi.chart;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.x01.defi.service.entry.AvgHistory;
import org.opendarts.ui.stats.model.IChart;
import org.opendarts.ui.stats.service.IStatsUiService;
import org.opendarts.ui.x01.X01UiPlugin;

/**
 * The Class CategoryChartX01.
 *
 * @param <T> the generic type
 */
public abstract class HistoryCategoryChartX01 implements IChart {

	/** The name. */
	private final String name;

	/** The session. */
	private final IGame game;

	/** The stat key. */
	private final String statKey;

	/** The chart. */
	private JFreeChart chart;

	/**
	 * Instantiates a new avg leg chart x01.
	 *
	 * @param name the name
	 * @param statKey the stat key
	 * @param session the session
	 */
	public HistoryCategoryChartX01(String name, String statKey, IGame game) {
		super();
		this.name = name;
		this.statKey = statKey;
		this.game = game;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.stats.model.IChart#getElement()
	 */
	@Override
	public Object getElement() {
		return this.game;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.stats.model.IChart#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.stats.model.IChart#getStatKey()
	 */
	@Override
	public String getStatKey() {
		return this.statKey;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.stats.model.IChart#buildChart()
	 */
	@Override
	public JFreeChart getChart() {
		if (this.chart == null) {
			this.chart = this.createChart();
		}
		return this.chart;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.stats.model.IChart#getService()
	 */
	@Override
	public IStatsUiService getService() {
		return X01UiPlugin.getStatsUiService();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.x01.model.ChartX01#createChart()
	 */
	/**
	 * Creates the chart.
	 *
	 * @return the j free chart
	 */
	protected JFreeChart createChart() {
		TimeSeriesCollection dataset = this.createDataset();
		JFreeChart chart = this.buildChart(dataset);
		return chart;
	}

	/**
	 * Creates the histogram dataset.
	 *
	 * @return the histogram dataset
	 */
	private TimeSeriesCollection createDataset() {
		TimeSeriesCollection result = new TimeSeriesCollection();
		TimeSeries ts;
		AvgHistory history;
		for (IPlayer player : this.getAllPlayers()) {
			ts = new TimeSeries(player.getName());
			history = this.getHistory(player);
			for (Entry<Long, Double> entry : history.getValues().entrySet()) {
				ts.add(new FixedMillisecond(entry.getKey()), entry.getValue());
			}
			result.addSeries(ts);
		}
		return result;
	}

	/**
	 * Gets the history.
	 *
	 * @param player the player
	 * @return the history
	 */
	protected abstract AvgHistory getHistory(IPlayer player);

	/**
	 * Gets the all players.
	 *
	 * @return the all players
	 */
	protected List<IPlayer> getAllPlayers() {
		Set<IPlayer> players = new HashSet<IPlayer>();
		players.addAll(this.game.getParentSet().getGameDefinition().getInitialPlayers());
		return new ArrayList<IPlayer>(players);
	}

	/**
	 * Builds the chart.
	 *
	 * @param dataset the dataset
	 * @return the j free chart
	 */
	private JFreeChart buildChart(TimeSeriesCollection dataset) {
		JFreeChart chart = ChartFactory.createTimeSeriesChart(this.getName(),
				"Player", "Average", dataset, true, true, false);

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setRangePannable(true);
		plot.setForegroundAlpha(0.66F);
		plot.setBackgroundPaint(Color.white);

		//		NumberAxis axis = (NumberAxis) plot.getRangeAxis();
		//		axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		//		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		//		renderer.setDrawBarOutline(false);
		//		renderer.setShadowVisible(false);

		return chart;
	}

}
