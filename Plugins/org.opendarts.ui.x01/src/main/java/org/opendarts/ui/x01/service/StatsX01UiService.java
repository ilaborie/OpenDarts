package org.opendarts.ui.x01.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.model.impl.AvgEntry;
import org.opendarts.core.x01.OpenDartsX01Bundle;
import org.opendarts.core.x01.service.StatsX01Service;
import org.opendarts.ui.stats.model.IChart;
import org.opendarts.ui.stats.service.IStatsUiService;
import org.opendarts.ui.x01.label.ChartLabelProvider;
import org.opendarts.ui.x01.label.StatsX01LabelProvider;
import org.opendarts.ui.x01.model.AvgSessionProgressChartX01;
import org.opendarts.ui.x01.model.SessionLegProgressChartX01;
import org.opendarts.ui.x01.model.SessionThrowDistributionChartX01;
import org.opendarts.ui.x01.model.SessionThrowPieChartX01;

/**
 * The Class StatsX01UiService.
 */
public class StatsX01UiService implements IStatsUiService {

	/**
	 * Instantiates a new stats x01 ui service.
	 */
	public StatsX01UiService() {
		super();
	}

	/**
	 * Gets the stats label provider.
	 *
	 * @return the stats label provider
	 */
	@Override
	public ColumnLabelProvider getStatsLabelProvider() {
		return new StatsX01LabelProvider();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.stats.service.IStatsUiService#getChartLabelProvider()
	 */
	@Override
	public ColumnLabelProvider getChartLabelProvider() {
		return new ChartLabelProvider();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.stats.service.IStatsUiService#getCharts(org.opendarts.core.model.session.ISession, java.lang.String)
	 */
	@Override
	public List<IChart> getCharts(ISession session, String statKey) {
		List<IChart> result = new ArrayList<IChart>();
		List<String> keys = Arrays.asList(StatsX01Service.SESSION_AVG_DART,
				StatsX01Service.SESSION_AVG_3_DARTS);

		String statName = this.getStatsLabelProvider().getToolTipText(statKey);
		if (keys.contains(statKey)) {
			// Evol 
			result.add(new AvgSessionProgressChartX01(statName, statKey,
					session, OpenDartsX01Bundle.getStatsX01Service()));
		} else if (StatsX01Service.SESSION_AVG_LEG.equals(statKey)) {
			result.add(new SessionLegProgressChartX01(statName, statKey,
					session, OpenDartsX01Bundle.getStatsX01Service()));
		}

		if (StatsX01Service.SESSION_AVG_DART.equals(statKey)) {
			result.add(new SessionThrowDistributionChartX01<AvgEntry>(
					"Throw distribution (Session)", statKey, session));
			result.add(new SessionThrowPieChartX01<AvgEntry>(
					"Throw pie distribution (Session)", statKey, session));
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.stats.service.IStatsUiService#getCharts(org.opendarts.core.model.session.ISet, java.lang.String)
	 */
	@Override
	public List<IChart> getCharts(ISet set, String statKey) {
		// No stats
		return Collections.emptyList();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.stats.service.IStatsUiService#getCharts(org.opendarts.core.model.game.IGame, java.lang.String)
	 */
	@Override
	public List<IChart> getCharts(IGame game, String statKey) {
		// No stats
		return Collections.emptyList();
	}

}
