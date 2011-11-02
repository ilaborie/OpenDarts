package org.opendarts.ui.export.model;

import java.util.ArrayList;
import java.util.List;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.func.GameIndexFunction;
import org.opendarts.core.model.game.func.GamePlayersFunction;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.IElementStats;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.ui.service.IGameUiService;
import org.opendarts.ui.stats.model.IChart;
import org.opendarts.ui.stats.service.IStatsUiService;
import org.opendarts.ui.utils.comp.GameResultFunction;

/**
 * The Class Set.
 */
public class Game extends AbstractBean<IGame> {

	/** The parent. */
	private final Set parent;

	/** The set. */
	private final IGame game;

	/** The stats. */
	private List<Stats<IGame>> stats = null;

	/** The stats services. */
	private final List<IStatsService> statsServices;

	/** The charts. */
	private List<IChart> charts = null;

	/** The index func. */
	private GameIndexFunction indexFunc;

	/** The players func. */
	private GamePlayersFunction playersFunc;
	
	/** The result func. */
	private GameResultFunction resultFunc;

	/**
	 * Instantiates a new sets the.
	 *
	 * @param parent the parent
	 * @param set the set
	 * @param statsServices the stats services
	 */
	public Game(Set set, IGame game, List<IStatsService> statsServices) {
		super(game);
		this.parent = set;
		this.game = game;
		this.statsServices = statsServices;
		this.indexFunc = new GameIndexFunction();
		this.playersFunc = new GamePlayersFunction();
		
		IGameUiService gameUiService = this.getGameUiProvider()
				.getGameUiService(set.getElement().getGameDefinition());
		this.resultFunc = new GameResultFunction(gameUiService);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.x01.md.model.AbstractBean#getPlayerList()
	 */
	@Override
	public List<IPlayer> getPlayerList() {
		return this.game.getPlayers();
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return this.game.getDescription();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.x01.md.model.AbstractBean#getStats()
	 */
	@Override
	public List<Stats<IGame>> getStats() {
		if (this.stats == null) {
			this.stats = new ArrayList<Stats<IGame>>();
			IElementStats<IGame> eltStats;
			for (IStatsService iss : this.statsServices) {
				eltStats = iss.getGameStats(this.game);
				// check if stats empty
				if (!eltStats.getStatsEntries().isEmpty()) {
					this.stats.add(new Stats<IGame>(this, iss, eltStats));
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
			IElementStats<IGame> eltStats;
			IStatsUiService statsUiService;
			for (IStatsService iss : this.statsServices) {
				eltStats = iss.getGameStats(this.game);
				statsUiService = getStatsUiProvider().getStatsUiService(iss);
				for (String key : eltStats.getStatsKeys()) {
					this.charts
							.addAll(statsUiService.getCharts(this.game, key));
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
		return "game " + getIndex();
	}

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public Set getParent() {
		return this.parent;
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public String getStart() {
		return this.getFormatters().getDatetimeFormat().format(this.game.getStart().getTime());
	}

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	public String getEnd() {
		if (this.game.getEnd() != null) {
			return this.getFormatters().getDatetimeFormat().format(this.game.getEnd().getTime());
		}
		return null;
	}

	/**
	 * Gets the winner.
	 *
	 * @return the winner
	 */
	public String getWinner() {
		return this.getPlayerToStringFunc().apply(this.game.getWinner());
	}

	/**
	 * Gets the index.
	 *
	 * @return the index
	 */
	public String getIndex() {
		return this.indexFunc.apply(this.game);
	}

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	public String getPlayers() {
		return this.playersFunc.apply(this.game);
	}

	/**
	 * Gets the result.
	 *
	 * @return the result
	 */
	public String getResult() {
		return this.resultFunc.apply(this.game);
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
