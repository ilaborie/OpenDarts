package org.opendarts.ui.x01.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryMarker;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.Layer;
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

/**
 * The Class ProgressChartX01.
 */
public abstract class SessionProgressChartX01<T> implements IChart {

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

	/**
	 * Instantiates a new avg leg chart x01.
	 *
	 * @param name the name
	 * @param statKey the stat key
	 * @param session the session
	 * @param service the service
	 */
	public SessionProgressChartX01(String name, String statKey,
			ISession session, IStatsService service) {
		super();
		this.name = name;
		this.statKey = statKey;
		this.session = session;
		this.service = service;
	}

	/**
	 * Creates the chart.
	 *
	 * @return the j free chart
	 */
	protected JFreeChart createChart() {
		CategoryDataset dataset = this.createCategoryDataset();
		JFreeChart chart = this.buildChart(dataset);
		return chart;
	}

	/**
	 * Creates the dataset.
	 *
	 * @return the dataset
	 */
	private CategoryDataset createCategoryDataset() {
		DefaultCategoryDataset result = new DefaultCategoryDataset();

		// Set
		for (ISet set : this.session.getAllGame()) {
			this.addSetStats(set, result);
		}

		return result;
	}

	/**
	 * Adds the set stats.
	 *
	 * @param set the set
	 * @param result the result
	 */
	private void addSetStats(ISet set, DefaultCategoryDataset result) {
		for (IGame game : set.getAllGame()) {
			this.addGameStats(set, game, result);
		}
	}

	/**
	 * Adds the game stats.
	 *
	 * @param set the set
	 * @param game the game
	 * @param result the result
	 */
	private void addGameStats(ISet set, IGame game,
			DefaultCategoryDataset result) {
		IElementStats<IGame> eltStats = service.getGameStats(game);
		String gameStatKey = this.statKey.replace("Session", "Game");
		Map<IPlayer, IStatsEntry<T>> entries = eltStats
				.getStatsEntries(gameStatKey);

		IStatsEntry<T> stEntry;
		IPlayer player;
		Double val;
		for (Entry<IPlayer, IStatsEntry<T>> entry : entries.entrySet()) {
			stEntry = entry.getValue();
			player = entry.getKey();

			val = this.getValue(stEntry);
			if (val != null) {
				result.addValue(val, player.getName(), this.getCategory(set));
			}
		}
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
	 * @see org.opendarts.ui.stats.model.IChart#getElement()
	 */
	@Override
	public Object getElement() {
		return this.session;
	}

	/**
	 * Gets the category.
	 *
	 * @param set the set
	 * @return the category
	 */
	protected String getCategory(ISet set) {
		int index = this.session.getAllGame().indexOf(set);
		return "Set #" + index;
	}

	/**
	 * Gets the value.
	 *
	 * @param stEntry the st entry
	 * @return the value
	 */
	protected abstract Double getValue(IStatsEntry<T> stEntry);

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

	/**
	 * Builds the chart.
	 *
	 * @param dataset the dataset
	 * @return the j free chart
	 */
	private JFreeChart buildChart(CategoryDataset dataset) {
		JFreeChart result = ChartFactory.createLineChart(this.getName(), null,
				this.getName(), dataset, PlotOrientation.VERTICAL, true, true,
				false);
		CategoryPlot plot = (CategoryPlot) result.getPlot();
		plot.setBackgroundPaint(Color.white);

		NumberAxis axis = (NumberAxis) plot.getRangeAxis();
		axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot
				.getRenderer();
		renderer.setSeriesShapesVisible(0, true);
		renderer.setDrawOutlines(true);
		renderer.setUseFillPaint(true);
		renderer.setBaseFillPaint(Color.white);
		renderer.setBaseItemLabelsVisible(true);

		// Set Marker
		Color colorEven = new Color(0, 0, 255, 25);
		Color colorOdd = new Color(0, 255, 0, 25);
		Color color;
		CategoryMarker marker;
		int i = 0;
		for (ISet set : this.session.getAllGame()) {
			if (i % 2 == 0) {
				color = colorOdd;
			} else {
				color = colorEven;
			}

			String category = this.getCategory(set);
			marker = new CategoryMarker(category, color, new BasicStroke(1.0F));

			marker.setDrawAsLine(false);
			marker.setAlpha(1.0F);

			// Label
			marker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);

			plot.addDomainMarker(marker, Layer.BACKGROUND);
			i++;
		}

		// Session marker
		IElementStats<ISession> eltStats = this.service
				.getSessionStats(this.session);
		Map<IPlayer, IStatsEntry<T>> entries = eltStats.getStatsEntries(this
				.getStatKey());
		ValueMarker sessionMarker;
		Double value;
		for (Entry<IPlayer, IStatsEntry<T>> entry : entries.entrySet()) {
			value = this.getValue(entry.getValue());
			if (value != null) {
				sessionMarker = new ValueMarker(value);
				plot.addRangeMarker(sessionMarker, Layer.BACKGROUND);
			}
		}

		return result;
	}
}
