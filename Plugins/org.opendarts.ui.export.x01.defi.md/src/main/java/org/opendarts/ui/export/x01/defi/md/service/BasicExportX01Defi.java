package org.opendarts.ui.export.x01.defi.md.service;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.func.GameIndexFunction;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.player.func.PlayerToStringFunction;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.model.session.func.SetIndexFunction;
import org.opendarts.core.stats.model.IElementStats;
import org.opendarts.core.stats.model.IStatValue;
import org.opendarts.core.stats.model.IStats;
import org.opendarts.core.stats.model.IStatsEntry;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.core.x01.defi.OpenDartsX01DefiBundle;
import org.opendarts.core.x01.defi.model.GameX01Defi;
import org.opendarts.core.x01.defi.model.GameX01DefiDefinition;
import org.opendarts.core.x01.defi.service.StatsX01DefiService;
import org.opendarts.ui.export.service.impl.BasicExportOption;
import org.opendarts.ui.export.x01.md.service.BasicExportX01Service;

import com.google.common.base.Strings;

/**
 * The Class BasicExportX01Defi.
 */
public class BasicExportX01Defi extends BasicExportX01Service {

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.x01.md.service.BasicExportX01Service#isApplicable(org.opendarts.core.model.game.IGame)
	 */
	@Override
	public boolean isApplicable(IGame game) {
		return (GameX01Defi.class.equals(game.getClass()));
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#writeExtraSetDetail(java.io.FileWriter, org.opendarts.core.model.session.ISet, org.opendarts.ui.export.service.impl.BasicExportOption)
	 */
	@Override
	protected void writeExtraSetInfo(FileWriter fw, ISet set,
			BasicExportOption option) throws IOException {
		GameX01DefiDefinition gameDefinition = (GameX01DefiDefinition) set
				.getGameDefinition();

		// Set index
		fw.write("Set: ");
		fw.write(new SetIndexFunction().apply(set));
		fw.write('\n');

		// String Score
		fw.write("Starting Score: ");
		fw.write(getNumberFormat().format(gameDefinition.getStartScore()));
		fw.write('\n');

		// Number Game to Win
		fw.write("Number game to win: ");
		fw.write(getNumberFormat().format(gameDefinition.getNbGameToWin()));
		fw.write('\n');

		// Number Game to Win
		fw.write("Target Time: ");
		DateFormat timeFormatter = gameDefinition.getTimeFormatter();
		Date date = new Date(gameDefinition.getTimeTarget());
		fw.write(timeFormatter.format(date));
		fw.write('\n');
		fw.write('\n');
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#writeExtraGameInfo(java.io.FileWriter, org.opendarts.core.model.game.IGame, org.opendarts.ui.export.service.impl.BasicExportOption)
	 */
	@Override
	protected void writeExtraGameInfo(FileWriter fw, IGame game,
			BasicExportOption option) throws IOException {
		GameX01DefiDefinition gameDefinition = (GameX01DefiDefinition) game
				.getParentSet().getGameDefinition();

		// Set index
		fw.write("Game: ");
		fw.write(new GameIndexFunction().apply(game));
		fw.write('\n');

		// String Score
		fw.write("Starting Score: ");
		fw.write(getNumberFormat().format(gameDefinition.getStartScore()));
		fw.write('\n');

		// Number Game to Win
		fw.write("Number game to win: ");
		fw.write(getNumberFormat().format(gameDefinition.getNbGameToWin()));
		fw.write('\n');

		// Number Game to Win
		fw.write("Target Time: ");
		DateFormat timeFormatter = gameDefinition.getTimeFormatter();
		Date date = new Date(gameDefinition.getTimeTarget());
		fw.write(timeFormatter.format(date));
		fw.write('\n');
		fw.write('\n');

		GameX01Defi gameX01Defi = (GameX01Defi) game;
		IStatsService statsService = OpenDartsX01DefiBundle
				.getStatsService(gameX01Defi);
		// Defi stats
		String title = "Defi Stats";
		fw.write(title);
		fw.write('\n');
		fw.write(Strings.repeat("-", title.length()));
		fw.write('\n');
		fw.write(this.createDefiStats(gameX01Defi, null, statsService));
		fw.write('\n');

		// Player stats
		PlayerToStringFunction playerFunc = new PlayerToStringFunction();
		for (IPlayer player : gameX01Defi.getPlayers()) {
			title = playerFunc.apply(player);
			fw.write(title);
			fw.write('\n');
			fw.write(Strings.repeat("-", title.length()));
			fw.write('\n');
			fw.write(this.createDefiStats(gameX01Defi, player, statsService));
			fw.write('\n');
		}
		fw.write('\n');
	}

	/**
	 * Creates the defi stats.
	 *
	 * @param game the game
	 * @param player the player
	 * @param statsService the stats service
	 */
	private String createDefiStats(GameX01Defi game, IPlayer player,
			IStatsService statsService) {
		IStats<IGame> stats = null;
		if (player != null) {
			IElementStats<IGame> gameStats = statsService.getGameStats(game);
			stats = gameStats.getPlayerStats(player);
		}

		int scoreDone = this.getScore(game, stats);
		int nbDarts = this.getNbDarts(game, stats);
		double duration = this.getDuration(game, stats);
		double nbThrows = this.getNbThrow(game, stats);

		StringBuffer sb = new StringBuffer();

		// Total time
		sb.append("Total time:");
		sb.append(this.getTotalTime(game, duration));
		sb.append("\n");

		// point by Seconds
		sb.append("Point by Seconds:");
		sb.append(this.getPointsBySecond(scoreDone, duration));
		sb.append("\n");

		// seconds by throw
		sb.append("Seconds by Throw:");
		sb.append(this.getSecondsByThrow(nbThrows, duration));
		sb.append("\n");

		// Average
		sb.append("Average score: ");
		sb.append(this.getAverage(scoreDone, nbDarts));
		sb.append("\n");

		// nb throw
		sb.append("Nb Throws:");
		sb.append(this.getNbThrow(nbThrows));
		sb.append("\n");

		// nb Darts
		sb.append("Nb Darts:");
		sb.append(this.getNbDarts(nbDarts));
		sb.append("\n");
		return sb.toString();
	}

	/**
	 * Gets the nb throw.
	 *
	 * @param player the player
	 * @return the nb throw
	 */
	private double getNbThrow(GameX01Defi game, IStats<IGame> stats) {
		double result;
		if (stats == null) {
			result = (double) game.getNbDartToFinish()
					/ (double) (game.getPlayers().size() * 3);
		} else {
			result = 0;
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
		}
		return result;
	}

	/**
	 * Gets the duration.
	 *
	 * @param player the player
	 * @return the duration
	 */
	private double getDuration(GameX01Defi game, IStats<IGame> stats) {
		double result;
		if (stats == null) {
			result = Long.valueOf(game.getDuration()).doubleValue();
		} else {
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
		}
		return result;
	}

	/**
	 * Gets the score.
	 *
	 * @param player the player
	 * @return the score
	 */
	private int getScore(GameX01Defi game, IStats<IGame> stats) {
		int result;
		if (stats == null) {
			result = game.getScoreToDo();
		} else {
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
		}
		return result;
	}

	/**
	 * Gets the nb darts.
	 *
	 * @param player the player
	 * @return the nb darts
	 */
	private int getNbDarts(GameX01Defi game, IStats<IGame> stats) {
		int result;
		if (stats == null) {
			result = game.getNbDartToFinish();
		} else {
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
		}
		return result;
	}

	/**
	 * Gets the points by second.
	 *
	 * @return the points by second
	 */
	private String getPointsBySecond(int score, double duration) {
		double pointBySec = ((double) score * 1000) / (duration);
		return DecimalFormat.getNumberInstance().format(pointBySec);
	}

	/**
	 * Gets the seconds by throw.
	 *
	 * @return the seconds by throw
	 */
	private String getSecondsByThrow(double nbThrows, double duration) {
		double secByThrow = ((double) duration) / (1000 * nbThrows);
		return DecimalFormat.getNumberInstance().format(secByThrow);
	}

	/**
	 * Gets the nb darts.
	 *
	 * @return the nb darts
	 */
	private String getNbDarts(int nbDarts) {
		return DecimalFormat.getIntegerInstance().format(nbDarts);
	}

	/**
	 * Gets the average.
	 *
	 * @return the average
	 */
	private String getAverage(int score, int nbDarts) {
		double avg = ((double) score * 3) / nbDarts;
		return NumberFormat.getNumberInstance().format(avg);
	}

	/**
	 * Gets the nb throw.
	 *
	 * @return the nb throw
	 */
	private String getNbThrow(double nbThrows) {
		return DecimalFormat.getIntegerInstance().format(Math.ceil(nbThrows));
	}

	/**
	 * Gets the total time.
	 *
	 * @return the total time
	 */
	private String getTotalTime(GameX01Defi game, double duration) {
		Date date = new Date(Double.valueOf(duration).longValue());
		return ((GameX01DefiDefinition) game.getParentSet().getGameDefinition())
				.getTimeFormatter().format(date);
	}

}
