package org.opendarts.ui.export.model;

import java.util.ArrayList;
import java.util.List;

import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.session.func.SetIndexFunction;
import org.opendarts.core.model.session.func.SetPlayersFunction;
import org.opendarts.core.model.session.func.SetResultFunction;
import org.opendarts.core.stats.model.IElementStats;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.ui.stats.model.IChart;
import org.opendarts.ui.stats.service.IStatsUiService;

/**
 * The Class Set.
 */
public class Set extends AbstractBean<ISet> {

	/** The parent. */
	private final Session parent;

	/** The set. */
	private final ISet set;

	/** The stats. */
	private List<Stats<ISet>> stats = null;

	/** The stats services. */
	private final List<IStatsService> statsServices;

	/** The indexFunc. */
	private final SetIndexFunction indexFunc;

	/** The playersFunc. */
	private final SetPlayersFunction playersFunc;

	/** The resultFunc. */
	private final SetResultFunction resultFunc;

	/** The charts. */
	private List<IChart> charts = null;

	private List<Game> games;

	/**
	 * Instantiates a new sets the.
	 *
	 * @param parent the parent
	 * @param set the set
	 * @param statsServices the stats services
	 */
	public Set(Session parent, ISet set, List<IStatsService> statsServices) {
		super(set);
		this.parent = parent;
		this.set = set;
		this.statsServices = statsServices;
		this.indexFunc = new SetIndexFunction();
		this.playersFunc = new SetPlayersFunction();
		this.resultFunc = new SetResultFunction();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.x01.md.model.AbstractBean#getPlayerList()
	 */
	@Override
	public List<IPlayer> getPlayerList() {
		return this.set.getGameDefinition().getInitialPlayers();
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return this.set.getDescription();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.x01.md.model.AbstractBean#getStats()
	 */
	@Override
	public List<Stats<ISet>> getStats() {
		if (this.stats == null) {
			this.stats = new ArrayList<Stats<ISet>>();
			IElementStats<ISet> eltStats;
			for (IStatsService iss : this.statsServices) {
				eltStats = iss.getSetStats(this.set);
				// check if stats empty
				if (!eltStats.getStatsEntries().isEmpty()) {
					this.stats.add(new Stats<ISet>(this, iss, eltStats));
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
			IElementStats<ISet> eltStats;
			IStatsUiService statsUiService;
			for (IStatsService iss : this.statsServices) {
				eltStats = iss.getSetStats(this.set);
				statsUiService = getStatsUiProvider().getStatsUiService(iss);
				for (String key : eltStats.getStatsKeys()) {
					this.charts.addAll(statsUiService.getCharts(this.set, key));
				}
			}
		}
		return this.charts;
	}

	/**
	 * Gets the games.
	 *
	 * @return the games
	 */
	public List<Game> getGames() {
		if (this.games == null) {
			this.games = new ArrayList<Game>();
			for (IGame g : this.set.getAllGame()) {
				this.games.add(new Game(this, g, this.statsServices));
			}
		}
		return this.games;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.model.AbstractBean#getRootName()
	 */
	@Override
	public String getRootName() {
		return "set " + getIndex();
	}

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public Session getParent() {
		return this.parent;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.set.toString();
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public String getStart() {
		return this.getFormatters().getDatetimeFormat().format(set.getStart().getTime());
	}

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	public String getEnd() {
		if (set.getEnd() != null) {
			return this.getFormatters().getDatetimeFormat().format(set.getEnd().getTime());
		}
		return null;
	}

	/**
	 * Gets the winner.
	 *
	 * @return the winner
	 */
	public String getWinner() {
		return this.getPlayerToStringFunc().apply(this.set.getWinner());
	}

	/**
	 * Gets the index.
	 *
	 * @return the index
	 */
	public String getIndex() {
		return this.indexFunc.apply(this.set);
	}

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	public String getPlayers() {
		return this.playersFunc.apply(this.set);
	}

	/**
	 * Gets the result.
	 *
	 * @return the result
	 */
	public String getResult() {
		return this.resultFunc.apply(this.set);
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
