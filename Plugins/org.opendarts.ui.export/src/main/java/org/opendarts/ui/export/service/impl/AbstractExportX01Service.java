/*
 * 
 */
package org.opendarts.ui.export.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.swt.graphics.Image;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.player.func.PlayerToStringFunction;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.stats.service.IStatsProvider;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.ui.export.model.AbstractBean;
import org.opendarts.ui.export.model.Game;
import org.opendarts.ui.export.model.Session;
import org.opendarts.ui.export.model.Set;
import org.opendarts.ui.export.model.Stats;
import org.opendarts.ui.export.model.StatsEntry;
import org.opendarts.ui.export.service.IExportUiService;
import org.opendarts.ui.service.IGameUiProvider;
import org.opendarts.ui.stats.model.IChart;
import org.opendarts.ui.stats.service.IStatsUiProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Closeables;
import com.google.common.io.Files;

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

	/**
	 * Instantiates a new basic export x01 service.
	 */
	public AbstractExportX01Service() {
		super();
		// OSGI service dependencies
		this.statsUiProvider = new AtomicReference<IStatsUiProvider>();
		this.statsProvider = new AtomicReference<IStatsProvider>();
		this.gameUiProvider = new AtomicReference<IGameUiProvider>();
	}

	/**
	 * Export.
	 *
	 * @param session the session
	 * @param file the file
	 * @param option the option
	 */
	@Override
	public void export(ISession ses, File file, O option) {
		List<IStatsService> stats = this.getStatsProvider()
				.getSessionStats(ses);
		Session session = createSession(ses, stats);

		// Session Root Dir
		File sessionFile = new File(file, session.getRootName());
		sessionFile.mkdirs();

		File sessionDetailFile = new File(sessionFile, session.getFileName()
				+ this.getFileExtension());

		// Write Detail
		this.writeSessionDetail(sessionDetailFile, session, option);

		// Export stats
		if (option.isExportStatsAsCsv()) {
			this.exportStatsCsv(session, sessionFile, option);
		}

		// Extract chart image
		if (option.isExportChart()) {
			this.exportCharts(session.getCharts(), sessionFile, option);
		}

		// Write set detail
		for (Set set : session.getSets()) {
			this.export(set, sessionFile, option);
		}
	}

	/**
	 * Creates the session.
	 *
	 * @param ses the ses
	 * @param stats the stats
	 * @return the session
	 */
	protected Session createSession(ISession ses, List<IStatsService> stats) {
		return new Session(ses, stats);
	}

	/**
	 * Export.
	 *
	 * @param set the session
	 * @param file the file
	 * @param option the option
	 */
	public void export(Set set, File file, O option) {
		File setFile = new File(file, set.getRootName());
		setFile.mkdirs();

		File setDetailFile = new File(setFile, set.getFileName()
				+ this.getFileExtension());
		this.writeSetDetail(setDetailFile, set, option);

		// Write set-stats.csv
		if (option.isExportStatsAsCsv()) {
			this.exportStatsCsv(set, setFile, option);
		}
		// Extract png chart
		if (option.isExportChart()) {
			this.exportCharts(set.getCharts(), setFile, option);
		}

		// Export game
		for (Game game : set.getGames()) {
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
	public void export(Game game, File file, O option) {
		File gameFile = new File(file, game.getRootName());
		gameFile.mkdirs();

		File gameDetailFile = new File(gameFile, game.getFileName()
				+ this.getFileExtension());
		this.writeGameDetail(gameDetailFile, game, option);

		// Write set-stats.csv
		if (option.isExportStatsAsCsv()) {
			this.exportStatsCsv(game, gameFile, option);
		}

		// Export chart
		if (option.isExportChart()) {
			this.exportCharts(game.getCharts(), gameFile, option);
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
	protected void writeSessionDetail(File sessionDetailFile, Session session,
			O option) {
		Writer writer = null;
		try {
			writer = Files.newWriter(sessionDetailFile, Charsets.UTF_8);
			this.writeSession(writer, session, option);

		} catch (IOException e) {
			LOG.error(e.toString(), e);
		} finally {
			Closeables.closeQuietly(writer);
		}
	}

	/**
	 * Write session.
	 *
	 * @param writer the writer
	 * @param session the session
	 * @param option the option
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void writeSession(Writer writer, Session session, O option)
			throws IOException {
		// Title
		String title = session.toString();
		writer.write(title);
		writer.write('\n');
		writer.write(Strings.repeat("=", title.length()));
		writer.write('\n');
		writer.write('\n');

		// Status
		String status = "Status";
		writer.write(status);
		writer.write('\n');
		writer.write(Strings.repeat("-", status.length()));
		writer.write('\n');

		// Started
		writer.write("Started at: ");
		writer.write(session.getStart());
		writer.write('\n');

		// End
		if (session.getEnd() != null) {
			writer.write("Ended at: ");
			writer.write(session.getEnd());
			writer.write('\n');
		}

		// Winner
		if (session.getWinner() != null) {
			writer.write("Winner: ");
			writer.write(session.getWinner().toString());
			writer.write('\n');
		}

		// Hooks
		this.writeExtraSessionInfo(writer, session, option);

		// Detail
		writer.write('\n');
		String detail = "Detail";
		writer.write(detail);
		writer.write('\n');
		writer.write(Strings.repeat("-", detail.length()));
		writer.write('\n');
		writer.write('\n');

		Joiner joiner = Joiner.on('\t');
		List<String> entry;
		for (Set set : session.getSets()) {
			entry = Arrays.asList(set.getIndex(), set.getPlayers(),
					set.getWinner(), set.getResult());
			joiner.appendTo(writer, entry);
			writer.write('\n');
		}

		// Hooks
		this.writeExtraSessionDetail(writer, session, option);
	}

	/**
	 * Write set detail.
	 *
	 * @param setDetailFile the set detail file
	 * @param set the set
	 * @param option the option
	 */
	protected void writeSetDetail(File setDetailFile, Set set, O option) {
		Writer writer = null;
		try {
			writer = Files.newWriter(setDetailFile, Charsets.UTF_8);
			this.writeSet(writer, set, option);
		} catch (IOException e) {
			LOG.error(e.toString(), e);
		} finally {
			Closeables.closeQuietly(writer);
		}
	}

	/**
	 * Write set.
	 *
	 * @param writer the writer
	 * @param set the set
	 * @param option the option
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void writeSet(Writer writer, Set set, O option)
			throws IOException {
		// Title
		String title = set.toString();
		writer.write(title);
		writer.write('\n');
		writer.write(Strings.repeat("=", title.length()));
		writer.write('\n');
		writer.write('\n');

		// Description
		String description = set.getDescription();
		if (!Strings.isNullOrEmpty(description)) {
			String descrTitle = "Description";
			writer.write(descrTitle);
			writer.write('\n');
			writer.write(Strings.repeat("-", descrTitle.length()));
			writer.write('\n');
			writer.write(description);
			writer.write('\n');
			writer.write('\n');
		}
		// Status
		String status = "Status";
		writer.write(status);
		writer.write('\n');
		writer.write(Strings.repeat("-", status.length()));
		writer.write('\n');
		writer.write('\n');

		// Started
		writer.write("Started at: ");
		writer.write(set.getStart());
		writer.write('\n');

		// End
		if (set.getEnd() != null) {
			writer.write("Ended at: ");
			writer.write(set.getEnd());
			writer.write('\n');
		}

		// Winner
		if (set.getWinner() != null) {
			writer.write("Winner: ");
			writer.write(set.getWinner().toString());
			writer.write('\n');
		}

		// Hook
		this.writeExtraSetInfo(writer, set, option);

		// Detail
		writer.write('\n');
		String detailTitle = "Detail";
		writer.write(detailTitle);
		writer.write('\n');
		writer.write(Strings.repeat("-", detailTitle.length()));
		writer.write('\n');
		writer.write('\n');

		Joiner joiner = Joiner.on('\t');
		List<String> entry;
		for (Game game : set.getGames()) {
			entry = Arrays.asList(game.getIndex(), game.getPlayers(),
					game.getWinner(), game.getResult());
			joiner.appendTo(writer, entry);
			writer.write('\n');
		}
		// Hook
		this.writeExtraSetDetail(writer, set, option);

	}

	/**
	 * Write game detail.
	 *
	 * @param gameDetailFile the game detail file
	 * @param game the game
	 * @param option the option
	 */
	protected void writeGameDetail(File gameDetailFile, Game game, O option) {
		Writer writer = null;
		try {
			writer = Files.newWriter(gameDetailFile, Charsets.UTF_8);
			this.writeGame(writer, game, option);
		} catch (IOException e) {
			LOG.error(e.toString(), e);
		} finally {
			Closeables.closeQuietly(writer);
		}
	}

	/**
	 * Write.
	 *
	 * @param writer the writer
	 * @param game the game
	 * @param option the option
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void writeGame(Writer writer, Game game, O option) throws IOException {
		// Title
		String title = game.toString();
		writer.write(title);
		writer.write('\n');
		writer.write(Strings.repeat("=", title.length()));
		writer.write('\n');
		writer.write('\n');

		// Description
		String description = game.getDescription();
		if (!Strings.isNullOrEmpty(description)) {
			String descrTitle = "Description";
			writer.write(descrTitle);
			writer.write('\n');
			writer.write(Strings.repeat("-", descrTitle.length()));
			writer.write('\n');
			writer.write(description);
			writer.write('\n');
			writer.write('\n');
		}

		// Status
		String status = "Status";
		writer.write(status);
		writer.write('\n');
		writer.write(Strings.repeat("-", status.length()));
		writer.write('\n');
		writer.write('\n');

		// Started
		writer.write("Started at: ");
		writer.write(game.getStart());
		writer.write('\n');

		// End
		if (game.getEnd() != null) {
			writer.write("Ended at: ");
			writer.write(game.getEnd());
			writer.write('\n');
		}

		// Winner
		if (game.getWinner() != null) {
			writer.write("Winner: ");
			writer.write(game.getWinner().toString());
			writer.write('\n');
		}

		this.writeExtraGameInfo(writer, game, option);

		// Detail
		this.writeGameDetail(writer, game, option);
	}

	/**
	 * Write extra session detail.
	 *
	 * @param fw the fw
	 * @param session the session
	 * @param option the option
	 */
	protected void writeExtraSessionInfo(Writer writer, Session session,
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
	protected void writeExtraSessionDetail(Writer writer, Session session,
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
	protected void writeExtraSetInfo(Writer writer, Set set, O option)
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
	protected void writeExtraSetDetail(Writer writer, Set set, O option)
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
	protected void writeExtraGameInfo(Writer writer, Game game, O option)
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
	protected void writeGameDetail(Writer writer, Game game, O option)
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
	protected <E> void exportStatsCsv(AbstractBean<E> bean, File file,
			BasicExportOption option) {
		File statsFile;
		Writer writer = null;
		List<IPlayer> players = bean.getPlayerList();

		Joiner joiner = Joiner.on(option.getCsvSeparator()).useForNull("-");
		EscapeCsvFuntion escapeCsv = new EscapeCsvFuntion();

		for (Stats<E> stats : bean.getStats()) {
			statsFile = new File(file, stats.getFileName());

			try {
				writer = Files.newWriter(statsFile, Charsets.UTF_8);
				// Header
				List<String> headers = new ArrayList<String>();
				headers.add("");
				headers.addAll(Lists.transform(players,
						new PlayerToStringFunction()));
				headers = Lists.transform(headers, escapeCsv);
				joiner.appendTo(writer, headers);
				writer.write('\n');

				// Entries
				List<String> entry;
				for (StatsEntry<E> e : stats.getEntries()) {
					entry = new ArrayList<String>();
					entry.add(escapeCsv.apply(e.getLabel()));
					entry.addAll(Lists.transform(e.getPlayersValues(),
							escapeCsv));
					joiner.appendTo(writer, entry);
					writer.write('\n');
				}
			} catch (IOException e) {
				LOG.error(e.toString(), e);
			} finally {
				Closeables.closeQuietly(writer);
			}
		}
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
		File chartFile = new File(rootFolder, chart.getName() + ".jpg");
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
		File chartFile = new File(rootFolder, chart.getName() + ".png");
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

}
