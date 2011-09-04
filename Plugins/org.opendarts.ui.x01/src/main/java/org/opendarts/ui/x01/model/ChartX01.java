/*
 * 
 */
package org.opendarts.ui.x01.model;

import java.util.Map;

import org.jfree.chart.JFreeChart;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.IStatsEntry;
import org.opendarts.ui.stats.model.IChart;
import org.opendarts.ui.stats.service.IStatsUiService;
import org.opendarts.ui.x01.X01UiPlugin;

/**
 * The Class ChartX01.
 *
 * @param <T> the generic type
 */
public abstract class ChartX01<T> implements IChart {

	/** The name. */
	private final String name;

	/** The stat key. */
	private final String statKey;

	/** The entries. */
	private final Map<IPlayer, IStatsEntry<T>> entries;

	/** The chart. */
	private JFreeChart chart;

	/** The element. */
	private final Object element;

	/**
	 * Instantiates a new chart x01.
	 *
	 * @param name the name
	 * @param statKey the stat key
	 * @param elt the elt
	 * @param entries the entries
	 */
	public ChartX01(String name, String statKey, Object elt,
			Map<IPlayer, IStatsEntry<T>> entries) {
		super();
		this.name = name;
		this.statKey = statKey;
		this.entries = entries;
		this.element = elt;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.stats.model.IChart#getElement()
	 */
	@Override
	public Object getElement() {
		return this.element;
	}

	/**
	 * Gets the service.
	 *
	 * @return the service
	 */
	@Override
	public IStatsUiService getService() {
		return X01UiPlugin.getStatsUiService();
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

	/**
	 * Creates the chart.
	 *
	 * @return the j free chart
	 */
	protected abstract JFreeChart createChart();

	/**
	 * Gets the entries.
	 *
	 * @return the entries
	 */
	protected Map<IPlayer, IStatsEntry<T>> getEntries() {
		return this.entries;
	}

}
