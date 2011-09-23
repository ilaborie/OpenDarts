package org.opendarts.ui.x01.defi.chart;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.IElementStats;
import org.opendarts.core.stats.model.IStatValue;
import org.opendarts.core.stats.model.IStats;
import org.opendarts.core.stats.model.IStatsEntry;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.core.x01.defi.service.entry.AvgHistory;

/**
 * The Class CategoryChartX01.
 *
 */
public class AvgDartHistoryCategoryChartX01 extends HistoryCategoryChartX01 {

	private final IStatsService service;
	private IElementStats<IGame> gameStats;

	/**
	 * Instantiates a new avg dart history category chart x01.
	 *
	 * @param name the name
	 * @param statKey the stat key
	 * @param session the session
	 */
	public AvgDartHistoryCategoryChartX01(String name, String statKey,
			IGame game, IStatsService service) {
		super(name, statKey, game);
		this.service = service;

		this.gameStats = this.service.getGameStats(game);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.x01.defi.chart.HistoryCategoryChartX01#getHistory(org.opendarts.core.model.player.IPlayer)
	 */
	@Override
	protected AvgHistory getHistory(IPlayer player) {
		AvgHistory result = null;
		IStats<IGame> stats = this.gameStats.getPlayerStats(player);
		IStatsEntry<AvgHistory> entry = stats.getEntry(getStatKey());
		IStatValue<AvgHistory> value = entry.getValue();
		if (value != null) {
			result = value.getValue();
		}
		return result;
	}

}
