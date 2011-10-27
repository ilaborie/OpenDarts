/*
 * 
 */
package org.opendarts.ui.x01.service.export;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.opendarts.core.model.dart.impl.ThreeDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.model.IElementStats;
import org.opendarts.core.stats.model.IElementStats.IEntry;
import org.opendarts.core.stats.model.IStatValue;
import org.opendarts.core.stats.model.IStatsEntry;
import org.opendarts.core.stats.service.IStatsProvider;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.core.x01.model.GameX01;
import org.opendarts.core.x01.model.GameX01Entry;
import org.opendarts.ui.export.composite.AbstractExportOptionComposite;
import org.opendarts.ui.export.service.IExportUiService;
import org.opendarts.ui.stats.model.IChart;
import org.opendarts.ui.stats.service.IStatsUiProvider;
import org.opendarts.ui.stats.service.IStatsUiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class BasicExportX01Service.
 */
public class BasicExportX01Service implements IExportUiService<NoExportOptions> {

	private static final char CSV_SEPARATOR = ';';

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(BasicExportX01Service.class);

	/** The date format. */
	private final DateFormat dateFormat;

	/** The stats ui provider. */
	private final AtomicReference<IStatsUiProvider> statsUiProvider;

	/** The stats ui provider. */
	private final AtomicReference<IStatsProvider> statsProvider;

	/**
	 * Instantiates a new basic export x01 service.
	 */
	public BasicExportX01Service() {
		super();
		this.dateFormat = DateFormat.getDateTimeInstance();
		this.statsUiProvider = new AtomicReference<IStatsUiProvider>();
		this.statsProvider = new AtomicReference<IStatsProvider>();
	}

	/**
	 * Export.
	 *
	 * @param session the session
	 * @param file the file
	 * @param option the option
	 */
	@Override
	public void export(ISession session, File file, NoExportOptions option) {
		File sessionFile = new File(file, this.normalize(session.toString()));
		sessionFile.mkdirs();

		File sessionDetailFile = new File(sessionFile, this.normalize(session
				.toString() + ".md"));

		// Write Detail
		FileWriter fw = null;
		try {
			fw = new FileWriter(sessionDetailFile);
			// Title
			String title = session.toString();
			fw.write(title);
			fw.write('\n');
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

			// Extra
			this.writeExtraSessionDetail(fw, session);
		} catch (IOException e) {
			LOG.error(e.toString(), e);
		} finally {
			this.closeQuietly(fw);
		}

		// write session-stats.csv
		List<IStatsService> stats = this.statsProvider.get().getSessionStats(
				session);
		this.exportStatsCsv(stats, session, sessionFile);

		// Extract chart image
		this.exportCharts(this.getSessionChart(session), sessionFile);

		// Write set detail
		for (ISet set : session.getAllGame()) {
			this.export(set, sessionFile, option);
		}
	}

	/**
	 * Write extra session detail.
	 *
	 * @param fw the fw
	 * @param session the session
	 */
	protected void writeExtraSessionDetail(FileWriter fw, ISession session) {
		// Hook for sub classes
	}

	/**
	 * Export stats csv.
	 *
	 * @param stats the stats
	 * @param elt the elt
	 * @param sessionFile the session file
	 */
	protected void exportStatsCsv(List<IStatsService> stats, Object elt,
			File sessionFile) {
		File statsFile;
		int count = 0;
		IElementStats<?> eltStats;
		FileWriter fw = null;
		for (IStatsService statsService : stats) {
			if (elt instanceof ISession) {
				eltStats = statsService.getSessionStats((ISession) elt);
			} else if (elt instanceof ISet) {
				eltStats = statsService.getSetStats((ISet) elt);
			} else {
				eltStats = statsService.getGameStats((IGame) elt);

			}
			statsFile = new File(sessionFile, MessageFormat.format(
					"{0}-stats_{1}.csv", this.normalize(elt.toString()),
					(++count)));
			try {
				fw = new FileWriter(statsFile);
				// Header
				fw.write("\"\""); // nothing
				for (IPlayer p : eltStats.getPlayers()) {
					fw.write(CSV_SEPARATOR);
					// Player
					fw.write('"');
					fw.write(p.toString());
					fw.write('"');
				}
				fw.write('\n');

				// Entries
				for (IEntry<?> e : eltStats.getStatsEntries()) {
					fw.write('"');
					fw.write(this.getStatsKeyLabel(e.getKey()));// entries
					fw.write('"');
					for (IPlayer p : eltStats.getPlayers()) {
						fw.write(CSV_SEPARATOR);
						// Stats value
						fw.write('"');
						fw.write(this.getPlayerStatsValue(e, p));
						fw.write('"');
					}
					fw.write('\n');
				}
			} catch (IOException e) {
				LOG.error(e.toString(), e);
			} finally {
				this.closeQuietly(fw);
			}
		}
	}

	/**
	 * Gets the player stats value.
	 *
	 * @param e the entry
	 * @param p the player
	 * @return the player stats value
	 */
	@SuppressWarnings("rawtypes")
	private String getPlayerStatsValue(IEntry<?> e, IPlayer p) {
		String result = "-";
		IStatsEntry entry = ((IEntry) e).getPlayerEntry(p);
		IStatValue value = entry.getValue();
		if (value != null) {
			result = value.getValueAsString();
		}
		return result;
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
	 * Close quietly.
	 *
	 * @param writer the writer
	 */
	private void closeQuietly(Writer writer) {
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				LOG.error(e.toString(), e);
			}
		}
	}

	/**
	 * Normalize.
	 *
	 * @param string the string
	 * @return the string
	 */
	private String normalize(String string) {
		String result = string.replace(':', '_');
		result = result.replace('/', '_');
		result = result.replace('\\', '_');
		result = result.replace('!', '_');
		result = result.replace('?', '_');
		return result;
	}

	/**
	 * Export.
	 *
	 * @param set the session
	 * @param file the file
	 * @param option the option
	 */
	@Override
	public void export(ISet set, File file, NoExportOptions option) {
		File setFile = new File(file, this.normalize(set.getName()));
		setFile.mkdirs();

		File setDetailFile = new File(setFile, this.normalize(set.getName()
				+ ".md"));

		// Write Detail
		FileWriter fw = null;
		try {
			fw = new FileWriter(setDetailFile);
			// Title
			String title = set.toString();
			fw.write(title);
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

			// Extra
			this.writeExtraSetDetail(fw, set);
		} catch (IOException e) {
			LOG.error(e.toString(), e);
		} finally {
			this.closeQuietly(fw);
		}

		// Write set-stats.csv
		List<IStatsService> stats = this.statsProvider.get().getSetStats(set);
		this.exportStatsCsv(stats, set, setFile);

		// Extract png chart
		this.exportCharts(this.getSetChart(set), setFile);

		// Export game
		for (IGame game : set.getAllGame()) {
			this.export(game, setFile, option);
		}
	}

	/**
	 * Write extra set detail.
	 *
	 * @param fw the fw
	 * @param set the set
	 */
	protected void writeExtraSetDetail(FileWriter fw, ISet set) {
		// Hook for Set detail (sub classes)
	}

	/**
	 * Export charts.
	 *
	 * @param charts the charts
	 * @param rootFolder the root folder
	 */
	private void exportCharts(Collection<IChart> charts, File rootFolder) {
		File chartFile;
		JFreeChart jfChart;
		for (IChart chart : charts) {
			chartFile = new File(rootFolder, this.normalize(chart.getName()
					+ ".png"));
			jfChart = chart.getChart();
			try {
				ChartUtilities.saveChartAsPNG(chartFile, jfChart, 640, 480);
			} catch (IOException e) {
				LOG.error("Could not export the chart {}", chart.getName());
			}
		}
	}

	/**
	 * Export.
	 *
	 * @param igame the session
	 * @param file the file
	 * @param option the option
	 */
	@Override
	public void export(IGame igame, File file, NoExportOptions option) {
		GameX01 game = (GameX01) igame;
		File gameFile = new File(file, this.normalize(game.getName()));
		gameFile.mkdirs();

		File gameDetailFile = new File(gameFile, this.normalize(game.getName()
				+ ".md"));

		// Write Detail
		FileWriter fw = null;
		try {
			fw = new FileWriter(gameDetailFile);
			// Title
			String title = game.toString();
			fw.write(title);
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

			// Extra
			this.writeExtraGameDetail(fw, game);
		} catch (IOException e) {
			LOG.error(e.toString(), e);
		} finally {
			this.closeQuietly(fw);
		}

		// Write set-stats.csv
		List<IStatsService> stats = this.statsProvider.get().getGameStats(game);
		this.exportStatsCsv(stats, game, gameFile);

		// Extract png chart
		this.exportCharts(this.getGameChart(game), gameFile);
		// Game entries
		this.exportGameEntries(game, gameFile);
	}

	/**
	 * Write extra game detail.
	 *
	 * @param fw the fw
	 * @param game the game
	 */
	protected void writeExtraGameDetail(FileWriter fw, GameX01 game) {
		// Hook for sub-classes
	}

	/**
	 * Export game entries.
	 *
	 * @param game the game
	 * @param gameFile the game file
	 */
	protected void exportGameEntries(GameX01 game, File gameFile) {
		File entriesFile = new File(gameFile, game.getName() + "-throws.csv");
		FileWriter fw = null;
		try {
			fw = new FileWriter(entriesFile);
			// Header
			fw.write("\"\""); // nothing
			for (IPlayer p : game.getPlayers()) {
				fw.write(CSV_SEPARATOR);
				// Player
				fw.write('"');
				fw.write(p.toString());
				fw.write('"');
			}
			fw.write('\n');

			// Entries
			GameX01Entry entry;
			Map<IPlayer, ThreeDartsThrow> playerThrow;
			for (IGameEntry e : game.getGameEntries()) {
				entry = (GameX01Entry) e;
				fw.write('"');
				fw.write('#');
				fw.write(String.valueOf(e.getRound()));// entries
				fw.write('"');
				playerThrow = entry.getPlayerThrow();
				for (IPlayer p : game.getPlayers()) {
					fw.write(CSV_SEPARATOR);
					// Stats value
					fw.write('"');
					playerThrow = entry.getPlayerThrow();
					ThreeDartsThrow dartsThrow = playerThrow.get(p);
					if (dartsThrow != null) {
						fw.write(String.valueOf(dartsThrow.getScore()));
					} else {
						fw.write("");
					}
					fw.write('"');
				}
				fw.write('\n');
			}
		} catch (IOException e) {
			LOG.error(e.toString(), e);
		} finally {
			this.closeQuietly(fw);
		}

	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.IExportUiService#getName()
	 */
	@Override
	public String getName() {
		return "Basic Export";
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.IExportUiService#getImage()
	 */
	@Override
	public Image getImage() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.IExportUiService#createExportOptionsComposite(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public AbstractExportOptionComposite<NoExportOptions> createExportOptionsComposite(
			Composite parent) {
		return new AbstractExportOptionComposite<NoExportOptions>(parent) {
			@Override
			public NoExportOptions getExportOptions() {
				return new NoExportOptions();
			}
		};
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.IExportUiService#isApplicable(org.opendarts.core.model.session.ISession)
	 */
	@Override
	public boolean isApplicable(ISession session) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.IExportUiService#isApplicable(org.opendarts.core.model.session.ISet)
	 */
	@Override
	public boolean isApplicable(ISet set) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.IExportUiService#isApplicable(org.opendarts.core.model.game.IGame)
	 */
	@Override
	public boolean isApplicable(IGame game) {
		//		return GameX01.class.equals(game.getClass());
		return (game instanceof GameX01);
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

}
