package org.opendarts.core.stats.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.service.IStatsProvider;
import org.opendarts.core.stats.service.IStatsService;

public class StatsProvider implements IStatsProvider {

	/** The session stats. */
	private final Map<ISession, List<IStatsService>> sessionStats;

	/** The set stats. */
	private final Map<ISet, List<IStatsService>> setStats;

	/** The game stats. */
	private final Map<IGame, List<IStatsService>> gameStats;

	/**
	 * Instantiates a new stats provider.
	 */
	public StatsProvider() {
		super();
		this.sessionStats = new HashMap<ISession, List<IStatsService>>();
		this.setStats = new HashMap<ISet, List<IStatsService>>();
		this.gameStats = new HashMap<IGame, List<IStatsService>>();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.service.IStatsProvider#getSessionStats(org.opendarts.core.model.session.ISession)
	 */
	@Override
	public List<IStatsService> getSessionStats(ISession session) {
		List<IStatsService> result = new ArrayList<IStatsService>();
		if (this.sessionStats.containsKey(session)) {
			result.addAll(this.sessionStats.get(session));
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.service.IStatsProvider#getSetStats(org.opendarts.core.model.session.ISet)
	 */
	@Override
	public List<IStatsService> getSetStats(ISet set) {
		List<IStatsService> result = new ArrayList<IStatsService>();
		if (this.setStats.containsKey(set)) {
			result.addAll(this.setStats.get(set));
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.service.IStatsProvider#getGameStats(org.opendarts.core.model.game.IGame)
	 */
	@Override
	public List<IStatsService> getGameStats(IGame game) {
		List<IStatsService> result = new ArrayList<IStatsService>();
		if (this.gameStats.containsKey(game)) {
			result.addAll(this.gameStats.get(game));
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.service.IStatsProvider#registerStatsService(org.opendarts.core.model.session.ISession, org.opendarts.core.stats.service.IStatsService)
	 */
	@Override
	public void registerStatsService(ISession session,
			IStatsService statsService) {
		List<IStatsService> list = this.sessionStats.get(session);
		if (list == null) {
			list = new ArrayList<IStatsService>();
			this.sessionStats.put(session, list);
		}
		if (!list.contains(statsService)) {
			list.add(statsService);
		}
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.service.IStatsProvider#registerStatsService(org.opendarts.core.model.session.ISet, org.opendarts.core.stats.service.IStatsService)
	 */
	@Override
	public void registerStatsService(ISet set, IStatsService statsService) {
		List<IStatsService> list = this.setStats.get(set);
		if (list == null) {
			list = new ArrayList<IStatsService>();
			this.setStats.put(set, list);
		}
		if (!list.contains(statsService)) {
			list.add(statsService);
		}
		// chain to session
		this.registerStatsService(set.getParentSession(), statsService);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.service.IStatsProvider#registerStatsService(org.opendarts.core.model.game.IGame, org.opendarts.core.stats.service.IStatsService)
	 */
	@Override
	public void registerStatsService(IGame game, IStatsService statsService) {
		List<IStatsService> list = this.gameStats.get(game);
		if (list == null) {
			list = new ArrayList<IStatsService>();
			this.gameStats.put(game, list);
		}
		if (!list.contains(statsService)) {
			list.add(statsService);
		}
		// chain to session
		this.registerStatsService(game.getParentSet(), statsService);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.service.IStatsProvider#getAllStatsService()
	 */
	@Override
	public Set<IStatsService> getAllStatsService() {
		Set<IStatsService> result = new HashSet<IStatsService>();
		for (List<IStatsService> lst : this.sessionStats.values()) {
			result.addAll(lst);
		}
		for (List<IStatsService> lst : this.setStats.values()) {
			result.addAll(lst);
		}
		for (List<IStatsService> lst : this.gameStats.values()) {
			result.addAll(lst);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.service.IStatsProvider#getStatsService(java.lang.String)
	 */
	@Override
	public IStatsService getStatsService(String statsKey) {
		for (IStatsService statsService : this.getAllStatsService()) {
			if (statsService.contains(statsKey)) {
				return statsService;
			}
		}
		return null;
	}
}
