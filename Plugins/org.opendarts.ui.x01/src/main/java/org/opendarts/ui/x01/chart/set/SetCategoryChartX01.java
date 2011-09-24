package org.opendarts.ui.x01.chart.set;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISet;
import org.opendarts.ui.stats.model.IChart;
import org.opendarts.ui.stats.service.IStatsUiService;
import org.opendarts.ui.x01.X01UiPlugin;
import org.opendarts.ui.x01.chart.Category;

/**
 * The Class CategoryChartX01.
 *
 * @param <T> the generic type
 */
public abstract class SetCategoryChartX01<T> implements IChart {

	// TODO prefs
	/** The color even. */
	private final Color colorEven = new Color(0, 0, 127, 25);

	/** The color odd. */
	private final Color colorOdd = new Color(0, 127, 0, 25);

	/** The name. */
	private final String name;

	/** The session. */
	private final ISet set;

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
	public SetCategoryChartX01(String name, String statKey, ISet set) {
		super();
		this.name = name;
		this.statKey = statKey;
		this.set = set;
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
		players.addAll(set.getGameDefinition().getInitialPlayers());
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
