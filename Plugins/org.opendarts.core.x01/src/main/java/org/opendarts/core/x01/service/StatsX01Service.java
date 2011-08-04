package org.opendarts.core.x01.service;

import org.opendarts.core.model.dart.impl.ThreeDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.model.session.impl.GameSet;
import org.opendarts.core.stats.model.IStats;
import org.opendarts.core.stats.model.IStatsEntry;
import org.opendarts.core.stats.model.impl.AbstractStatsEntry;
import org.opendarts.core.stats.model.impl.GameStats;
import org.opendarts.core.stats.model.impl.SessionStats;
import org.opendarts.core.stats.model.impl.SetStats;
import org.opendarts.core.stats.service.impl.AbstractStatsService;
import org.opendarts.core.x01.model.GameX01;
import org.opendarts.core.x01.model.GameX01Entry;
import org.opendarts.core.x01.service.entry.Average3DartsStatsEntry;
import org.opendarts.core.x01.service.entry.AverageDartStatsEntry;
import org.opendarts.core.x01.service.entry.AverageLegStatsEntry;
import org.opendarts.core.x01.service.entry.BestLegStatsEntry;
import org.opendarts.core.x01.service.entry.BestOutStatsEntry;
import org.opendarts.core.x01.service.entry.CountDartsStatsEntry;
import org.opendarts.core.x01.service.entry.DartRangeStatsEntry;
import org.opendarts.core.x01.service.entry.DartScoreStatsEntry;
import org.opendarts.core.x01.service.entry.OutsOver100StatsEntry;
import org.opendarts.core.x01.service.entry.TotalDartStatsEntry;

/**
 * The Class StatsX01Service.
 */
public class StatsX01Service extends AbstractStatsService {

	public static final String SESSION_AVG_DART = "Session.avg.dart";
	public static final String SESSION_AVG_3_DARTS = "Session.avg.3darts";
	public static final String SESSION_180s = "Session.180";
	public static final String SESSION_140 = "Session.140";
	public static final String SESSION_TONS = "Session.100";
	public static final String SESSION_60 = "Session.60";
	public static final String SESSION_60_PLUS = "Session.60+";
	public static final String SESSION_TONS_PLUS = "Session.100+";
	public static final String SESSION_BEST_LEG = "Session.best.leg";
	public static final String SESSION_AVG_LEG = "Session.avg.leg";
	public static final String SESSION_BEST_OUT = "Session.best.out";
	public static final String SESSION_COUNT_DARTS = "Session.darts.count";
	public static final String SESSION_TOTAL_SCORE = "Session.total.score";
	public static final String SESSION_OUT_OVER_100 = "Session.out.100+";

	public static final String SET_AVG_DART = "Set.avg.dart";
	public static final String SET_AVG_3_DARTS = "Set.avg.3darts";
	public static final String SET_180s = "Set.180";
	public static final String SET_140 = "Set.140";
	public static final String SET_TONS = "Set.100";
	public static final String SET_60 = "Set.60";
	public static final String SET_60_PLUS = "Set.60+";
	public static final String SET_TONS_PLUS = "Set.100+";
	public static final String SET_BEST_LEG = "Set.best.leg";
	public static final String SET_AVG_LEG = "Set.avg.leg";
	public static final String SET_BEST_OUT = "Set.best.out";
	public static final String SET_COUNT_DARTS = "Set.darts.count";
	public static final String SET_TOTAL_SCORE = "Set.total.score";
	public static final String SET_OUT_OVER_100 = "Set.out.100+";

	public static final String GAME_AVG_DART = "Game.avg.dart";
	public static final String GAME_AVG_3_DARTS = "Game.avg.3darts";
	public static final String GAME_180s = "Game.180";
	public static final String GAME_140 = "Game.140";
	public static final String GAME_TONS = "Game.100";
	public static final String GAME_60 = "Game.100";
	public static final String GAME_60_PLUS = "Game.60+";
	public static final String GAME_TONS_PLUS = "Game.100+";

	/**
	 * Instantiates a new stats x01 service.
	 */
	public StatsX01Service() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.service.IStatsService#getName()
	 */
	@Override
	public String getName() {
		return "x01 statistics";
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.stats.IStatsService#updateStats( org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void updateStats(IPlayer player, IGame igame, IGameEntry ientry) {
		GameX01 game = (GameX01) igame;
		GameSet set = game.getParentSet();
		ISession session = set.getParentSession();

		GameX01Entry entry = (GameX01Entry) ientry;
		ThreeDartsThrow dartsThrow = entry.getPlayerThrow().get(player);

		AbstractStatsEntry se;
		// session
		IStats<ISession> sessionStats = this.getSessionStats(session)
				.getPlayerStats(player);
		if (sessionStats == null) {
			sessionStats = this.createSessionStats(session, player);
			this.addSessionStats(session, player, sessionStats);
		}
		for (IStatsEntry gameStatsEntry : sessionStats.getAllEntries().values()) {
			se = (AbstractStatsEntry) gameStatsEntry;
			if (se.handleDartsThrow(game, player, entry, dartsThrow)) {
				this.fireEntryUpdated(sessionStats, se);
			}
		}

		// set
		IStats<ISet> setStats = this.getSetStats(set).getPlayerStats(player);
		if (setStats == null) {
			setStats = this.createSetStats(set, player);
			this.addSetStats(set, player, setStats);
		}
		for (IStatsEntry gameStatsEntry : setStats.getAllEntries().values()) {
			se = (AbstractStatsEntry) gameStatsEntry;
			if (se.handleDartsThrow(game, player, entry, dartsThrow)) {
				this.fireEntryUpdated(setStats, se);
			}
		}

		// game
		IStats<IGame> gameStats = this.getGameStats(game)
				.getPlayerStats(player);
		if (gameStats == null) {
			gameStats = this.createGameStats(game, player);
			this.addGameStats(game, player, gameStats);
		}
		for (IStatsEntry gameStatsEntry : gameStats.getAllEntries().values()) {
			se = (AbstractStatsEntry) gameStatsEntry;
			if (se.handleDartsThrow(game, player, entry, dartsThrow)) {
				this.fireEntryUpdated(gameStats, se);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.stats.IStatsService#undoStats(org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.game.IGameEntry)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void undoStats(IPlayer player, IGame igame, IGameEntry ientry) {
		GameX01 game = (GameX01) igame;
		GameSet set = game.getParentSet();
		ISession session = set.getParentSession();

		GameX01Entry entry = (GameX01Entry) ientry;
		ThreeDartsThrow dartsThrow = entry.getPlayerThrow().get(player);

		AbstractStatsEntry se;
		// session
		IStats<ISession> sessionStats = this.getSessionStats(session)
				.getPlayerStats(player);
		if (sessionStats != null) {
			for (IStatsEntry gameStatsEntry : sessionStats.getAllEntries()
					.values()) {
				se = (AbstractStatsEntry) gameStatsEntry;
				if (se.undoDartsThrow(game, player, entry, dartsThrow)) {
					this.fireEntryUpdated(sessionStats, se);
				}
			}
		}
		// set
		IStats<ISet> setStats = this.getSetStats(set).getPlayerStats(player);
		if (setStats != null) {
			for (IStatsEntry gameStatsEntry : setStats.getAllEntries().values()) {
				se = (AbstractStatsEntry) gameStatsEntry;
				if (se.undoDartsThrow(game, player, entry, dartsThrow)) {
					this.fireEntryUpdated(setStats, se);
				}
			}
		}

		// game
		IStats<IGame> gameStats = this.getGameStats(game)
				.getPlayerStats(player);
		if (gameStats != null) {
			for (IStatsEntry gameStatsEntry : gameStats.getAllEntries()
					.values()) {
				se = (AbstractStatsEntry) gameStatsEntry;
				if (se.undoDartsThrow(game, player, entry, dartsThrow)) {
					this.fireEntryUpdated(gameStats, se);
				}
			}
		}
	}

	/**
	 * Creates the game stats.
	 *
	 * @param game the game
	 * @param player the player
	 * @return the game stats
	 */
	private IStats<IGame> createGameStats(GameX01 game, IPlayer player) {
		GameStats stats = new GameStats(game, player);
		// Average darts
		stats.addEntry(new Average3DartsStatsEntry(GAME_AVG_3_DARTS));
		stats.addEntry(new AverageDartStatsEntry(GAME_AVG_DART));

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
		return stats;
	}

	/**
	 * Creates the set stats.
	 *
	 * @param set the set
	 * @param player the player
	 * @return the game stats
	 */
	private IStats<ISet> createSetStats(GameSet set, IPlayer player) {
		SetStats stats = new SetStats(set, player);
		// Average darts
		stats.addEntry(new Average3DartsStatsEntry(SET_AVG_3_DARTS));
		stats.addEntry(new AverageDartStatsEntry(SET_AVG_DART));

		// 180
		stats.addEntry(new DartScoreStatsEntry(SET_180s, 180));

		// 140
		stats.addEntry(new DartScoreStatsEntry(SET_140, 140));

		// Tons
		stats.addEntry(new DartScoreStatsEntry(SET_TONS, 100));

		// 60
		stats.addEntry(new DartScoreStatsEntry(SET_60, 60));

		// Tons+
		stats.addEntry(new DartRangeStatsEntry(SET_TONS_PLUS, 101, 180));

		// 60+
		stats.addEntry(new DartRangeStatsEntry(SET_60_PLUS, 61, 99));

		// Best Leg
		stats.addEntry(new BestLegStatsEntry(SET_BEST_LEG));

		// Avg. Leg
		stats.addEntry(new AverageLegStatsEntry(SET_AVG_LEG));

		// Best out
		stats.addEntry(new BestOutStatsEntry(SET_BEST_OUT));

		// Set counts darts
		stats.addEntry(new CountDartsStatsEntry(SET_COUNT_DARTS));

		// Set total darts
		stats.addEntry(new TotalDartStatsEntry(SET_TOTAL_SCORE));

		// Set best outs
		stats.addEntry(new OutsOver100StatsEntry(SET_OUT_OVER_100));

		// TODO
		//		public static final String SET_GAME_WIN = "Set.Game.Win";
		//		public static final String SET_NB_GAME = "Set.nb.Game";

		return stats;
	}

	/**
	 * Creates the session stats.
	 *
	 * @param set the set
	 * @param player the player
	 * @return the game stats
	 */
	private IStats<ISession> createSessionStats(ISession session, IPlayer player) {
		SessionStats stats = new SessionStats(session, player);
		// Average darts
		stats.addEntry(new Average3DartsStatsEntry(SESSION_AVG_3_DARTS));
		stats.addEntry(new AverageDartStatsEntry(SESSION_AVG_DART));

		// 180
		stats.addEntry(new DartScoreStatsEntry(SESSION_180s, 180));

		// 140
		stats.addEntry(new DartScoreStatsEntry(SESSION_140, 140));

		// Tons
		stats.addEntry(new DartScoreStatsEntry(SESSION_TONS, 100));

		// 60
		stats.addEntry(new DartScoreStatsEntry(SESSION_60, 60));

		// Tons+
		stats.addEntry(new DartRangeStatsEntry(SESSION_TONS_PLUS, 101, 180));

		// 60+
		stats.addEntry(new DartRangeStatsEntry(SESSION_60_PLUS, 61, 99));

		// Best Leg
		stats.addEntry(new BestLegStatsEntry(SESSION_BEST_LEG));

		// Avg Leg
		stats.addEntry(new AverageLegStatsEntry(SESSION_AVG_LEG));

		// Best out
		stats.addEntry(new BestOutStatsEntry(SESSION_BEST_OUT));

		// Session counts darts
		stats.addEntry(new CountDartsStatsEntry(SESSION_COUNT_DARTS));

		// Session total darts
		stats.addEntry(new TotalDartStatsEntry(SESSION_TOTAL_SCORE));

		// Session best outs
		stats.addEntry(new OutsOver100StatsEntry(SESSION_OUT_OVER_100));

		// TODO
		//		public static final String SESSION_SET_WIN = "Session.Set.Win";
		//		public static final String SESSION_NB_SET = "Session.nb.Set";
		//		public static final String SESSION_NB_GAME = "Session.nb.Game";
		return stats;
	}
}
