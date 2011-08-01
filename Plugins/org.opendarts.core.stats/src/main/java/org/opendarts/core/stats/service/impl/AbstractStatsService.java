package org.opendarts.core.stats.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.model.IStats;
import org.opendarts.core.stats.model.IStatsEntry;
import org.opendarts.core.stats.service.IStatsListener;
import org.opendarts.core.stats.service.IStatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class AbstractStatsService.
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractStatsService implements IStatsService {

	public static final String SESSION_SET_WIN = "Session.Set.Win";
	public static final String SESSION_NB_SET = "Session.nb.Set";
	public static final String SESSION_NB_GAME = "Session.nb.Game";

	public static final String SET_GAME_WIN = "Set.Game.Win";
	public static final String SET_NB_GAME = "Set.nb.Game";

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(AbstractStatsService.class);

	/** The listeners. */
	private final CopyOnWriteArraySet<IStatsListener> listeners;

	/** The session stats. */
	private final Map<ISession, Map<IPlayer, IStats<ISession>>> sessionStats;

	/** The set stats. */
	private final Map<ISet, Map<IPlayer, IStats<ISet>>> setStats;

	/** The game stats. */
	private final Map<IGame, Map<IPlayer, IStats<IGame>>> gameStats;

	/** The entries. */
	private final Map<IPlayer, Map<String, IStatsEntry>> entries;

	/**
	 * Instantiates a new abstract stats service.
	 */
	public AbstractStatsService() {
		super();
		this.listeners = new CopyOnWriteArraySet<IStatsListener>();
		this.sessionStats = new HashMap<ISession, Map<IPlayer, IStats<ISession>>>();
		this.setStats = new HashMap<ISet, Map<IPlayer, IStats<ISet>>>();
		this.gameStats = new HashMap<IGame, Map<IPlayer, IStats<IGame>>>();
		this.entries = new HashMap<IPlayer, Map<String, IStatsEntry>>();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.stats.IStatsService#getSessionStats(org.opendarts.prototype.model.session.ISession)
	 */
	@Override
	public Map<IPlayer, IStats<ISession>> getSessionStats(ISession session) {
		Map<IPlayer, IStats<ISession>> map = this.sessionStats.get(session);
		if (map == null) {
			map = new HashMap<IPlayer, IStats<ISession>>();
			this.sessionStats.put(session, map);
		}
		return Collections.unmodifiableMap(map);
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
		Map<IPlayer, IStats<ISession>> map = this.sessionStats.get(session);
		if (map == null) {
			map = new HashMap<IPlayer, IStats<ISession>>();
			this.sessionStats.put(session, map);
		}

		IStats<ISession> stats = map.get(player);
		if (stats == null) {
			map.put(player, sessionStats);
		}

		// Entries
		Map<String, IStatsEntry> playerEntries = this.entries.get(player);
		if (playerEntries == null) {
			playerEntries = new HashMap<String, IStatsEntry>();
			this.entries.put(player, playerEntries);
		}
		playerEntries.putAll(sessionStats.getAllEntries());
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.stats.IStatsService#getSetStats(org.opendarts.prototype.model.session.ISet)
	 */
	@Override
	public Map<IPlayer, IStats<ISet>> getSetStats(ISet set) {
		Map<IPlayer, IStats<ISet>> map = this.setStats.get(set);
		if (map == null) {
			map = new HashMap<IPlayer, IStats<ISet>>();
			this.setStats.put(set, map);
		}
		return Collections.unmodifiableMap(map);
	}

	/**
	 * Adds the set stats.
	 *
	 * @param set the set
	 * @param player the player
	 * @param setStats the set stats
	 */
	protected void addSetStats(ISet set, IPlayer player, IStats<ISet> setStats) {
		Map<IPlayer, IStats<ISet>> map = this.setStats.get(set);
		if (map == null) {
			map = new HashMap<IPlayer, IStats<ISet>>();
			this.setStats.put(set, map);
		}

		IStats<ISet> stats = map.get(player);
		if (stats == null) {
			map.put(player, setStats);
		}

		// Entries
		Map<String, IStatsEntry> playerEntries = this.entries.get(player);
		if (playerEntries == null) {
			playerEntries = new HashMap<String, IStatsEntry>();
			this.entries.put(player, playerEntries);
		}
		playerEntries.putAll(setStats.getAllEntries());
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.stats.IStatsService#getGameStats(org.opendarts.prototype.model.game.IGame)
	 */
	@Override
	public Map<IPlayer, IStats<IGame>> getGameStats(IGame game) {
		Map<IPlayer, IStats<IGame>> map = this.gameStats.get(game);
		if (map == null) {
			map = new HashMap<IPlayer, IStats<IGame>>();
			this.gameStats.put(game, map);
		}
		return Collections.unmodifiableMap(map);
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
		Map<IPlayer, IStats<IGame>> map = this.gameStats.get(game);
		if (map == null) {
			map = new HashMap<IPlayer, IStats<IGame>>();
			this.gameStats.put(game, map);
		}

		IStats<IGame> stats = map.get(player);
		if (stats == null) {
			map.put(player, gameStats);
		}
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
	public IStatsEntry getStatsEntry(ISession session, ISet set,
			IGame game, IPlayer player, String statsKey) {
		IStatsEntry result = null;
		// try in session
		Map<IPlayer, IStats<ISession>> sessionMap = this
				.getSessionStats(session);
		if (sessionMap != null) {
			IStats<ISession> sessionStats = sessionMap.get(player);
			if (sessionStats != null) {
				result = sessionStats.getEntry(statsKey);
			}
		}

		// try in set
		if (result == null) {
			Map<IPlayer, IStats<ISet>> setMap = this.setStats.get(set);
			if (setMap != null) {
				IStats<ISet> setStats = setMap.get(player);
				if (setStats != null) {
					result = setStats.getEntry(statsKey);
				}
			}
		}

		// try in game
		if (result == null) {
			Map<IPlayer, IStats<IGame>> gameMap = this.gameStats.get(game);
			if (gameMap != null) {
				IStats<IGame> gameStats = gameMap.get(player);
				if (gameStats != null) {
					result = gameStats.getEntry(statsKey);
				}
			}
		}

		return result;
	}}
