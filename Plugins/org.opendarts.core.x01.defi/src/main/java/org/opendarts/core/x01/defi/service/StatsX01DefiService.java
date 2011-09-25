package org.opendarts.core.x01.defi.service;

import java.util.List;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.model.session.impl.GameSet;
import org.opendarts.core.stats.model.IStats;
import org.opendarts.core.x01.defi.service.entry.AverageTimeStatsEntry;
import org.opendarts.core.x01.defi.service.entry.PlaysThrowsStatsEntry;
import org.opendarts.core.x01.model.GameX01;
import org.opendarts.core.x01.service.StatsX01Service;
import org.opendarts.core.x01.service.entry.AverageHistoryStatsEntry;

/**
 * The Class StatsX01DefiService.
 */
public class StatsX01DefiService extends StatsX01Service {
	public static final String SESSION_AVG_TIME = "Session.avg.time";
	public static final String SESSION_AVG_TIME_HISTORY = "Session.avg.time.hist";
	
	public static final String SET_AVG_TIME = "Set.avg.time";
	public static final String SET_AVG_TIME_HISTORY = "Set.avg.time.hist";
	
	public static final String GAME_AVG_TIME = "Game.avg.time";
	public static final String GAME_AVG_TIME_HISTORY = "Game.avg.time.hist";
	
	public static final String GAME_COUNT_THROWS = "Game.throws.count";
	
	// TODO nb Throw
	
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

		// Remove Session non interesting stats
		result.remove(SESSION_BEST_LEG);
		result.remove(SESSION_AVG_LEG);
		result.remove(SESSION_BEST_OUT);
		result.remove(SESSION_OUT_OVER_100);
		result.remove(SESSION_SET_WIN);
		result.remove(SESSION_NB_SET);
		result.remove(SESSION_GAME_WIN);
		result.remove(SESSION_NB_GAME);

		// Remove Session non interesting stats
		result.remove(SET_BEST_LEG);
		result.remove(SET_AVG_LEG);
		result.remove(SET_BEST_OUT);
		result.remove(SET_OUT_OVER_100);
		result.remove(SET_GAME_WIN);
		result.remove(SET_NB_GAME);

		// Add Time stats
		result.add(SESSION_AVG_TIME);
		result.add(SESSION_AVG_TIME_HISTORY);

		result.add(SET_AVG_TIME);
		result.add(SET_AVG_TIME_HISTORY);

		result.add(GAME_AVG_TIME);
		result.add(GAME_AVG_TIME_HISTORY);
		result.add(GAME_COUNT_THROWS);
		return result;
	}
	
	
	/* (non-Javadoc)
	 * @see org.opendarts.core.x01.service.StatsX01Service#createGameStats(org.opendarts.core.x01.model.GameX01, org.opendarts.core.model.player.IPlayer)
	 */
	@Override
	protected IStats<IGame> createGameStats(GameX01 game, IPlayer player) {
		IStats<IGame> stats = super.createGameStats(game, player);
		// Time
		AverageTimeStatsEntry avgTime = new AverageTimeStatsEntry(GAME_AVG_TIME);
		stats.addEntry(avgTime);
		stats.addEntry(new AverageHistoryStatsEntry(GAME_AVG_TIME_HISTORY,avgTime));
		
		// Nb Throw
		stats.addEntry(new PlaysThrowsStatsEntry(GAME_COUNT_THROWS));
		
		return stats;
	}
	
	/* (non-Javadoc)
	 * @see org.opendarts.core.x01.service.StatsX01Service#createSetStats(org.opendarts.core.model.session.impl.GameSet, org.opendarts.core.model.player.IPlayer)
	 */
	@Override
	protected IStats<ISet> createSetStats(GameSet set, IPlayer player) {
		IStats<ISet> stats = super.createSetStats(set, player);

		// Time
		AverageTimeStatsEntry avgTime = new AverageTimeStatsEntry(SET_AVG_TIME);
		stats.addEntry(avgTime);
		stats.addEntry(new AverageHistoryStatsEntry(SET_AVG_TIME_HISTORY,avgTime));
		return stats;
	}
	
	/* (non-Javadoc)
	 * @see org.opendarts.core.x01.service.StatsX01Service#createSessionStats(org.opendarts.core.model.session.ISession, org.opendarts.core.model.player.IPlayer)
	 */
	@Override
	protected IStats<ISession> createSessionStats(ISession session,
			IPlayer player) {
		IStats<ISession> stats = super.createSessionStats(session, player);

		// Time
		AverageTimeStatsEntry avgTime = new AverageTimeStatsEntry(SESSION_AVG_TIME);
		stats.addEntry(avgTime);
		stats.addEntry(new AverageHistoryStatsEntry(SESSION_AVG_TIME_HISTORY,avgTime));
		return stats;
	}
	
}
