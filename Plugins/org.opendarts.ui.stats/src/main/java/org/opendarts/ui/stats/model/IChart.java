package org.opendarts.ui.stats.model;

import org.jfree.chart.JFreeChart;
import org.opendarts.ui.stats.service.IStatsUiService;

/**
 * The Interface IChart.
 */
public interface IChart {
			
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();
	
	/**
	 * Gets the element (Session, Set, Game).
	 *
	 * @return the element
	 */
	Object getElement();

	/**
	 * Gets the service.
	 *
	 * @return the service
	 */
	IStatsUiService getService();
	
	/**
	 * Gets the stat key.
	 *
	 * @return the stat key
	 */
	String getStatKey();
	
	/**
	 * Builds the chart.
	 *
	 * @return the j free chart
	 */
	JFreeChart getChart();


}
