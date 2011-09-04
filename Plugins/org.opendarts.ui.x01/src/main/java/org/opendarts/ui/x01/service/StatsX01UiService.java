package org.opendarts.ui.x01.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.model.IElementStats;
import org.opendarts.core.stats.model.IStatsEntry;
import org.opendarts.core.stats.model.impl.AvgEntry;
import org.opendarts.core.x01.OpenDartsX01Bundle;
import org.opendarts.core.x01.service.StatsX01Service;
import org.opendarts.ui.stats.model.IChart;
import org.opendarts.ui.stats.service.IStatsUiService;
import org.opendarts.ui.x01.label.ChartLabelProvider;
import org.opendarts.ui.x01.label.StatsX01LabelProvider;
import org.opendarts.ui.x01.model.AvgDistributionChartX01;
import org.opendarts.ui.x01.model.AvgSessionProgressChartX01;
import org.opendarts.ui.x01.model.AvgSetProgressChartX01;

// TODO: Auto-generated Javadoc
/**
 * The Class StatsX01UiService.
 */
public class StatsX01UiService implements IStatsUiService {

	/** The stats service. */
	private final StatsX01Service statsService;

	/**
	 * Instantiates a new stats x01 ui service.
	 */
	public StatsX01UiService() {
		super();
		this.statsService = OpenDartsX01Bundle.getStatsX01Service();
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
		IElementStats<ISession> sessionStats = this.statsService
				.getSessionStats(session);

		List<String> keys = Arrays.asList(StatsX01Service.SESSION_AVG_LEG,
				StatsX01Service.SESSION_AVG_DART,
				StatsX01Service.SESSION_AVG_3_DARTS);

		Map<IPlayer, IStatsEntry<AvgEntry>> entries = sessionStats
				.getStatsEntries(statKey);
		String statName = this.getStatsLabelProvider().getText(statKey);
		if (keys.contains(statKey)) {
			// Distribution
			String name = MessageFormat.format("{0} Distribution", statName);
			result.add(new AvgDistributionChartX01(name, statKey, session,
					entries));

			// Evol 
			result.add(new AvgSessionProgressChartX01(statName, statKey,
					session, OpenDartsX01Bundle.getStatsX01Service()));
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.stats.service.IStatsUiService#getCharts(org.opendarts.core.model.session.ISet, java.lang.String)
	 */
	@Override
	public List<IChart> getCharts(ISet set, String statKey) {
		List<IChart> result = new ArrayList<IChart>();
		IElementStats<ISet> sessionStats = this.statsService.getSetStats(set);

		List<String> keys = Arrays.asList(StatsX01Service.SET_AVG_LEG,
				StatsX01Service.SET_AVG_DART, StatsX01Service.SET_AVG_3_DARTS);

		Map<IPlayer, IStatsEntry<AvgEntry>> entries = sessionStats
				.getStatsEntries(statKey);
		String statName = this.getStatsLabelProvider().getText(statKey);
		if (keys.contains(statKey)) {
			// Distribution
			String name = MessageFormat.format("{0} Distribution", statName);
			result.add(new AvgDistributionChartX01(name, statKey, set, entries));

			// Evol
			result.add(new AvgSetProgressChartX01(statName, statKey, set,
					OpenDartsX01Bundle.getStatsX01Service()));
		}
		return result;
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
