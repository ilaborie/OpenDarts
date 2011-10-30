/*
 * 
 */
package org.opendarts.ui.export.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.swt.graphics.Image;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.func.GameIndexFunction;
import org.opendarts.core.model.game.func.GamePlayersFunction;
import org.opendarts.core.model.game.func.GameWinnerFunction;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.player.func.PlayerToStringFunction;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.model.session.func.SetIndexFunction;
import org.opendarts.core.model.session.func.SetPlayersFunction;
import org.opendarts.core.model.session.func.SetResultFunction;
import org.opendarts.core.model.session.func.SetWinnerFunction;
import org.opendarts.core.stats.model.IElementStats;
import org.opendarts.core.stats.model.IElementStats.IEntry;
import org.opendarts.core.stats.model.func.PlayerEntryValueFunction;
import org.opendarts.core.stats.service.IStatsProvider;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.ui.export.service.IExportUiService;
import org.opendarts.ui.service.IGameUiProvider;
import org.opendarts.ui.service.IGameUiService;
import org.opendarts.ui.stats.model.IChart;
import org.opendarts.ui.stats.service.IStatsUiProvider;
import org.opendarts.ui.stats.service.IStatsUiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Closeables;

/**
 * The Class BasicExportX01Service.
 *
 * @param <O> the option type
 */
public abstract class AbstractExportX01Service<O extends BasicExportOption>
		implements IExportUiService<O> {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(AbstractExportX01Service.class);

	/** The stats ui provider. */
	private final AtomicReference<IStatsUiProvider> statsUiProvider;

	/** The stats ui provider. */
	private final AtomicReference<IStatsProvider> statsProvider;

	private final AtomicReference<IGameUiProvider> gameUiProvider;

	/** The date format. */
	private final DateFormat dateFormat;

	/** The number format. */
	private final NumberFormat numberFormat;

	/** The decimal format. */
	private final NumberFormat decimalFormat;

	/**
	 * Instantiates a new basic export x01 service.
	 */
	public AbstractExportX01Service() {
		super();
		// OSGI service dependencies
		this.statsUiProvider = new AtomicReference<IStatsUiProvider>();
		this.statsProvider = new AtomicReference<IStatsProvider>();
		this.gameUiProvider = new AtomicReference<IGameUiProvider>();
		// Formatter
		this.dateFormat = DateFormat.getDateTimeInstance();
		this.numberFormat = NumberFormat.getIntegerInstance();
		this.decimalFormat = NumberFormat.getNumberInstance();
	}

	/**
	 * Export.
	 *
	 * @param session the session
	 * @param file the file
	 * @param option the option
	 */
	@Override
	public void export(ISession session, File file, O option) {
		File sessionFile = new File(file, this.normalize(session.toString()));
		sessionFile.mkdirs();

		File sessionDetailFile = new File(sessionFile, this.normalize(session
				.toString() + this.getFileExtension()));

		// Write Detail
		this.writeSessionDetail(sessionDetailFile, session, option);

		// Export stats
		if (option.isExportStatsAsCsv()) {
			List<IStatsService> stats = this.statsProvider.get()
					.getSessionStats(session);
			this.exportStatsCsv(stats, session, sessionFile, option);
		}

		// Extract chart image
		if (option.isExportChart()) {
			this.exportCharts(this.getSessionChart(session), sessionFile,
					option);
		}

		// Write set detail
		for (ISet set : session.getAllGame()) {
			this.export(set, sessionFile, option);
		}
	}

	/**
	 * Export.
	 *
	 * @param set the session
	 * @param file the file
	 * @param option the option
	 */
	@Override
	public void export(ISet set, File file, O option) {
		File setFile = new File(file, this.normalize(set.getName()));
		setFile.mkdirs();

		File setDetailFile = new File(setFile, this.normalize(set.getName()
				+ this.getFileExtension()));
		this.writeSetDetail(setDetailFile, set, option);

		// Write set-stats.csv
		if (option.isExportStatsAsCsv()) {
			List<IStatsService> stats = this.statsProvider.get().getSetStats(
					set);
			this.exportStatsCsv(stats, set, setFile, option);
		}
		// Extract png chart
		if (option.isExportChart()) {
			this.exportCharts(this.getSetChart(set), setFile, option);
		}

		// Export game
		for (IGame game : set.getAllGame()) {
			this.export(game, setFile, option);
		}
	}

	/**
	 * Export.
	 *
	 * @param game the game
	 * @param file the file
	 * @param option the option
	 */
	@Override
	public void export(IGame game, File file, O option) {
		File gameFile = new File(file, this.normalize(game.getName()));
		gameFile.mkdirs();

		File gameDetailFile = new File(gameFile, this.normalize(game.getName()
				+ this.getFileExtension()));
		this.writeGameDetail(gameDetailFile, game, option);

		// Write set-stats.csv
		if (option.isExportStatsAsCsv()) {
			List<IStatsService> stats = this.statsProvider.get().getGameStats(
					game);
			this.exportStatsCsv(stats, game, gameFile, option);
		}

		// Export chart
		if (option.isExportChart()) {
			this.exportCharts(this.getGameChart(game), gameFile, option);
		}
	}

	/**
	 * Gets the file extension.
	 *
	 * @return the file extension
	 */
	protected String getFileExtension() {
		return ".txt";
	}

	/**
	 * Write session detail.
	 *
	 * @param sessionDetailFile the session detail file
	 * @param session the session
	 * @param option the option
	 */
	@SuppressWarnings("unchecked")
	protected void writeSessionDetail(File sessionDetailFile, ISession session,
			O option) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(sessionDetailFile);
			// Title
			String title = session.toString();
			fw.write(title);
			fw.write('\n');
			fw.write(Strings.repeat("=", title.length()));
			fw.write('\n');
			fw.write('\n');

			// Status
			String status = "Status";
			fw.write(status);
			fw.write('\n');
			fw.write(Strings.repeat("-", status.length()));
			fw.write('\n');

			// Started
			fw.write("Started at: ");
			fw.write(this.dateFormat.format(session.getStart().getTime()));
			fw.write('\n');

			// End
			if (session.getEnd() != null) {
				fw.write("Ended at: ");
				fw.write(this.dateFormat.format(session.getEnd().getTime()));
				fw.write('\n');
			}

			// Winner
			if (session.getWinner() != null) {
				fw.write("Winner: ");
				fw.write(session.getWinner().toString());
				fw.write('\n');
			}

			// Hooks
			this.writeExtraSessionInfo(fw, session, option);

			// Detail
			fw.write('\n');
			String detail = "Detail";
			fw.write(detail);
			fw.write('\n');
			fw.write(Strings.repeat("-", detail.length()));
			fw.write('\n');
			fw.write('\n');

			Joiner joiner = Joiner.on('\t');
			ListFunctionBuilder<ISet, String> builder = new ListFunctionBuilder<ISet, String>(
					new SetIndexFunction(), new SetPlayersFunction(),
					new SetWinnerFunction(), new SetResultFunction());
			for (ISet set : session.getAllGame()) {
				joiner.appendTo(fw, builder.apply(set));
				fw.write('\n');
			}

			// Hooks
			this.writeExtraSessionDetail(fw, session, option);

		} catch (IOException e) {
			LOG.error(e.toString(), e);
		} finally {
			Closeables.closeQuietly(fw);
		}
	}

	/**
	 * Write set detail.
	 *
	 * @param setDetailFile the set detail file
	 * @param set the set
	 * @param option the option
	 */
	@SuppressWarnings("unchecked")
	protected void writeSetDetail(File setDetailFile, ISet set, O option) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(setDetailFile);
			// Title
			String title = set.toString();
			fw.write(title);
			fw.write('\n');
			fw.write(Strings.repeat("=", title.length()));
			fw.write('\n');
			fw.write('\n');
			
			// Description
			String description = set.getDescription();
			if (!Strings.isNullOrEmpty(description)) {
				String descrTitle = "Description";
				fw.write(descrTitle);
				fw.write('\n');
				fw.write(Strings.repeat("-", descrTitle.length()));
				fw.write('\n');
				fw.write(description);
				fw.write('\n');
				fw.write('\n');
			}
			// Status
			String status = "Status";
			fw.write(status);
			fw.write('\n');
			fw.write(Strings.repeat("-", status.length()));
			fw.write('\n');
			fw.write('\n');

			// Started
			fw.write("Started at: ");
			fw.write(this.dateFormat.format(set.getStart().getTime()));
			fw.write('\n');

			// End
			if (set.getEnd() != null) {
				fw.write("Ended at: ");
				fw.write(this.dateFormat.format(set.getEnd().getTime()));
				fw.write('\n');
			}

			// Winner
			if (set.getWinner() != null) {
				fw.write("Winner: ");
				fw.write(set.getWinner().toString());
				fw.write('\n');
			}

			// Hook
			this.writeExtraSetInfo(fw, set, option);

			// Detail
			fw.write('\n');
			String detailTitle = "Detail";
			fw.write(detailTitle);
			fw.write('\n');
			fw.write(Strings.repeat("-", detailTitle.length()));
			fw.write('\n');
			fw.write('\n');

			IGameUiService gameUiService = this.getGameUiProvider()
					.getGameUiService(set.getGameDefinition());
			Joiner joiner = Joiner.on('\t');
			ListFunctionBuilder<IGame, String> builder = new ListFunctionBuilder<IGame, String>(
					new GameIndexFunction(), new GamePlayersFunction(),
					new GameWinnerFunction(), new GameResultFunction(
							gameUiService));
			for (IGame game : set.getAllGame()) {
				joiner.appendTo(fw, builder.apply(game));
				fw.write('\n');
			}

			// Hook
			this.writeExtraSetDetail(fw, set, option);
		} catch (IOException e) {
			LOG.error(e.toString(), e);
		} finally {
			Closeables.closeQuietly(fw);
		}
	}

	/**
	 * Write game detail.
	 *
	 * @param gameDetailFile the game detail file
	 * @param game the game
	 * @param option the option
	 */
	protected void writeGameDetail(File gameDetailFile, IGame game, O option) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(gameDetailFile);
			// Title
			String title = game.toString();
			fw.write(title);
			fw.write('\n');
			fw.write(Strings.repeat("=", title.length()));
			fw.write('\n');
			fw.write('\n');

			// Description
			String description = game.getDescription();
			if (!Strings.isNullOrEmpty(description)) {
				String descrTitle = "Description";
				fw.write(descrTitle);
				fw.write('\n');
				fw.write(Strings.repeat("-", descrTitle.length()));
				fw.write('\n');
				fw.write(description);
				fw.write('\n');
				fw.write('\n');
			}
			
			// Status
			String status = "Status";
			fw.write(status);
			fw.write('\n');
			fw.write(Strings.repeat("-", status.length()));
			fw.write('\n');
			fw.write('\n');

			// Started
			fw.write("Started at: ");
			fw.write(this.dateFormat.format(game.getStart().getTime()));
			fw.write('\n');

			// End
			if (game.getEnd() != null) {
				fw.write("Ended at: ");
				fw.write(this.dateFormat.format(game.getEnd().getTime()));
				fw.write('\n');
			}

			// Winner
			if (game.getWinner() != null) {
				fw.write("Winner: ");
				fw.write(game.getWinner().toString());
				fw.write('\n');
			}

			this.writeExtraGameInfo(fw, game, option);

			// Detail
			this.writeGameDetail(fw, game, option);
		} catch (IOException e) {
			LOG.error(e.toString(), e);
		} finally {
			Closeables.closeQuietly(fw);
		}
	}

	/**
	 * Write extra session detail.
	 *
	 * @param fw the fw
	 * @param session the session
	 * @param option the option
	 */
	protected void writeExtraSessionInfo(FileWriter fw, ISession session,
			O option) throws IOException {
		// Hook for more Detail
	}

	/**
	 * Write extra session detail.
	 *
	 * @param fw the fw
	 * @param session the session
	 * @param option the option
	 */
	protected void writeExtraSessionDetail(FileWriter fw, ISession session,
			O option) throws IOException {
		// Hook for more Detail
	}

	/**
	 * Write extra set detail.
	 *
	 * @param fw the fw
	 * @param set the set
	 * @param option the option
	 * @throws IOException 
	 */
	protected void writeExtraSetInfo(FileWriter fw, ISet set, O option)
			throws IOException {
		// Hooks for more detail in Set Detail
	}

	/**
	 * Write extra set detail.
	 *
	 * @param fw the fw
	 * @param set the set
	 * @param option the option
	 */
	protected void writeExtraSetDetail(FileWriter fw, ISet set, O option)
			throws IOException {
		// Hooks for more detail in Set Detail
	}

	/**
	 * Write extra game detail.
	 *
	 * @param fw the fw
	 * @param game the game
	 * @param option the option
	 */
	protected void writeExtraGameInfo(FileWriter fw, IGame game, O option)
			throws IOException {
		// Hook for more detail game
	}

	/**
	 * Write extra game detail.
	 *
	 * @param fw the fw
	 * @param game the game
	 * @param option the option
	 */
	protected void writeGameDetail(FileWriter fw, IGame game, O option)
			throws IOException {
		// Hook for more detail game
	}

	/**
	 * Export stats csv.
	 *
	 * @param stats the stats
	 * @param elt the elt
	 * @param file the session file
	 * @param option the option
	 */
	protected void exportStatsCsv(List<IStatsService> stats, Object elt,
			File file, BasicExportOption option) {
		File statsFile;
		int count = 0;
		IElementStats<?> eltStats;
		FileWriter fw = null;
		for (IStatsService statsService : stats) {
			List<IPlayer> players;
			if (elt instanceof ISession) {
				eltStats = statsService.getSessionStats((ISession) elt);
				players = eltStats.getPlayers();
			} else if (elt instanceof ISet) {
				ISet set = (ISet) elt;
				eltStats = statsService.getSetStats(set);
				players = set.getGameDefinition().getInitialPlayers();
			} else {
				IGame game = (IGame) elt;
				eltStats = statsService.getGameStats(game);
				players = game.getPlayers();
			}
			// check if stats empty
			if (eltStats.getStatsEntries().isEmpty()) {
				// skip if no entries available
				continue;
			}

			statsFile = new File(file, MessageFormat.format(
					"{0}-stats_{1}.csv", this.normalize(elt.toString()),
					(++count)));

			Joiner joiner = Joiner.on(option.getCsvSeparator()).useForNull("-");
			EscapeCsvFuntion escapeCsv = new EscapeCsvFuntion();
			try {
				fw = new FileWriter(statsFile);

				// Header
				List<String> headers = new ArrayList<String>();
				headers.add("");
				headers.addAll(Lists.transform(players,
						new PlayerToStringFunction()));
				headers = Lists.transform(headers, escapeCsv);
				joiner.appendTo(fw, headers);
				fw.write('\n');

				// Entries
				List<String> entry;
				Function<IPlayer, Object> playerEntryValue;
				for (final IEntry<?> e : eltStats.getStatsEntries()) {
					if (e.isVisible()) {
						playerEntryValue = new PlayerEntryValueFunction(e);
						entry = new ArrayList<String>();
						entry.add(escapeCsv.apply(this.getStatsKeyLabel(e
								.getKey())));
						entry.addAll(Lists.transform(players,
								Functions.compose(escapeCsv, playerEntryValue)));
						joiner.appendTo(fw, entry);
						fw.write('\n');
					}
				}
			} catch (IOException e) {
				LOG.error(e.toString(), e);
			} finally {
				Closeables.closeQuietly(fw);
			}
		}
	}

	/**
	 * Gets the stats key label.
	 *
	 * @param statsKey the stats key
	 * @return the stats key label
	 */
	private String getStatsKeyLabel(String statsKey) {
		IStatsService statsService = this.statsProvider.get().getStatsService(
				statsKey);
		if (statsService != null) {
			IStatsUiService statsUiService = this.statsUiProvider.get()
					.getStatsUiService(statsService);
			if (statsUiService != null) {
				return statsUiService.getStatsLabelProvider().getText(statsKey);
			}
		}
		return statsKey;
	}

	/**
	 * Normalize.
	 *
	 * @param string the string
	 * @return the string
	 */
	public String normalize(String string) {
		String result = string.replace(':', '_');
		result = result.replace('/', '_');
		result = result.replace('\\', '_');
		result = result.replace('!', '_');
		result = result.replace('?', '_');
		return result.trim();
	}

	/**
	 * Export charts.
	 *
	 * @param charts the charts
	 * @param rootFolder the root folder
	 * @param option the option
	 */
	private void exportCharts(Collection<IChart> charts, File rootFolder,
			BasicExportOption option) {
		switch (option.getChartImageType()) {
			case JPEG:
				for (IChart chart : charts) {
					this.exportChartAsJpeg(chart, rootFolder, option);
				}
				break;
			case PNG:
				for (IChart chart : charts) {
					this.exportChartAsPng(chart, rootFolder, option);
				}
				break;
			default:
				break;
		}
	}

	/**
	 * Export chart as jpeg.
	 *
	 * @param chart the chart
	 * @param rootFolder the root folder
	 * @param option the option
	 */
	private void exportChartAsJpeg(IChart chart, File rootFolder,
			BasicExportOption option) {
		File chartFile = new File(rootFolder, this.normalize(chart.getName()
				+ ".jpg"));
		JFreeChart jfChart = chart.getChart();
		try {
			ChartUtilities.saveChartAsJPEG(chartFile, jfChart,
					option.getChartImageWidth(), option.getChartImageHeight());
		} catch (IOException e) {
			LOG.error("Could not export the chart {}", chart.getName());
		}
	}

	/**
	 * Export chart as png.
	 *
	 * @param chart the chart
	 * @param rootFolder the root folder
	 * @param option the option
	 */
	private void exportChartAsPng(IChart chart, File rootFolder,
			BasicExportOption option) {
		File chartFile = new File(rootFolder, this.normalize(chart.getName()
				+ ".png"));
		JFreeChart jfChart = chart.getChart();
		try {
			ChartUtilities.saveChartAsPNG(chartFile, jfChart,
					option.getChartImageWidth(), option.getChartImageHeight());
		} catch (IOException e) {
			LOG.error("Could not export the chart {}", chart.getName());
		}
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.IExportUiService#getImage()
	 */
	@Override
	public Image getImage() {
		return null;
	}

	/**
	 * Gets the session chart.
	 *
	 * @param session the session
	 * @return the session chart
	 */
	private List<IChart> getSessionChart(ISession session) {
		List<IChart> result = new ArrayList<IChart>();

		IElementStats<ISession> sesStats;
		IStatsUiService stUiService;
		for (IStatsService stService : this.statsProvider.get()
				.getAllStatsService()) {
			sesStats = stService.getSessionStats(session);
			stUiService = this.statsUiProvider.get().getStatsUiService(
					stService);
			for (String key : sesStats.getStatsKeys()) {
				result.addAll(stUiService.getCharts(session, key));
			}
		}

		return result;
	}

	/**
	 * Gets the sets the chart.
	 *
	 * @param set the set
	 * @return the sets the chart
	 */
	private Collection<IChart> getSetChart(ISet set) {
		List<IChart> result = new ArrayList<IChart>();

		IElementStats<ISet> setStats;
		IStatsUiService stUiService;
		List<IChart> charts;
		for (IStatsService stService : this.statsProvider.get()
				.getAllStatsService()) {
			setStats = stService.getSetStats(set);
			stUiService = this.statsUiProvider.get().getStatsUiService(
					stService);
			for (String key : setStats.getStatsKeys()) {
				charts = stUiService.getCharts(set, key);
				if (charts != null) {
					result.addAll(charts);
				}
			}
		}

		return result;
	}

	/**
	 * Gets the game chart.
	 *
	 * @param game the game
	 * @return the game chart
	 */
	private List<IChart> getGameChart(IGame game) {
		List<IChart> result = new ArrayList<IChart>();

		IElementStats<IGame> gameStats;
		IStatsUiService stUiService;
		for (IStatsService stService : this.statsProvider.get()
				.getAllStatsService()) {
			gameStats = stService.getGameStats(game);
			stUiService = this.statsUiProvider.get().getStatsUiService(
					stService);
			for (String key : gameStats.getStatsKeys()) {
				result.addAll(stUiService.getCharts(game, key));
			}
		}

		return result;
	}

	/**
	 * Sets the stats ui provider.
	 *
	 * @param statsUiProvider the new stats ui provider
	 */
	public void setStatsUiProvider(IStatsUiProvider statsUiProvider) {
		this.statsUiProvider.set(statsUiProvider);
	}

	/**
	 * Unset stats ui provider.
	 *
	 * @param statsUiProvider the stats ui provider
	 */
	public void unsetStatsUiProvider(IStatsUiProvider statsUiProvider) {
		this.statsUiProvider.compareAndSet(statsUiProvider, null);
	}

	/**
	 * Sets the stats provider.
	 *
	 * @param statsProvider the new stats provider
	 */
	public void setStatsProvider(IStatsProvider statsProvider) {
		this.statsProvider.set(statsProvider);
	}

	/**
	 * Unset stats provider.
	 *
	 * @param statsProvider the stats provider
	 */
	public void unsetStatsProvider(IStatsProvider statsProvider) {
		this.statsProvider.compareAndSet(statsProvider, null);
	}

	/**
	 * Sets the game ui provider.
	 *
	 * @param gameUiProvider the new game ui provider
	 */
	public void setGameUiProvider(IGameUiProvider gameUiProvider) {
		this.gameUiProvider.set(gameUiProvider);
	}

	/**
	 * Unset game ui provider.
	 *
	 * @param gameUiProvider the game ui provider
	 */
	public void unsetGameUiProvider(IGameUiProvider gameUiProvider) {
		this.gameUiProvider.compareAndSet(gameUiProvider, null);
	}

	/**
	 * Gets the game ui provider.
	 *
	 * @return the game ui provider
	 */
	public IGameUiProvider getGameUiProvider() {
		return this.gameUiProvider.get();
	}

	/**
	 * Gets the stats provider.
	 *
	 * @return the stats provider
	 */
	public IStatsProvider getStatsProvider() {
		return this.statsProvider.get();
	}

	/**
	 * Gets the stats ui provider.
	 *
	 * @return the stats ui provider
	 */
	public IStatsUiProvider getStatsUiProvider() {
		return this.statsUiProvider.get();
	}

	/**
	 * Gets the date format.
	 *
	 * @return the date format
	 */
	public DateFormat getDateFormat() {
		return this.dateFormat;
	}

	/**
	 * Gets the decimal format.
	 *
	 * @return the decimal format
	 */
	public NumberFormat getDecimalFormat() {
		return this.decimalFormat;
	}

	/**
	 * Gets the number format.
	 *
	 * @return the number format
	 */
	public NumberFormat getNumberFormat() {
		return this.numberFormat;
	}

}
