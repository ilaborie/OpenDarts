package org.opendarts.ui.x01.model;

import java.awt.Color;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.IStatsEntry;

public abstract class DisbributionChartX01<T> extends ChartX01<T> {

	/**
	 * Instantiates a new avg leg chart x01.
	 *
	 * @param name the name
	 * @param statKey the stat key
	 * @param entries the entries
	 */
	public DisbributionChartX01(String name, String statKey, Object elt,
			Map<IPlayer, IStatsEntry<T>> entries) {
		super(name, statKey, elt, entries);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.x01.model.ChartX01#createChart()
	 */
	@Override
	protected JFreeChart createChart() {
		HistogramDataset dataset = this.createHistogramDataset();
		JFreeChart chart = this.buildChart(dataset);
		return chart;
	}

	/**
	 * Creates the histogram dataset.
	 *
	 * @return the histogram dataset
	 */
	private HistogramDataset createHistogramDataset() {
		HistogramDataset result = new HistogramDataset();
		IStatsEntry<T> stEntry;
		IPlayer player;
		double[] distribution;
		for (Entry<IPlayer, IStatsEntry<T>> entry : this.getEntries()
				.entrySet()) {
			stEntry = entry.getValue();
			player = entry.getKey();
			distribution = this.getDistribution(stEntry);
			if (distribution != null && distribution.length > 0) {
				result.addSeries(player.getName(), distribution, 100);
			}
		}
		return result;
	}

	/**
	 * Gets the distribution.
	 *
	 * @param stEntry the st entry
	 * @return the distribution
	 */
	protected abstract double[] getDistribution(IStatsEntry<T> stEntry);

	/**
	 * Builds the chart.
	 *
	 * @param dataset the dataset
	 * @return the j free chart
	 */
	private JFreeChart buildChart(HistogramDataset dataset) {
		JFreeChart result = ChartFactory.createHistogram(this.getName(), null,
				null, dataset, PlotOrientation.VERTICAL, true, true, false);

		XYPlot plot = (XYPlot) result.getPlot();
		plot.setDomainPannable(true);
		plot.setRangePannable(true);
		plot.setForegroundAlpha(0.6F);
		plot.setBackgroundPaint(Color.white);

		NumberAxis axis = (NumberAxis) plot.getRangeAxis();
		axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
		renderer.setBarPainter(new StandardXYBarPainter());
		renderer.setShadowVisible(false);
		return result;
	}

}
