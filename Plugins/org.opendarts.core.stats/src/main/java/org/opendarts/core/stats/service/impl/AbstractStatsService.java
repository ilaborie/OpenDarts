package org.opendarts.core.stats.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.model.IElementStats;
import org.opendarts.core.stats.model.IStats;
import org.opendarts.core.stats.model.IStatsEntry;
import org.opendarts.core.stats.model.impl.ElementStats;
import org.opendarts.core.stats.service.IStatsListener;
import org.opendarts.core.stats.service.IStatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class AbstractStatsService.
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractStatsService implements IStatsService {

	public static final String SESSION_SET_WIN = "Session.Set.win";
	public static final String SESSION_NB_SET = "Session.Set.played";
	public static final String SESSION_GAME_WIN = "Session.Game.win";
	public static final String SESSION_NB_GAME = "Session.Game.played";

	public static final String SET_GAME_WIN = "Set.Game.win";
	public static final String SET_NB_GAME = "Set.Game.played";

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(AbstractStatsService.class);

	/** The listeners. */
	private final CopyOnWriteArraySet<IStatsListener> listeners;

	/** The session stats. */
	private final Map<ISession, ElementStats<ISession>> sessionStats;

	/** The set stats. */
	private final Map<ISet, ElementStats<ISet>> setStats;

	/** The game stats. */
	private final Map<IGame, ElementStats<IGame>> gameStats;

	/** The entries. */
	private final Map<IPlayer, Map<String, IStatsEntry>> entries;

	/**
	 * Instantiates a new abstract stats service.
	 */
	public AbstractStatsService() {
		super();
		this.listeners = new CopyOnWriteArraySet<IStatsListener>();
		this.sessionStats = new HashMap<ISession, ElementStats<ISession>>();
		this.setStats = new HashMap<ISet, ElementStats<ISet>>();
		this.gameStats = new HashMap<IGame, ElementStats<IGame>>();
		this.entries = new HashMap<IPlayer, Map<String, IStatsEntry>>();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.service.IStatsService#getSessionStats(org.opendarts.core.model.session.ISession)
	 */
	@Override
	public IElementStats<ISession> getSessionStats(ISession session) {
		ElementStats<ISession> stats = this.sessionStats.get(session);
		if (stats == null) {
			stats = new ElementStats<ISession>(session);
			this.sessionStats.put(session, stats);
		}
		return stats;
	}

	/**
	 * Adds the session stats.
	 *
	 * @param session the session
	 * @param player the player
	 * @param sessionStats the session stats
	 */
	protected void addSessionStats(ISession session, IPlayer player,
			IStats<ISession> sessionStats) {
		ElementStats<ISession> sesStats = this.sessionStats.get(session);
		if (sesStats == null) {
			sesStats = new ElementStats<ISession>(session);
		}
		sesStats.addPlayerStats(player, sessionStats);

		// Entries
		Map<String, IStatsEntry> playerEntries = this.entries.get(player);
		if (playerEntries == null) {
			playerEntries = new HashMap<String, IStatsEntry>();
			this.entries.put(player, playerEntries);
		}
		playerEntries.putAll(sessionStats.getAllEntries());
	}

	/**
	 * Gets the sets the stats.
	 *
	 * @param set the set
	 * @return the sets the stats
	 */
	@Override
	public IElementStats<ISet> getSetStats(ISet set) {
		ElementStats<ISet> stats = this.setStats.get(set);
		if (stats == null) {
			stats = new ElementStats<ISet>(set);
			this.setStats.put(set, stats);
		}
		return stats;
	}

	/**
	 * Adds the set stats.
	 *
	 * @param set the set
	 * @param player the player
	 * @param setStats the set stats
	 */
	protected void addSetStats(ISet set, IPlayer player, IStats<ISet> setStats) {
		ElementStats<ISet> sStats = this.setStats.get(set);
		if (sStats == null) {
			sStats = new ElementStats<ISet>(set);
		}
		sStats.addPlayerStats(player, setStats);

		// Entries
		Map<String, IStatsEntry> playerEntries = this.entries.get(player);
		if (playerEntries == null) {
			playerEntries = new HashMap<String, IStatsEntry>();
			this.entries.put(player, playerEntries);
		}
		playerEntries.putAll(setStats.getAllEntries());
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.service.IStatsService#getGameStats(org.opendarts.core.model.game.IGame)
	 */
	@Override
	public IElementStats<IGame> getGameStats(IGame game) {
		ElementStats<IGame> stats = this.gameStats.get(game);
		if (stats == null) {
			stats = new ElementStats<IGame>(game);
			this.gameStats.put(game, stats);
		}
		return stats;
	}

	/**
	 * Adds the game stats.
	 *
	 * @param game the game
	 * @param player the player
	 * @param gameStats the game stats
	 */
	protected void addGameStats(IGame game, IPlayer player,
			IStats<IGame> gameStats) {
		ElementStats<IGame> gStats = this.gameStats.get(game);
		if (gStats == null) {
			gStats = new ElementStats<IGame>(game);
		}
		gStats.addPlayerStats(player, gameStats);

		// Entries
		Map<String, IStatsEntry> playerEntries = this.entries.get(player);
		if (playerEntries == null) {
			playerEntries = new HashMap<String, IStatsEntry>();
			this.entries.put(player, playerEntries);
		}
		playerEntries.putAll(gameStats.getAllEntries());
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.stats.IStatsService#addStatsListener(org.opendarts.prototype.service.stats.IStatsListener)
	 */
	@Override
	public <T> void addStatsListener(IStatsListener<T> listener) {
		this.listeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.stats.IStatsService#removeStatsListener(org.opendarts.prototype.service.stats.IStatsListener)
	 */
	@Override
	public <T> void removeStatsListener(IStatsListener<T> listener) {
		this.listeners.remove(listener);
	}

	/**
	 * Fire entry updated.
	 *
	 * @param stats the stats
	 * @param oldValue the old value
	 * @param entry the entry
	 */
	@SuppressWarnings({ "unchecked" })
	protected void fireEntryUpdated(IStats stats, IStatsEntry entry) {
		for (IStatsListener listener : this.listeners) {
			try {
				listener.updatedEntry(stats, entry);
			} catch (Throwable t) {
				LOG.error("Error in listener", t);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.stats.IStatsService#getSetStatEntry(org.opendarts.prototype.model.player.IPlayer, java.lang.String)
	 */
	@Override
	public IStatsEntry getSetStatEntry(IPlayer player, String statsKey) {
		IStatsEntry result = null;
		Map<String, IStatsEntry> map = this.entries.get(player);
		if (map != null) {
			result = map.get(statsKey);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.stats.IStatsService#getStatsEntry(org.opendarts.prototype.model.session.ISession, org.opendarts.prototype.internal.model.session.GameSet, org.opendarts.prototype.internal.model.game.x01.GameX01, org.opendarts.prototype.model.player.IPlayer, java.lang.String)
	 */
	@Override
	public IStatsEntry getStatsEntry(ISession session, ISet set, IGame game,
			IPlayer player, String statsKey) {
		IStatsEntry result = null;
		// try in session

		IElementStats<ISession> sessionSt = this.getSessionStats(session);
		if (sessionSt != null) {
			result = sessionSt.getStatsEntry(player, statsKey);
		}

		// try in set
		if (result == null) {
			IElementStats<ISet> setMap = this.setStats.get(set);
			if (setMap != null) {
				result = setMap.getStatsEntry(player, statsKey);
			}
		}

		// try in game
		if (result == null) {
			IElementStats<IGame> gameMap = this.gameStats.get(game);
			if (gameMap != null) {
				result = gameMap.getStatsEntry(player, statsKey);
			}
		}

		return result;
	}
}
