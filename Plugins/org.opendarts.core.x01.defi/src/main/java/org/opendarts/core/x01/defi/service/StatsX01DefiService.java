package org.opendarts.core.x01.defi.service;

import java.util.List;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.model.session.impl.GameSet;
import org.opendarts.core.stats.model.IStats;
import org.opendarts.core.x01.defi.service.entry.AverageTimeStatsEntry;
import org.opendarts.core.x01.model.GameX01;
import org.opendarts.core.x01.service.StatsX01Service;
import org.opendarts.core.x01.service.entry.AverageHistoryStatsEntry;

/**
 * The Class StatsX01DefiService.
 */
public class StatsX01DefiService extends StatsX01Service {
	
	/** The Constant GAME_AVG_TIME. */
	public static final String GAME_AVG_TIME = "Game.avg.time";
	
	/** The Constant GAME_AVG_TIME_HISTORY. */
	public static final String GAME_AVG_TIME_HISTORY = "Game.avg.time.hist";
	
	/**
	 * Instantiates a new stats x01 defi service.
	 */
	public StatsX01DefiService() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.opendarts.core.x01.service.StatsX01Service#getStatsKeys()
	 */
	@Override
	protected List<String> getStatsKeys() {
		List<String> result = super.getStatsKeys();
		result.add(GAME_AVG_TIME);
		result.add(GAME_AVG_TIME_HISTORY);
		return result;
	}
	
	
	/* (non-Javadoc)
	 * @see org.opendarts.core.x01.service.StatsX01Service#createGameStats(org.opendarts.core.x01.model.GameX01, org.opendarts.core.model.player.IPlayer)
	 */
	@Override
	protected IStats<IGame> createGameStats(GameX01 game, IPlayer player) {
		IStats<IGame> stats = super.createGameStats(game, player);
		// Average darts		
		// Time
		AverageTimeStatsEntry avgTime = new AverageTimeStatsEntry(GAME_AVG_TIME);
		stats.addEntry(avgTime);
		stats.addEntry(new AverageHistoryStatsEntry(GAME_AVG_TIME_HISTORY,avgTime));
		return stats;
	}
	
	/* (non-Javadoc)
	 * @see org.opendarts.core.x01.service.StatsX01Service#createSetStats(org.opendarts.core.model.session.impl.GameSet, org.opendarts.core.model.player.IPlayer)
	 */
	@Override
	protected IStats<ISet> createSetStats(GameSet set, IPlayer player) {
		return super.createSetStats(set, player);
	}
	
	/* (non-Javadoc)
	 * @see org.opendarts.core.x01.service.StatsX01Service#createSessionStats(org.opendarts.core.model.session.ISession, org.opendarts.core.model.player.IPlayer)
	 */
	@Override
	protected IStats<ISession> createSessionStats(ISession session,
			IPlayer player) {
		return super.createSessionStats(session, player);
	}
	
}
