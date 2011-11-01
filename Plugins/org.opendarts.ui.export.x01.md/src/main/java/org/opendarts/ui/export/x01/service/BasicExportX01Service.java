/*
 * 
 */
package org.opendarts.ui.export.x01.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.player.func.PlayerToStringFunction;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.core.x01.model.GameX01;
import org.opendarts.core.x01.model.GameX01Entry;
import org.opendarts.ui.export.composite.AbstractExportOptionComposite;
import org.opendarts.ui.export.model.Game;
import org.opendarts.ui.export.model.Session;
import org.opendarts.ui.export.model.Set;
import org.opendarts.ui.export.service.IExportUiService;
import org.opendarts.ui.export.service.impl.AbstractExportX01Service;
import org.opendarts.ui.export.service.impl.BasicExportOption;
import org.opendarts.ui.export.service.impl.EscapeCsvFuntion;
import org.opendarts.ui.export.x01.model.GameEntry;
import org.opendarts.ui.export.x01.model.SessionX01;
import org.opendarts.ui.export.x01.model.SetX01;
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
 */
public class BasicExportX01Service extends
		AbstractExportX01Service<BasicExportOption> implements
		IExportUiService<BasicExportOption> {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(BasicExportX01Service.class);

	/**
	 * Instantiates a new basic export x01 service.
	 */
	public BasicExportX01Service() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.IExportUiService#getName()
	 */
	@Override
	public String getName() {
		return "Markdown (Text File)";
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#getFileExtension()
	 */
	@Override
	protected String getFileExtension() {
		return ".md";
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.IExportUiService#createExportOptionsComposite(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public AbstractExportOptionComposite<BasicExportOption> createExportOptionsComposite(
			Composite parent) {
		return new AbstractExportOptionComposite<BasicExportOption>(parent) {
			@Override
			public BasicExportOption getExportOptions() {
				return new BasicExportOption();
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
		return GameX01.class.equals(game.getClass());
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#createSession(org.opendarts.core.model.session.ISession, java.util.List)
	 */
	@Override
	protected Session createSession(ISession ses, List<IStatsService> stats) {
		return new SessionX01(ses, stats);
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

		if (set instanceof SetX01) {
			SetX01 setX01 = (SetX01) set;
			// String Score
			writer.write("Starting Score: ");
			writer.write(setX01.getStartingScore());
			writer.write('\n');

			// Number Game to Win
			writer.write("Number game to win: ");
			writer.write(setX01.getNbGameToWin());
			writer.write('\n');
			writer.write('\n');
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

		if (game instanceof org.opendarts.ui.export.x01.model.GameX01) {
			org.opendarts.ui.export.x01.model.GameX01 gameX01 = (org.opendarts.ui.export.x01.model.GameX01) game;
			// String Score
			writer.write("Starting Score: ");
			writer.write(gameX01.getStartingScore());
			writer.write('\n');

			// Number Game to Win
			writer.write("Number game to win: ");
			writer.write(gameX01.getNbGameToWin());
			writer.write('\n');
			writer.write('\n');
		}
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
				(org.opendarts.ui.export.x01.model.GameX01) game,
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
	protected void exportGameEntries(
			org.opendarts.ui.export.x01.model.GameX01 game, File gameFile,
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
}
