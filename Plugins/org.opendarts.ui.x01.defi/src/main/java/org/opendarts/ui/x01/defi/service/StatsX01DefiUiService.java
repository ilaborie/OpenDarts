package org.opendarts.ui.x01.defi.service;

import java.util.List;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.core.x01.defi.OpenDartsX01DefiBundle;
import org.opendarts.core.x01.defi.model.GameX01Defi;
import org.opendarts.core.x01.defi.service.StatsX01DefiService;
import org.opendarts.ui.stats.model.IChart;
import org.opendarts.ui.x01.chart.game.GameHistoryChartX01;
import org.opendarts.ui.x01.defi.label.StatsX01DefiLabelProvider;
import org.opendarts.ui.x01.label.ChartLabelProvider;
import org.opendarts.ui.x01.service.StatsX01UiService;

/**
 * The Class StatsX01UiService.
 */
public class StatsX01DefiUiService extends StatsX01UiService {

	/**
	 * Instantiates a new stats x01 ui service.
	 */
	public StatsX01DefiUiService() {
		super();
	}
	
	@Override
	protected IStatsService getStatsService() {
		return OpenDartsX01DefiBundle.getStatsX01Service();
	}

	/**
	 * Gets the stats label provider.
	 *
	 * @return the stats label provider
	 */
	@Override
	public ColumnLabelProvider getStatsLabelProvider() {
		return new StatsX01DefiLabelProvider();
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
		return super.getCharts(session, statKey);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.stats.service.IStatsUiService#getCharts(org.opendarts.core.model.session.ISet, java.lang.String)
	 */
	@Override
	public List<IChart> getCharts(ISet set, String statKey) {
		return super.getCharts(set, statKey);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.stats.service.IStatsUiService#getCharts(org.opendarts.core.model.game.IGame, java.lang.String)
	 */
	@Override
	public List<IChart> getCharts(IGame game, String statKey) {
		List<IChart> result = super.getCharts(game, statKey);
		if (StatsX01DefiService.GAME_AVG_TIME_HISTORY.equals(statKey)) {
			String statName = this.getStatsLabelProvider().getToolTipText(
					statKey);
			result.add(new GameHistoryChartX01(statName, statKey, game,
					OpenDartsX01DefiBundle.getStatsService((GameX01Defi) game)));
		}

		return result;
	}

}
