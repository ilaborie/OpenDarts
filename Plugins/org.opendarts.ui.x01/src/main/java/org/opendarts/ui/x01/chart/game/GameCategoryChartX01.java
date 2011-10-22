package org.opendarts.ui.x01.chart.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryMarker;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.Layer;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.ui.stats.OpenDartsStatsUiPlugin;
import org.opendarts.ui.stats.model.IChart;
import org.opendarts.ui.stats.pref.IStatsPrefs;
import org.opendarts.ui.stats.service.IStatsUiService;
import org.opendarts.ui.x01.X01UiPlugin;
import org.opendarts.ui.x01.chart.Category;
import org.opendarts.ui.x01.chart.PlayerColor;

/**
 * The Class CategoryChartX01.
 *
 * @param <T> the generic type
 */
public abstract class GameCategoryChartX01<T> implements IChart {

	/** The color even. */
	private final Color colorEven;

	/** The color odd. */
	private final Color colorOdd;

	/** The name. */
	private final String name;

	/** The game. */
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
	public GameCategoryChartX01(String name, String statKey, IGame game) {
		super();
		this.name = name;
		this.statKey = statKey;
		this.game = game;

		RGB rgb = PreferenceConverter.getColor(OpenDartsStatsUiPlugin.getOpenDartsStats(), IStatsPrefs.STATS_COLOR_EVEN);
		this.colorEven = new Color(rgb.red, rgb.green, rgb.blue,25);
		rgb = PreferenceConverter.getColor(OpenDartsStatsUiPlugin.getOpenDartsStats(), IStatsPrefs.STATS_COLOR_ODD);
		this.colorOdd = new Color(rgb.red, rgb.green, rgb.blue,25);
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
	 * @see org.opendarts.ui.x01.chart.ChartX01#createChart()
	 */
	/**
	 * Creates the chart.
	 *
	 * @return the j free chart
	 */
	protected JFreeChart createChart() {
		CategoryDataset dataset = this.createDataset();
		JFreeChart chart = this.buildChart(dataset);
		return chart;
	}

	/**
	 * Creates the histogram dataset.
	 *
	 * @return the histogram dataset
	 */
	private CategoryDataset createDataset() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		Double value;
		for (IPlayer player : this.getAllPlayers()) {

			for (Category c : this.getCategories()) {
				value = this.getValue(c, player);
				if (value != null) {
					dataset.addValue(value, this.getRow(player), c);
				}
			}

		}
		return dataset;
	}

	/**
	 * Gets the category.
	 *
	 * @param player the player
	 * @return the category
	 */
	public String getRow(IPlayer player) {
		return player.getName();
	}

	/**
	 * Gets the all players.
	 *
	 * @return the all players
	 */
	protected List<IPlayer> getAllPlayers() {
		Set<IPlayer> players = new HashSet<IPlayer>();
		players.addAll(this.game.getParentSet().getGameDefinition()
				.getInitialPlayers());
		return new ArrayList<IPlayer>(players);
	}

	/**
	 * Gets the categories.
	 *
	 * @return the categories
	 */
	protected abstract List<Category> getCategories();

	/**
	 * Gets the distribution.
	 *
	 * @param c the c
	 * @param player the player
	 * @return the distribution
	 */
	protected abstract Double getValue(Category c, IPlayer player);

	/**
	 * Builds the chart.
	 *
	 * @param dataset the dataset
	 * @return the j free chart
	 */
	private JFreeChart buildChart(CategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createBarChart(this.getName(),
				"Player", "Count", dataset, PlotOrientation.VERTICAL, true,
				true, false);

		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setRangePannable(true);
		plot.setForegroundAlpha(0.66F);
		plot.setBackgroundPaint(Color.white);

		NumberAxis axis = (NumberAxis) plot.getRangeAxis();
		axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
		renderer.setShadowVisible(false);
		
		int series;
		@SuppressWarnings("unchecked")
		List<String> rowKeys = dataset.getRowKeys();
		for (IPlayer player : this.getAllPlayers()) {
			series = rowKeys.indexOf(this.getRow(player));
			renderer.setSeriesPaint(series, PlayerColor.getColor(player));
		}

		Comparable<?> category;
		CategoryMarker marker;
		Color color;
		for (int i = 0; i < dataset.getColumnCount(); i++) {
			category = dataset.getColumnKey(i);
			if ((i % 2) == 0) {
				color = this.colorEven;
			} else {
				color = this.colorOdd;
			}
			marker = new CategoryMarker(category, color, new BasicStroke(1.0F));
			marker.setDrawAsLine(false);
			marker.setAlpha(0.50F);

			plot.addDomainMarker(marker, Layer.BACKGROUND);
		}

		return chart;
	}

}
