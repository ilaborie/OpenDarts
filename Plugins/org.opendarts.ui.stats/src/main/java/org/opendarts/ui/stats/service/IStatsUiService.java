package org.opendarts.ui.stats.service;

import java.util.List;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.ui.stats.model.IChart;

/**
 * The Interface IStatsUiService.
 */
public interface IStatsUiService {

	/**
	 * Gets the stats label provider.
	 *
	 * @return the stats label provider
	 */
	ColumnLabelProvider getStatsLabelProvider();

	/**
	 * Gets the chart label provider.
	 *
	 * @return the chart label provider
	 */
	ColumnLabelProvider getChartLabelProvider();

	/**
	 * Gets the charts.
	 *
	 * @param session the session
	 * @param statKey the stat key
	 * @return the charts
	 */
	List<IChart> getCharts(ISession session, String statKey);

	/**
	 * Gets the charts.
	 *
	 * @param set the set
	 * @param statKey the stat key
	 * @return the charts
	 */
	List<IChart> getCharts(ISet set, String statKey);

	/**
	 * Gets the charts.
	 *
	 * @param game the game
	 * @param statKey the stat key
	 * @return the charts
	 */
	List<IChart> getCharts(IGame game, String statKey);

}
