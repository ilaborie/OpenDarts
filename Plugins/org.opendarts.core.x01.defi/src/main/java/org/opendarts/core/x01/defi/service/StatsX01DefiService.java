package org.opendarts.core.x01.defi.service;

import java.util.Arrays;
import java.util.List;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.model.session.impl.GameSet;
import org.opendarts.core.stats.model.IStats;
import org.opendarts.core.stats.model.impl.GameStats;
import org.opendarts.core.stats.model.impl.SessionStats;
import org.opendarts.core.stats.model.impl.SetStats;
import org.opendarts.core.x01.defi.service.entry.AverageHistoryStatsEntry;
import org.opendarts.core.x01.defi.service.entry.AverageTimeStatsEntry;
import org.opendarts.core.x01.model.GameX01;
import org.opendarts.core.x01.service.StatsX01Service;
import org.opendarts.core.x01.service.entry.Average3DartsStatsEntry;
import org.opendarts.core.x01.service.entry.AverageDartStatsEntry;
import org.opendarts.core.x01.service.entry.DartRangeStatsEntry;
import org.opendarts.core.x01.service.entry.DartScoreStatsEntry;

/**
 * The Class StatsX01DefiService.
 */
public class StatsX01DefiService extends StatsX01Service {

	/** The Constant GAME_AVG_DART_HISTORY. */
	public static final String GAME_AVG_DART_HISTORY = "Game.avg.dart.hist";
	
	/** The Constant GAME_AVG_3_DARTS_HISTORY. */
	public static final String GAME_AVG_3_DARTS_HISTORY = "Game.avg.3darts.hist";
	
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
		return Arrays.asList(
				GAME_AVG_DART, GAME_AVG_3_DARTS, GAME_180s, GAME_140,
				GAME_TONS, GAME_60, GAME_60_PLUS, GAME_TONS_PLUS,
				
				GAME_AVG_DART_HISTORY,GAME_AVG_3_DARTS_HISTORY,GAME_AVG_TIME,GAME_AVG_TIME_HISTORY
				);
	}
	
	
	/* (non-Javadoc)
	 * @see org.opendarts.core.x01.service.StatsX01Service#createGameStats(org.opendarts.core.x01.model.GameX01, org.opendarts.core.model.player.IPlayer)
	 */
	@Override
	protected IStats<IGame> createGameStats(GameX01 game, IPlayer player) {
		GameStats stats = new GameStats(game, player);
		// Average darts
		Average3DartsStatsEntry avg3Darts = new Average3DartsStatsEntry(GAME_AVG_3_DARTS);
		AverageDartStatsEntry avgDart = new AverageDartStatsEntry(GAME_AVG_DART);
		stats.addEntry(avg3Darts);
		stats.addEntry(avgDart);
		// Average History
		stats.addEntry(new AverageHistoryStatsEntry(GAME_AVG_DART_HISTORY,avgDart));
		stats.addEntry(new AverageHistoryStatsEntry(GAME_AVG_3_DARTS_HISTORY,avg3Darts));

		// 180
		stats.addEntry(new DartScoreStatsEntry(GAME_180s, 180));

		// 140
		stats.addEntry(new DartScoreStatsEntry(GAME_140, 140));

		// Tons
		stats.addEntry(new DartScoreStatsEntry(GAME_TONS, 100));

		// 60
		stats.addEntry(new DartScoreStatsEntry(GAME_60, 60));

		// Tons+
		stats.addEntry(new DartRangeStatsEntry(GAME_TONS_PLUS, 101, 180));

		// 60+
		stats.addEntry(new DartRangeStatsEntry(GAME_60_PLUS, 61, 99));
		
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
		return new SetStats(set, player);
	}
	
	/* (non-Javadoc)
	 * @see org.opendarts.core.x01.service.StatsX01Service#createSessionStats(org.opendarts.core.model.session.ISession, org.opendarts.core.model.player.IPlayer)
	 */
	@Override
	protected IStats<ISession> createSessionStats(ISession session,
			IPlayer player) {
		return new SessionStats(session, player);
	}
	
}
