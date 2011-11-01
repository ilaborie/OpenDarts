/*
 * 
 */
package org.opendarts.ui.export.x01.service;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.core.x01.model.GameX01;
import org.opendarts.ui.export.composite.AbstractExportOptionComposite;
import org.opendarts.ui.export.model.Game;
import org.opendarts.ui.export.model.Session;
import org.opendarts.ui.export.model.Set;
import org.opendarts.ui.export.service.IExportUiService;
import org.opendarts.ui.export.service.impl.AbstractExportX01Service;
import org.opendarts.ui.export.service.impl.BasicExportOption;
import org.opendarts.ui.export.x01.model.SessionX01;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		return new AbstractExportOptionComposite<BasicExportOption>(parent) {
			@Override
			public BasicExportOption getExportOptions() {
				return new BasicExportOption();
			}
		};
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
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#writeSessionDetail(java.io.File, org.opendarts.ui.export.model.Session, org.opendarts.ui.export.service.impl.BasicExportOption)
	 */
	@Override
	protected void writeSessionDetail(File sessionDetailFile, Session session,
			BasicExportOption option) {
		super.writeSessionDetail(sessionDetailFile, session, option);
		this.copyCss(sessionDetailFile.getParentFile());
	}
	
	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#writeSetDetail(java.io.File, org.opendarts.ui.export.model.Set, org.opendarts.ui.export.service.impl.BasicExportOption)
	 */
	@Override
	protected void writeSetDetail(File setDetailFile, Set set,
			BasicExportOption option) {
		super.writeSetDetail(setDetailFile, set, option);
		this.copyCss(setDetailFile.getParentFile());
	}
	
	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#writeGameDetail(java.io.File, org.opendarts.ui.export.model.Game, org.opendarts.ui.export.service.impl.BasicExportOption)
	 */
	@Override
	protected void writeGameDetail(File gameDetailFile, Game game,
			BasicExportOption option) {
		super.writeGameDetail(gameDetailFile, game, option);
		this.copyCss(gameDetailFile.getParentFile());
	}
	/**
	 * Copy css.
	 *
	 * @param folder the folder
	 */
	private void copyCss(File folder) {
		// Copy CSS
		try {
			URL res = this.getClass().getClassLoader().getResource("opendarts.css");
			File file = new File(folder, "opendarts.css");
			Files.copy(Resources.newInputStreamSupplier(res), file);
		} catch (IOException e) {
			LOG.error("Could not copy css file", e);
		}
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

}
