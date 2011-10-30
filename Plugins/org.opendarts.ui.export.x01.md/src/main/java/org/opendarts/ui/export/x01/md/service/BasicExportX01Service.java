/*
 * 
 */
package org.opendarts.ui.export.x01.md.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.game.func.GameIndexFunction;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.player.func.PlayerToStringFunction;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.model.session.func.SetIndexFunction;
import org.opendarts.core.x01.model.GameX01;
import org.opendarts.core.x01.model.GameX01Definition;
import org.opendarts.core.x01.model.GameX01Entry;
import org.opendarts.ui.export.composite.AbstractExportOptionComposite;
import org.opendarts.ui.export.service.IExportUiService;
import org.opendarts.ui.export.service.impl.AbstractExportX01Service;
import org.opendarts.ui.export.service.impl.BasicExportOption;
import org.opendarts.ui.export.service.impl.EscapeCsvFuntion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Closeables;

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
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#writeExtraSetDetail(java.io.FileWriter, org.opendarts.core.model.session.ISet, org.opendarts.ui.export.service.impl.BasicExportOption)
	 */
	@Override
	protected void writeExtraSetInfo(FileWriter fw, ISet set,
			BasicExportOption option) throws IOException {
		GameX01Definition gameDefinition = (GameX01Definition) set
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
		fw.write('\n');
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#writeExtraGameInfo(java.io.FileWriter, org.opendarts.core.model.game.IGame, org.opendarts.ui.export.service.impl.BasicExportOption)
	 */
	@Override
	protected void writeExtraGameInfo(FileWriter fw, IGame game,
			BasicExportOption option) throws IOException {

		GameX01Definition gameDefinition = (GameX01Definition) game
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
		fw.write('\n');
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#writeGameDetail(java.io.File, org.opendarts.core.model.game.IGame, org.opendarts.core.export.IExportOptions)
	 */
	@Override
	protected void writeGameDetail(File gameDetailFile, IGame game,
			BasicExportOption option) {
		super.writeGameDetail(gameDetailFile, game, option);

		// Game entries
		this.exportGameEntries((GameX01) game, gameDetailFile.getParentFile(),
				option);
	}

	@Override
	protected void writeGameDetail(FileWriter fw, IGame game,
			BasicExportOption option) throws IOException {
		// Detail
		String detailTitle = "Detail";
		fw.write(detailTitle);
		fw.write('\n');
		fw.write(Strings.repeat("-", detailTitle.length()));
		fw.write('\n');
		fw.write('\n');

		Joiner joiner = Joiner.on('\t');

		List<IPlayer> players = game.getPlayers();

		// Header
		List<String> headers = new ArrayList<String>();
		headers.add("   ");
		headers.addAll(Lists.transform(players, new PlayerToStringFunction()));
		joiner.appendTo(fw, headers);
		fw.write('\n');

		// Entries
		PlayerGameEntryScoreFunction entryScoreFunction;
		GameX01Entry entry;
		List<Object> lst;
		for (IGameEntry e : game.getGameEntries()) {
			entry = (GameX01Entry) e;
			entryScoreFunction = new PlayerGameEntryScoreFunction(entry);
			lst = new ArrayList<Object>();
			lst.add("#" + entry.getRound());
			lst.addAll(Lists.transform(players, entryScoreFunction));

			joiner.appendTo(fw, lst);
			fw.write('\n');
		}
	}

	/**
	 * Export game entries.
	 *
	 * @param game the game
	 * @param gameFile the game file
	 * @param option 
	 */
	protected void exportGameEntries(GameX01 game, File gameFile,
			BasicExportOption option) {
		File entriesFile = new File(gameFile, game.getName() + "-throws.csv");
		FileWriter fw = null;

		List<IPlayer> players = game.getPlayers();
		Joiner joiner = Joiner.on(option.getCsvSeparator()).useForNull("-");
		EscapeCsvFuntion escapeCsv = new EscapeCsvFuntion();

		try {
			fw = new FileWriter(entriesFile);

			// Header
			List<String> headers = new ArrayList<String>();
			headers.add("");
			headers.addAll(Lists.transform(players,
					new PlayerToStringFunction()));
			headers = Lists.transform(headers, escapeCsv);
			joiner.appendTo(fw, headers);
			fw.write('\n');

			// Entries
			PlayerGameEntryScoreFunction entryScoreFunction;
			GameX01Entry entry;
			List<Object> lst;
			for (IGameEntry e : game.getGameEntries()) {
				entry = (GameX01Entry) e;
				entryScoreFunction = new PlayerGameEntryScoreFunction(entry);
				lst = new ArrayList<Object>();
				lst.add("#" + entry.getRound());
				lst.addAll(Lists.transform(players, entryScoreFunction));

				joiner.appendTo(fw, lst);
				fw.write('\n');
			}
		} catch (IOException e) {
			LOG.error(e.toString(), e);
		} finally {
			Closeables.closeQuietly(fw);
		}
	}
}
