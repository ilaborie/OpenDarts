package org.opendarts.ui.x01.chart.set;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.Layer;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.model.IElementStats;
import org.opendarts.core.stats.model.IStatValue;
import org.opendarts.core.stats.model.IStats;
import org.opendarts.core.stats.model.IStatsEntry;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.core.x01.service.entry.AvgHistory;
import org.opendarts.ui.stats.model.IChart;
import org.opendarts.ui.stats.service.IStatsUiService;
import org.opendarts.ui.x01.X01UiPlugin;
import org.opendarts.ui.x01.chart.PlayerColor;

/**
 * The Class CategoryChartX01.
 *
 * @param <T> the generic type
 */
public class SetHistoryChartX01 implements IChart {

	/** The name. */
	private final String name;

	/** The session. */
	private final ISet set;

	/** The stat key. */
	private final String statKey;

	/** The chart. */
	private JFreeChart chart;

	/** The service. */
	private final IStatsService service;

	/** The game stats. */
	private final  IElementStats<ISet> setStats;

	/** The player series. */
	private final Map<IPlayer, TimeSeries> playerSeries;

	/**
	 * Instantiates a new avg leg chart x01.
	 *
	 * @param name the name
	 * @param statKey the stat key
	 * @param session the session
	 */
	public SetHistoryChartX01(String name, String statKey, ISet set,IStatsService service) {
		super();
		this.name = name;
		this.statKey = statKey;
		this.set = set;
		this.service = service;

		this.setStats = this.service.getSetStats(set);
		this.playerSeries = new HashMap<IPlayer, TimeSeries>();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.stats.model.IChart#getElement()
	 */
	@Override
	public Object getElement() {
		return this.set;
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
	 * @see org.opendarts.ui.x01.chart.ChartX01#createChart()
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
			this.playerSeries.put(player, ts);
		}
		return result;
	}

	/**
	 * Gets the history.
	 *
	 * @param player the player
	 * @return the history
	 */
	protected AvgHistory getHistory(IPlayer player) {
		AvgHistory result = null;
		IStats<ISet> stats = this.setStats.getPlayerStats(player);
		IStatsEntry<AvgHistory> entry = stats.getEntry(getStatKey());
		IStatValue<AvgHistory> value = entry.getValue();
		if (value != null) {
			result = value.getValue();
		}
		return result;
	}

	/**
	 * Gets the all players.
	 *
	 * @return the all players
	 */
	protected List<IPlayer> getAllPlayers() {
		Set<IPlayer> players = new HashSet<IPlayer>();
		players.addAll(this.set.getGameDefinition().getInitialPlayers());
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
		
		XYItemRenderer renderer = plot.getRenderer();
		int serieIndex;
		for (Entry<IPlayer, TimeSeries> entry : this.playerSeries.entrySet()) {
			serieIndex = dataset.getSeries().indexOf(entry.getValue());
			if (serieIndex >= 0 && dataset.getSeries(serieIndex) != null) {
				renderer.setSeriesPaint(serieIndex,
						PlayerColor.getColor(entry.getKey()));
			}
		}

 		// Average
		this.displayAvg(plot);
		return chart;
	}

	/**
	 * Display avg.
	 *
	 * @param plot the plot
	 */
	private void displayAvg(XYPlot plot) {
		IElementStats<ISet> eltStats = this.service
				.getSetStats(this.set);
		Map<IPlayer, IStatsEntry<AvgHistory>> entries = eltStats
				.getStatsEntries(this.getStatKey());
		ValueMarker marker;
		IStatsEntry<AvgHistory> v;
		IStatValue<AvgHistory> value;
		AvgHistory hist;
		for (Entry<IPlayer, IStatsEntry<AvgHistory>> entry : entries.entrySet()) {
			v = entry.getValue();
			if (v != null) {
				 value = v.getValue();
				if (value != null) {
					hist = value.getValue();
					if (hist!=null) {
					marker = new ValueMarker(hist.getLastValue());
					marker.setAlpha(0.25F);
					marker.setPaint(PlayerColor.getColor(entry.getKey()));
					plot.addRangeMarker(marker, Layer.BACKGROUND);
				}}
			}
		}
	}
}
