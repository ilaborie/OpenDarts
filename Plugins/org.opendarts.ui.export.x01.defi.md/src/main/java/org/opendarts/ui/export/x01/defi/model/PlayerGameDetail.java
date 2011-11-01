package org.opendarts.ui.export.x01.defi.model;

import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.stats.model.IStatValue;
import org.opendarts.core.stats.model.IStats;
import org.opendarts.core.stats.model.IStatsEntry;
import org.opendarts.core.x01.defi.service.StatsX01DefiService;

/**
 * The Class PlayerGameDetail.
 */
public class PlayerGameDetail extends GameDetail {

	/** The player. */
	private final IPlayer player;

	/**
	 * Instantiates a new player game detail.
	 *
	 * @param game the game
	 * @param player the player
	 */
	public PlayerGameDetail(GameDefi game, IPlayer player, IStats<IGame> stats) {
		super(game, getDuration(game, player, stats), getScore(game, player,
				stats), getNbThrows(game, player, stats), getNbDarts(game,
				player, stats));
		this.player = player;
	}
	
	/**
	 * Gets the player.
	 *
	 * @return the player
	 */
	public IPlayer getPlayer() {
		return this.player;
	}

	/**
	 * Gets the nb darts.
	 *
	 * @param game the game
	 * @param player the player
	 * @return the nb darts
	 */
	private static int getNbDarts(GameDefi game, IPlayer player,
			IStats<IGame> stats) {
		int result;
		result = 0;
		IStatsEntry<Object> entry = stats
				.getEntry(StatsX01DefiService.GAME_COUNT_DARTS);
		if (entry != null) {
			IStatValue<Object> value = entry.getValue();
			if (value != null) {
				Object v = value.getValue();
				if (v instanceof Number) {
					result = ((Number) v).intValue();
				}
			}
		}
		return result;
	}

	/**
	 * Gets the score.
	 *
	 * @param game the game
	 * @param player the player
	 * @return the score
	 */
	private static int getScore(GameDefi game, IPlayer player,
			IStats<IGame> stats) {
		int result;
		result = 0;
		IStatsEntry<Object> entry = stats
				.getEntry(StatsX01DefiService.GAME_TOTAL_SCORE);
		if (entry != null) {
			IStatValue<Object> value = entry.getValue();
			if (value != null) {
				Object v = value.getValue();
				if (v instanceof Number) {
					result = ((Number) v).intValue();
				}
			}
		}
		return result;
	}

	/**
	 * Gets the nb throws.
	 *
	 * @param game the game
	 * @param player the player
	 * @return the nb throws
	 */
	private static double getNbThrows(GameDefi game, IPlayer player,
			IStats<IGame> stats) {
		double result = 0;
		IStatsEntry<Object> entry = stats
				.getEntry(StatsX01DefiService.GAME_COUNT_DARTS);
		if (entry != null) {
			IStatValue<Object> value = entry.getValue();
			if (value != null) {
				Object v = value.getValue();
				if (v instanceof Number) {
					result = ((Number) v).doubleValue() / 3;
				}
			}
		}
		return result;
	}

	/**
	 * Gets the duration.
	 *
	 * @param game the game
	 * @param player the player
	 * @return the duration
	 */
	private static double getDuration(GameDefi game, IPlayer player,
			IStats<IGame> stats) {
		double result;
		result = 0;
		IStatsEntry<Object> entry = stats
				.getEntry(StatsX01DefiService.GAME_TOTAL_TIME);
		if (entry != null) {
			IStatValue<Object> value = entry.getValue();
			if (value != null) {
				Object v = value.getValue();
				if (v instanceof Number) {
					result = ((Number) v).doubleValue() * 1000;
				}
			}
		}
		return result;
	}

}
