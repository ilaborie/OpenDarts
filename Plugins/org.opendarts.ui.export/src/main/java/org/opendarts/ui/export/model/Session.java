package org.opendarts.ui.export.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.player.func.PlayerToStringFunction;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.model.IElementStats;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.ui.stats.model.IChart;
import org.opendarts.ui.stats.service.IStatsUiService;

/**
 * The Class Session.
 */
public class Session extends AbstractBean<ISession> {

	/** The session. */
	private final ISession session;

	/** The player. */
	private final PlayerToStringFunction playerToStringFunc;

	/** The sets. */
	private List<Set> sets = null;

	/** The stats services. */
	private final List<IStatsService> statsServices;

	/** The stats. */
	private List<Stats<ISession>> stats = null;

	/** The charts. */
	private List<IChart> charts = null;

	/**
	 * Instantiates a new session.
	 *
	 * @param parent the parent
	 * @param statsServices the stats services
	 */
	public Session(ISession parent, List<IStatsService> statsServices) {
		super(parent);
		this.session = parent;
		this.playerToStringFunc = new PlayerToStringFunction();
		this.statsServices = new ArrayList<IStatsService>(statsServices);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.x01.md.model.AbstractBean#getPlayerList()
	 */
	@Override
	public List<IPlayer> getPlayerList() {
		java.util.Set<IPlayer> players = new HashSet<IPlayer>();
		for (Set s : this.getSets()) {
			players.addAll(s.getPlayerList());
		}
		return new ArrayList<IPlayer>(players);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.x01.md.model.AbstractBean#getStats()
	 */
	@Override
	public List<Stats<ISession>> getStats() {
		if (this.stats == null) {
			this.stats = new ArrayList<Stats<ISession>>();
			IElementStats<ISession> eltStats;
			for (IStatsService iss : this.statsServices) {
				eltStats = iss.getSessionStats(this.session);
				// check if stats empty
				if (!eltStats.getStatsEntries().isEmpty()) {
					this.stats.add(new Stats<ISession>(this, iss, eltStats));
				}
			}
		}
		return this.stats;
	}

	/**
	 * Gets the charts.
	 *
	 * @return the charts
	 */
	public List<IChart> getCharts() {
		if (this.charts == null) {
			this.charts = new ArrayList<IChart>();
			IElementStats<ISession> eltStats;
			IStatsUiService statsUiService;
			for (IStatsService iss : this.statsServices) {
				eltStats = iss.getSessionStats(this.session);
				statsUiService = getStatsUiProvider().getStatsUiService(iss);
				for (String key : eltStats.getStatsKeys()) {
					this.charts.addAll(statsUiService.getCharts(this.session,
							key));
				}
			}
		}
		return this.charts;
	}
	
	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.model.AbstractBean#getRootName()
	 */
	@Override
	public String getRootName() {
		return "session";
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public String getStart() {
		return this.getDateFormat().format(session.getStart().getTime());
	}

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	public String getEnd() {
		if (session.getEnd() != null) {
			return this.getDateFormat().format(session.getEnd().getTime());
		}
		return null;
	}

	/**
	 * Gets the winner.
	 *
	 * @return the winner
	 */
	public String getWinner() {
		return this.playerToStringFunc.apply(this.session.getWinner());
	}

	/**
	 * Gets the sets.
	 *
	 * @return the sets
	 */
	public List<Set> getSets() {
		if (sets == null) {
			this.sets = new ArrayList<Set>();
			for (ISet set : this.session.getAllGame()) {
				this.sets.add(new Set(this, set, this.statsServices));
			}
		}
		return this.sets;
	}
	
	/**
	 * Gets the stats services.
	 *
	 * @return the stats services
	 */
	protected List<IStatsService> getStatsServices() {
		return this.statsServices;
	}

}
