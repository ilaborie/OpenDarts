/*
 * 
 */
package org.opendarts.ui.export.x01.defi.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.player.func.PlayerToStringFunction;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.core.x01.defi.model.GameX01Defi;
import org.opendarts.ui.export.composite.AbstractExportOptionComposite;
import org.opendarts.ui.export.composite.BasicExportOptionComposite;
import org.opendarts.ui.export.model.Game;
import org.opendarts.ui.export.model.Session;
import org.opendarts.ui.export.model.Set;
import org.opendarts.ui.export.service.IExportUiService;
import org.opendarts.ui.export.service.impl.AbstractExportX01Service;
import org.opendarts.ui.export.service.impl.BasicExportOption;
import org.opendarts.ui.export.service.impl.EscapeCsvFuntion;
import org.opendarts.ui.export.x01.defi.model.GameDefi;
import org.opendarts.ui.export.x01.defi.model.SessionDefi;
import org.opendarts.ui.export.x01.model.GameEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.io.Closeables;
import com.google.common.io.Files;
import com.google.common.io.Resources;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * The Class BasicExportX01Service.
 */
public class HtmlExportX01Service extends
		AbstractExportX01Service<BasicExportOption> implements
		IExportUiService<BasicExportOption> {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(HtmlExportX01Service.class);

	/** The template config. */
	private final Configuration templateConfig;

	/**
	 * Instantiates a new basic export x01 service.
	 */
	public HtmlExportX01Service() {
		super();
		// Freemarker configuration
		try {
			freemarker.log.Logger
					.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_SLF4J);
		} catch (ClassNotFoundException e) {
			LOG.error("Could not configure Freemaker logger");
		}
		this.templateConfig = new Configuration();
		this.templateConfig.setClassForTemplateLoading(this.getClass(), "/");
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.IExportUiService#createExportOptionsComposite(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public AbstractExportOptionComposite<BasicExportOption> createExportOptionsComposite(
			Composite parent) {
		return new BasicExportOptionComposite(parent);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.IExportUiService#getName()
	 */
	@Override
	public String getName() {
		return "Html";
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#getFileExtension()
	 */
	@Override
	protected String getFileExtension() {
		return ".html";
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
		return GameX01Defi.class.equals(game.getClass());
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#createSession(org.opendarts.core.model.session.ISession, java.util.List)
	 */
	@Override
	protected Session createSession(ISession ses, List<IStatsService> stats) {
		return new SessionDefi(ses, stats);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#writeSession(java.io.Writer, org.opendarts.core.model.session.ISession, org.opendarts.ui.export.service.impl.BasicExportOption)
	 */
	@Override
	protected void writeSession(Writer writer, Session session,
			BasicExportOption option) throws IOException {

		Map<String, Object> objects = new HashMap<String, Object>();
		objects.put("session", session);
		objects.put("option", option);

		try {
			Template template = templateConfig.getTemplate("session.html.ftl");
			template.process(objects, writer);
			writer.flush();
		} catch (TemplateException e) {
			LOG.error("Error during Export session", e);
		}
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#writeSet(java.io.Writer, org.opendarts.ui.export.model.Set, org.opendarts.ui.export.service.impl.BasicExportOption)
	 */
	@Override
	protected void writeSet(Writer writer, Set set, BasicExportOption option)
			throws IOException {
		Map<String, Object> objects = new HashMap<String, Object>();
		objects.put("set", set);
		objects.put("option", option);

		try {
			Template template = templateConfig.getTemplate("set.html.ftl");
			template.process(objects, writer);
			writer.flush();
		} catch (TemplateException e) {
			LOG.error("Error during Export session", e);
		}
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#writeGameDetail(java.io.Writer, org.opendarts.ui.export.model.Game, org.opendarts.ui.export.service.impl.BasicExportOption)
	 */
	@Override
	protected void writeGame(Writer writer, Game game, BasicExportOption option)
			throws IOException {
		Map<String, Object> objects = new HashMap<String, Object>();
		objects.put("game", game);
		objects.put("option", option);

		try {
			Template template = templateConfig.getTemplate("game.html.ftl");
			template.process(objects, writer);
			writer.flush();
		} catch (TemplateException e) {
			LOG.error("Error during Export session", e);
		}
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#writeSessionDetail(java.io.File, org.opendarts.ui.export.model.Session, org.opendarts.ui.export.service.impl.BasicExportOption)
	 */
	@Override
	protected void writeSessionDetail(File sessionDetailFile, Session session,
			BasicExportOption option) {
		super.writeSessionDetail(sessionDetailFile, session, option);

		File folder = sessionDetailFile.getParentFile();
		this.copyCss(folder);
		this.copyImageDir(folder);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#writeSetDetail(java.io.File, org.opendarts.ui.export.model.Set, org.opendarts.ui.export.service.impl.BasicExportOption)
	 */
	@Override
	protected void writeSetDetail(File setDetailFile, Set set,
			BasicExportOption option) {
		super.writeSetDetail(setDetailFile, set, option);
		
		File folder = setDetailFile.getParentFile();
		this.copyCss(folder);
		this.copyImageDir(folder);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#writeGameDetail(java.io.File, org.opendarts.ui.export.model.Game, org.opendarts.ui.export.service.impl.BasicExportOption)
	 */
	@Override
	protected void writeGameDetail(File gameDetailFile, Game game,
			BasicExportOption option) {
		super.writeGameDetail(gameDetailFile, game, option);
		File folder = gameDetailFile.getParentFile();
		this.copyCss(folder);
		this.copyImageDir(folder);

		// Export Throws
		this.exportGameEntries((GameDefi) game, folder,
				option);
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

	/**
	 * Copy css.
	 *
	 * @param folder the folder
	 */
	private void copyCss(File folder) {
		// Copy CSS
		try {
			URL res = this.getClass().getClassLoader()
					.getResource("opendarts.css");
			File file = new File(folder, "opendarts.css");
			Files.copy(Resources.newInputStreamSupplier(res), file);
		} catch (IOException e) {
			LOG.error("Could not copy css file", e);
		}
	}

	/**
	 * Copy image dir.
	 *
	 * @param folder the folder
	 */
	private void copyImageDir(File folder) {
		try {
			String prefix = "img";
			String[] images = new String[] { "background.png", "block.png",
					"opendarts-banner.png", "title.png" };
			File parentFolder = new File(folder, prefix);
			ClassLoader classLoader = this.getClass().getClassLoader();
			URL res;
			File file;
			for (String image : images) {
				res = classLoader.getResource(prefix+'/'+image);
				file = new File(parentFolder, image);
				Files.createParentDirs(file);
				Files.copy(Resources.newInputStreamSupplier(res), file);
			}
		} catch (IOException e) {
			LOG.error("Could not copy dir file", e);
		}
	}

}
