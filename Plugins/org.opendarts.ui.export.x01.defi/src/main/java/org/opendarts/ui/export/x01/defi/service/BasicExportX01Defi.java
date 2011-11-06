package org.opendarts.ui.export.x01.defi.service;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.player.func.PlayerToStringFunction;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.stats.model.IElementStats;
import org.opendarts.core.stats.model.IStatValue;
import org.opendarts.core.stats.model.IStats;
import org.opendarts.core.stats.model.IStatsEntry;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.core.x01.defi.OpenDartsX01DefiBundle;
import org.opendarts.core.x01.defi.model.GameX01Defi;
import org.opendarts.core.x01.defi.model.GameX01DefiDefinition;
import org.opendarts.core.x01.defi.service.StatsX01DefiService;
import org.opendarts.core.x01.model.GameX01Entry;
import org.opendarts.ui.export.model.Game;
import org.opendarts.ui.export.model.Session;
import org.opendarts.ui.export.model.Set;
import org.opendarts.ui.export.service.impl.BasicExportOption;
import org.opendarts.ui.export.service.impl.EscapeCsvFuntion;
import org.opendarts.ui.export.x01.defi.model.GameDefi;
import org.opendarts.ui.export.x01.defi.model.SessionDefi;
import org.opendarts.ui.export.x01.defi.model.SetDefi;
import org.opendarts.ui.export.x01.model.GameEntry;
import org.opendarts.ui.export.x01.service.BasicExportX01Service;
import org.opendarts.ui.export.x01.service.PlayerGameEntryScoreFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Closeables;
import com.google.common.io.Files;

/**
 * The Class BasicExportX01Defi.
 */
public class BasicExportX01Defi extends BasicExportX01Service {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(BasicExportX01Defi.class);
	
	
	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.x01.service.BasicExportX01Service#isApplicable(org.opendarts.core.model.game.IGame)
	 */
	@Override
	public boolean isApplicable(IGame game) {
		return (GameX01Defi.class.equals(game.getClass()));
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.x01.service.BasicExportX01Service#createSession(org.opendarts.core.model.session.ISession, java.util.List)
	 */
	@Override
	protected Session createSession(ISession ses, List<IStatsService> stats) {
		return new SessionDefi(ses, stats);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#writeExtraSetDetail(java.io.FileWriter, org.opendarts.core.model.session.ISet, org.opendarts.ui.export.service.impl.BasicExportOption)
	 */
	@Override
	protected void writeExtraSetInfo(Writer writer, Set set,
			BasicExportOption option) throws IOException {
		// Set index
		writer.write("Set: ");
		writer.write(set.getIndex());
		writer.write('\n');

		if (set instanceof SetDefi) {
			SetDefi setDefi = (SetDefi) set;
			// String Score
			writer.write("Starting Score: ");
			writer.write(setDefi.getStartingScore());
			writer.write('\n');

			// Number Game to Win
			writer.write("Number game to win: ");
			writer.write(setDefi.getNbGameToWin());
			writer.write('\n');
			writer.write('\n');

			// Number Game to Win
			writer.write("Target Time: ");
			writer.write(setDefi.getTargetTime());
			writer.write('\n');
		}
		writer.write('\n');
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#writeGameDetail(java.io.File, org.opendarts.core.model.game.IGame, org.opendarts.core.export.IExportOptions)
	 */
	@Override
	protected void writeGameDetail(File gameDetailFile, Game game,
			BasicExportOption option) {
		super.writeGameDetail(gameDetailFile, game, option);

		// Game entries
		this.exportGameEntries(
				(GameDefi) game,
				gameDetailFile.getParentFile(), option);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#writeGameDetail(java.io.Writer, org.opendarts.ui.export.model.Game, org.opendarts.ui.export.service.impl.BasicExportOption)
	 */
	@Override
	protected void writeGameDetail(Writer writer, Game game,
			BasicExportOption option) throws IOException {
		// Detail
		String detailTitle = "Detail";
		writer.write(detailTitle);
		writer.write('\n');
		writer.write(Strings.repeat("-", detailTitle.length()));
		writer.write('\n');
		writer.write('\n');

		Joiner joiner = Joiner.on('\t');

		List<IPlayer> players = game.getPlayerList();

		// Header
		List<String> headers = new ArrayList<String>();
		headers.add("   ");
		headers.addAll(Lists.transform(players, new PlayerToStringFunction()));
		joiner.appendTo(writer, headers);
		writer.write('\n');

		// Entries
		PlayerGameEntryScoreFunction entryScoreFunction;
		GameX01Entry entry;
		List<Object> lst;
		for (IGameEntry e : game.getElement().getGameEntries()) {
			entry = (GameX01Entry) e;
			entryScoreFunction = new PlayerGameEntryScoreFunction(entry);
			lst = new ArrayList<Object>();
			lst.add("#" + entry.getRound());
			lst.addAll(Lists.transform(players, entryScoreFunction));

			joiner.appendTo(writer, lst);
			writer.write('\n');
		}
	}

	/**
	 * Export game entries.
	 *
	 * @param game the game
	 * @param gameFile the game file
	 * @param option 
	 */
	protected void exportGameEntries(GameDefi game, File gameFile,
			BasicExportOption option) {
		File entriesFile = new File(gameFile, game.getFileName()
				+ "-throws.csv");
		Writer writer = null;

		List<IPlayer> players = game.getPlayerList();
		Joiner joiner = Joiner.on(option.getCsvSeparator()).useForNull("-");
		EscapeCsvFuntion escapeCsv = new EscapeCsvFuntion();

		try {
			writer = Files.newWriter(entriesFile, Charsets.UTF_8);
			new FileWriter(entriesFile);

			// Header
			List<String> headers = new ArrayList<String>();
			headers.add("");
			headers.addAll(Lists.transform(players,
					new PlayerToStringFunction()));
			headers = Lists.transform(headers, escapeCsv);
			joiner.appendTo(writer, headers);
			writer.write('\n');

			// Entries
			List<Object> lst;
			for (GameEntry e : game.getEntries()) {
				lst = new ArrayList<Object>();
				lst.add(e.getLabel());
				lst.addAll(e.getScores());

				joiner.appendTo(writer, lst);
				writer.write('\n');
			}
		} catch (IOException e) {
			LOG.error(e.toString(), e);
		} finally {
			Closeables.closeQuietly(writer);
		}
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#writeExtraGameInfo(java.io.FileWriter, org.opendarts.core.model.game.IGame, org.opendarts.ui.export.service.impl.BasicExportOption)
	 */
	@Override
	protected void writeExtraGameInfo(Writer writer, Game game,
			BasicExportOption option) throws IOException {
		// Set index
		writer.write("Game: ");
		writer.write(game.getIndex());
		writer.write('\n');

		if (game instanceof GameDefi) {
			GameDefi gameDefi = (GameDefi) game;

			// String Score
			writer.write("Starting Score: ");
			writer.write(gameDefi.getStartingScore());
			writer.write('\n');

			// Number Game to Win
			writer.write("Number game to win: ");
			writer.write(gameDefi.getNbGameToWin());
			writer.write('\n');

			// Number Game to Win
			writer.write("Target Time: ");
			writer.write(gameDefi.getTargetTime());
			writer.write('\n');
			writer.write('\n');

			GameX01Defi gameX01Defi = (GameX01Defi) game.getElement();
			IStatsService statsService = OpenDartsX01DefiBundle
					.getStatsService(gameX01Defi);
			// Defi stats
			String title = "Defi Stats";
			writer.write(title);
			writer.write('\n');
			writer.write(Strings.repeat("-", title.length()));
			writer.write('\n');
			writer.write(this.createDefiStats(gameX01Defi, null, statsService));
			writer.write('\n');

			// Player stats
			PlayerToStringFunction playerFunc = new PlayerToStringFunction();
			for (IPlayer player : gameX01Defi.getPlayers()) {
				title = playerFunc.apply(player);
				writer.write(title);
				writer.write('\n');
				writer.write(Strings.repeat("-", title.length()));
				writer.write('\n');
				writer.write(this.createDefiStats(gameX01Defi, player,
						statsService));
				writer.write('\n');

			}
		}
		writer.write('\n');
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
