package org.opendarts.ui.x01.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.model.impl.AvgEntry;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.core.x01.OpenDartsX01Bundle;
import org.opendarts.core.x01.service.StatsX01Service;
import org.opendarts.ui.stats.model.IChart;
import org.opendarts.ui.stats.service.IStatsUiService;
import org.opendarts.ui.x01.chart.game.GameHistoryChartX01;
import org.opendarts.ui.x01.chart.game.GameThrowDistributionChartX01;
import org.opendarts.ui.x01.chart.game.GameThrowPieChartX01;
import org.opendarts.ui.x01.chart.session.AvgSessionProgressChartX01;
import org.opendarts.ui.x01.chart.session.SessionHistoryChartX01;
import org.opendarts.ui.x01.chart.session.SessionLegProgressChartX01;
import org.opendarts.ui.x01.chart.session.SessionThrowDistributionChartX01;
import org.opendarts.ui.x01.chart.session.SessionThrowPieChartX01;
import org.opendarts.ui.x01.chart.set.AvgSetProgressChartX01;
import org.opendarts.ui.x01.chart.set.SetHistoryChartX01;
import org.opendarts.ui.x01.chart.set.SetLegProgressChartX01;
import org.opendarts.ui.x01.chart.set.SetThrowDistributionChartX01;
import org.opendarts.ui.x01.chart.set.SetThrowPieChartX01;
import org.opendarts.ui.x01.label.ChartLabelProvider;
import org.opendarts.ui.x01.label.StatsX01LabelProvider;

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
	/**
	 * Gets the stats service.
	 *
	 * @return the stats service
	 */
	protected IStatsService getStatsService() {
		return OpenDartsX01Bundle.getStatsX01Service();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.stats.service.IStatsUiService#getCharts(org.opendarts.core.model.session.ISession, java.lang.String)
	 */
	@Override
	public List<IChart> getCharts(ISession session, String statKey) {
		List<IChart> result = new ArrayList<IChart>();
		List<String> keys = Arrays.asList(StatsX01Service.SESSION_AVG_DART,
				StatsX01Service.SESSION_AVG_3_DARTS);
		List<String> historyKeys = Arrays.asList(StatsX01Service.SESSION_AVG_DART_HISTORY,
				StatsX01Service.SESSION_AVG_3_DARTS_HISTORY);

		String statName = this.getStatsLabelProvider().getToolTipText(statKey);
		if (keys.contains(statKey)) {
			// Evol 
			result.add(new AvgSessionProgressChartX01(statName, statKey,
					session, this.getStatsService()));
		} else if (StatsX01Service.SESSION_AVG_LEG.equals(statKey)) {
			result.add(new SessionLegProgressChartX01(statName, statKey,
					session, getStatsService()));
		} else if (historyKeys.contains(statKey)) {
			result.add(new SessionHistoryChartX01(statName, statKey,
					session, this.getStatsService()));
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
		List<IChart> result = new ArrayList<IChart>();
		List<String> keys = Arrays.asList(StatsX01Service.SET_AVG_DART,
				StatsX01Service.SET_AVG_3_DARTS);
		List<String> historyKeys = Arrays.asList(StatsX01Service.SET_AVG_DART_HISTORY,
				StatsX01Service.SET_AVG_3_DARTS_HISTORY);

		String statName = this.getStatsLabelProvider().getToolTipText(statKey);
		if (keys.contains(statKey)) {
			// Evol 
			result.add(new AvgSetProgressChartX01(statName, statKey,
					set, this.getStatsService()));
		} else if (StatsX01Service.SET_AVG_LEG.equals(statKey)) {
			result.add(new SetLegProgressChartX01(statName, statKey,
					set, getStatsService()));
		} else if (historyKeys.contains(statKey)) {
			result.add(new SetHistoryChartX01(statName, statKey,
					set, this.getStatsService()));
		}

		if (StatsX01Service.SET_AVG_DART.equals(statKey)) {
			result.add(new SetThrowDistributionChartX01<AvgEntry>(
					"Throw distribution (Set)", statKey, set));
			result.add(new SetThrowPieChartX01<AvgEntry>(
					"Throw pie distribution (Set)", statKey, set));
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.stats.service.IStatsUiService#getCharts(org.opendarts.core.model.game.IGame, java.lang.String)
	 */
	@Override
	public List<IChart> getCharts(IGame game, String statKey) {

		List<String> historyKeys = Arrays.asList(StatsX01Service.GAME_AVG_DART_HISTORY,
				StatsX01Service.GAME_AVG_3_DARTS_HISTORY);
		
		List<IChart> result = new ArrayList<IChart>();
		String statName = this.getStatsLabelProvider().getToolTipText(statKey);
		if (StatsX01Service.GAME_AVG_DART.equals(statKey)) {
			result.add(new GameThrowDistributionChartX01<AvgEntry>(
					"Throw distribution (game)", statKey, game));
			result.add(new GameThrowPieChartX01<AvgEntry>(
					"Throw pie distribution (game)", statKey, game));
		} else if (historyKeys.contains(statKey)) {
			result.add(new GameHistoryChartX01(statName, statKey,
					game,  this.getStatsService()));
		}
		return result;
		}

}
