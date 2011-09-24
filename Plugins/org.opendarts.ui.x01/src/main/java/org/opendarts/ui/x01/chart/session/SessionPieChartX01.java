package org.opendarts.ui.x01.chart.session;

import java.awt.BasicStroke;
import java.awt.Color;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.MultiplePiePlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.util.TableOrder;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISession;
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
public abstract class SessionPieChartX01<T> implements IChart {

	/** The name. */
	private final String name;

	/** The session. */
	private final ISession session;

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
	public SessionPieChartX01(String name, String statKey, ISession session) {
		super();
		this.name = name;
		this.statKey = statKey;
		this.session = session;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.stats.model.IChart#getElement()
	 */
	@Override
	public Object getElement() {
		return this.session;
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
//		if (this.chart == null) {
			this.chart = this.createChart();
//		}
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
					dataset.addValue(value, c, this.getRow(player));
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
		for (ISet set : this.session.getAllGame()) {
			players.addAll(set.getGameDefinition().getInitialPlayers());
		}
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
		JFreeChart chart1 = ChartFactory.createMultiplePieChart(this.getName(),
				dataset, TableOrder.BY_COLUMN, true, true, false);

		MultiplePiePlot mplot = (MultiplePiePlot) chart1.getPlot();
		mplot.setBackgroundPaint(Color.white);
		mplot.setOutlineStroke(new BasicStroke(1.0F));

		JFreeChart chart2 = mplot.getPieChart();

		PiePlot plot = (PiePlot) chart2.getPlot();
		plot.setBackgroundPaint(null);
		plot.setOutlineStroke(null);

		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0} ({2})", NumberFormat.getNumberInstance(), NumberFormat
						.getPercentInstance()));

		return chart1;
	}

}
