package org.opendarts.ui.export.pdf.x01.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.player.func.PlayerToStringFunction;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.core.x01.model.GameX01;
import org.opendarts.ui.export.composite.AbstractExportOptionComposite;
import org.opendarts.ui.export.composite.BasicExportOptionComposite;
import org.opendarts.ui.export.model.Game;
import org.opendarts.ui.export.model.Session;
import org.opendarts.ui.export.model.Set;
import org.opendarts.ui.export.model.Stats;
import org.opendarts.ui.export.model.StatsEntry;
import org.opendarts.ui.export.model.StatsValue;
import org.opendarts.ui.export.service.impl.AbstractExportX01Service;
import org.opendarts.ui.export.service.impl.BasicExportOption;
import org.opendarts.ui.export.x01.model.GameEntry;
import org.opendarts.ui.export.x01.model.SessionX01;
import org.opendarts.ui.export.x01.model.SetX01;
import org.opendarts.ui.stats.model.IChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * The Class PdfExportX01Service.
 */
public class PdfExportX01Service extends
		AbstractExportX01Service<BasicExportOption> {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(PdfExportX01Service.class);

	private static Font catFont = new Font(Font.FontFamily.HELVETICA, 32,
			Font.BOLD);
	private static Font secFont_1 = new Font(Font.FontFamily.HELVETICA, 16,
			Font.BOLD);
	private static Font secFont_2 = new Font(Font.FontFamily.HELVETICA, 14,
			Font.BOLD);
	private static Font secFont_3 = new Font(Font.FontFamily.HELVETICA, 12,
			Font.BOLD);

	/**
	 * Instantiates a new pdf export x01 service.
	 */
	public PdfExportX01Service() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.IExportUiService#getName()
	 */
	@Override
	public String getName() {
		return "PDF";
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#getFileExtension()
	 */
	@Override
	protected String getFileExtension() {
		return ".pdf";
	}
	
	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#createSession(org.opendarts.core.model.session.ISession, java.util.List)
	 */
	@Override
	protected Session createSession(ISession ses, List<IStatsService> stats) {
		return new SessionX01(ses, stats);
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
		return (game instanceof GameX01);
	}

	@Override
	public void export(ISession ses, File file, BasicExportOption option) {

		List<IStatsService> stats = this.getStatsProvider()
				.getSessionStats(ses);
		Session session = createSession(ses, stats);
		// Fix images dimension
		option.setChartImageHeight(320);
		option.setChartImageWidth(480);

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

		if (option.isZip()) {
			File zipFile = new File(file, session.getRootName() + ".zip");
			this.zip(zipFile, sessionFile);
		}
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.impl.AbstractExportX01Service#writeSessionDetail(java.io.File, org.opendarts.ui.export.model.Session, org.opendarts.ui.export.service.impl.BasicExportOption)
	 */
	@Override
	protected void writeSessionDetail(File sessionDetailFile, Session session,
			BasicExportOption option) {
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(
					sessionDetailFile));

			document.open();
			this.addMetaData(document, session, option);
			this.addTitlePage(document, session, option);
			this.addSessionContent(document, session, option,
					sessionDetailFile.getParentFile());
			document.close();

		} catch (Exception e) {
			LOG.error("Could no produce session PDF", e);
		}
	}

	/**
	 * Adds the session meta data.
	 *
	 * @param document the document
	 * @param session the session
	 * @param option the option
	 */
	protected void addMetaData(Document document, Session session,
			BasicExportOption option) {
		document.addTitle(session.getName());
		document.addSubject("OpenDarts Export for " + session.getName());
		document.addAuthor("OpenDarts");
		document.addCreationDate();
		document.addCreator("OpenDarts");
	}

	/**
	 * Adds the session title page.
	 *
	 * @param document the document
	 * @param session the session
	 * @param option the option
	 * @throws DocumentException 
	 */
	protected void addTitlePage(Document document, Session session,
			BasicExportOption option) throws DocumentException {
		Paragraph preface = new Paragraph();
		// Lets write a big header
		preface.add(new Paragraph(session.getName(), catFont));
		// Will create: Report generated by: _name, _date
		preface.add(new Paragraph("Report generated by OpenDarts, "
				+ new Date()));
		document.add(preface);
	}

	/**
	 * Adds the session content.
	 *
	 * @param document the document
	 * @param session the session
	 * @param option the option
	 * @param rootFolder 
	 * @throws DocumentException 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	protected void addSessionContent(Document document, Session session,
			BasicExportOption option, File rootFolder)
			throws DocumentException, MalformedURLException, IOException {
		int index = 1;
		// Detail session
		Anchor anchor = new Anchor("Session Detail", catFont);
		anchor.setName("Session Detail");
		Chapter chapter = new Chapter(new Paragraph(anchor), index++);

		this.createSessionStatus(chapter, session, option);
		this.createSessionStatistics(chapter, session, option);
		this.createSessionDetail(chapter, session, option);
		if (option.isExportChart()) {
			this.createSessionCharts(chapter, session, option, rootFolder);
		}
		document.add(chapter);

		Anchor setAnchor;
		Chapter setChapter;
		for (Set set : session.getSets()) {
			setAnchor = new Anchor("Set " + set.getIndex(), catFont);
			setChapter = new Chapter(new Paragraph(setAnchor), index++);
			// fill 
			this.addSetContent(setChapter, set, option, rootFolder);
			document.add(setChapter);
		}
	}

	/**
	 * Creates the session status.
	 * @param index 
	 *
	 * @param document the document
	 * @param session the session
	 * @param option the option
	 */
	protected void createSessionStatus(Chapter chapter, Session session,
			BasicExportOption option) {
		Section section = chapter.addSection(new Paragraph("Status", secFont_1));
		section.add(new Paragraph(" "));

		Paragraph para;
		// Started
		para = new Paragraph("Started at: " + session.getStart());
		section.add(para);

		// End
		if (session.getEnd() != null) {
			para = new Paragraph("Ended at: " + session.getEnd());
			section.add(para);
		}

		// Winner
		para = new Paragraph("Winner: " + session.getWinner());
		section.add(para);
	}

	/**
	 * Creates the session statistics.
	 * @param index 
	 *
	 * @param session the session
	 * @param option the option
	 * @return the element
	 */
	protected void createSessionStatistics(Chapter chapter, Session session,
			BasicExportOption option) {
		Section section = chapter.addSection(new Paragraph("Statistics",
				secFont_1));
		section.add(new Paragraph(" "));

		List<IPlayer> players;
		PdfPTable table;
		String sVal;
		for (Stats<ISession> stat : session.getStats()) {
			players = stat.getPlayers();
			table = new PdfPTable(players.size() + 1);

			// Header
			table.addCell("");
			PdfPCell cell;
			PlayerToStringFunction func = new PlayerToStringFunction();
			for (IPlayer player : players) {
				cell = new PdfPCell(new Phrase(func.apply(player)));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}
			table.setHeaderRows(1);

			// entries
			for (StatsEntry<ISession> entry : stat.getEntries()) {
				cell = new PdfPCell(new Phrase(entry.getLabel()));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				for (StatsValue val : entry.getPlayersValues()) {
					if (val != null) {
						sVal = val.toString();
					} else {
						sVal = "-";
					}
					cell = new PdfPCell(new Phrase(sVal));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}
			}
			section.add(table);
		}
	}

	/**
	 * Creates the session charts.
	 * @param index 
	 *
	 * @param session the session
	 * @param option the option
	 * @param rootFolder 
	 * @return the element
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws BadElementException 
	 */
	protected void createSessionCharts(Chapter chapter, Session session,
			BasicExportOption option, File rootFolder)
			throws BadElementException, MalformedURLException, IOException {
		Section section = chapter.addSection(new Paragraph("Charts", secFont_1));
		section.add(new Paragraph(" "));

		Image img;
		File file;
		switch (option.getChartImageType()) {
			case JPEG:
				for (IChart chart : session.getCharts()) {
					file = this.exportChartAsJpeg(chart, rootFolder, option);
					img = Image.getInstance(file.getAbsolutePath());
					section.add(img);
					file.delete();
				}
				break;
			case PNG:
				for (IChart chart : session.getCharts()) {
					file = this.exportChartAsPng(chart, rootFolder, option);
					img = Image.getInstance(file.getAbsolutePath());
					section.add(img);
					file.delete();
				}
				break;
			default:
				break;
		}
	}

	/**
	 * Creates the session detail.
	 * @param index 
	 *
	 * @param session the session
	 * @param option the option
	 * @return the element
	 */
	protected void createSessionDetail(Chapter chapter, Session session,
			BasicExportOption option) {
		Section section = chapter.addSection(new Paragraph("Detail", secFont_1));
		section.add(new Paragraph(" "));

		PdfPTable table = new PdfPTable(4);
		PdfPCell cell;
		// Header
		for (String header : Arrays
				.asList("Set", "Players", "Winner", "Result")) {
			cell = new PdfPCell(new Phrase(header));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
		}
		table.setHeaderRows(1);

		// Entries
		for (Set set : session.getSets()) {
			for (String header : Arrays.asList(set.getIndex(),
					set.getPlayers(), set.getWinner(), set.getResult())) {
				cell = new PdfPCell(new Phrase(header));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}
		}
		section.add(table);
	}

	/**
	 * Adds the set content.
	 *
	 * @param chapter the chapter
	 * @param set the set
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws BadElementException 
	 */
	protected void addSetContent(Chapter chapter, Set set,
			BasicExportOption option, File rootFolder)
			throws BadElementException, MalformedURLException, IOException {
		// Set Detail
		Section section = chapter.addSection(new Paragraph("Detail", secFont_1));
		section.add(new Paragraph(" "));
		section.add(new Paragraph(set.getDescription()));

		this.createSetStatus(section, set, option);
		this.createSetStatistics(section, set, option);
		this.createSetDetail(section, set, option);
		if (option.isExportChart()) {
			this.createSetCharts(section, set, option, rootFolder);
		}

		section = chapter.addSection(new Paragraph("Games", secFont_1));
		// Set Games
		for (Game game : set.getGames()) {
			this.addGameContent(section, game, option, rootFolder);
		}
	}

	/**
	 * Creates the set status.
	 *
	 * @param section the section
	 * @param set the set
	 * @param option the option
	 */
	protected void createSetStatus(Section section, Set set,
			BasicExportOption option) {
		Section subSection = section
				.addSection(new Paragraph("Status", secFont_2));
		section.add(new Paragraph(" "));

		// Started
		subSection.add(new Paragraph("Started at: " + set.getStart()));
		// End
		if (set.getEnd() != null) {
			subSection.add(new Paragraph("Ended at: " + set.getEnd()));
		}
		// Winner
		subSection.add(new Paragraph("Winner: " + set.getWinner()));

		if (set instanceof SetX01) {
			SetX01 setX01 = (SetX01) set;

			subSection.add(new Paragraph("Starting Score: "
					+ setX01.getStartingScore()));
			subSection.add(new Paragraph("Nb. Games to Win: "
					+ setX01.getNbGameToWin()));
		}
	}

	/**
	 * Creates the set statistics.
	 *
	 * @param parentSection the section
	 * @param set the set
	 * @param option the option
	 */
	protected void createSetStatistics(Section parentSection, Set set,
			BasicExportOption option) {
		Section section = parentSection.addSection(new Paragraph("Statistics",
				secFont_2));
		section.add(new Paragraph(" "));

		List<IPlayer> players;
		PdfPTable table;
		String sVal;
		for (Stats<ISet> stat : set.getStats()) {
			players = stat.getPlayers();
			table = new PdfPTable(players.size() + 1);

			// Header
			table.addCell("");
			PdfPCell cell;
			PlayerToStringFunction func = new PlayerToStringFunction();
			for (IPlayer player : players) {
				cell = new PdfPCell(new Phrase(func.apply(player)));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}
			table.setHeaderRows(1);

			// entries
			for (StatsEntry<ISet> entry : stat.getEntries()) {
				cell = new PdfPCell(new Phrase(entry.getLabel()));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				for (StatsValue val : entry.getPlayersValues()) {
					if (val != null) {
						sVal = val.toString();
					} else {
						sVal = "-";
					}
					cell = new PdfPCell(new Phrase(sVal));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}
			}
			section.add(table);
		}
	}

	/**
	 * Creates the set charts.
	 *
	 * @param section the section
	 * @param set the set
	 * @param option the option
	 * @param rootFolder the root folder
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws BadElementException 
	 */
	protected void createSetCharts(Section parentSection, Set set,
			BasicExportOption option, File rootFolder)
			throws BadElementException, MalformedURLException, IOException {
		Section section = parentSection.addSection(new Paragraph("Charts",
				secFont_2));
		section.add(new Paragraph(" "));

		Image img;
		File file;
		switch (option.getChartImageType()) {
			case JPEG:
				for (IChart chart : set.getCharts()) {
					file = this.exportChartAsJpeg(chart, rootFolder, option);
					img = Image.getInstance(file.getAbsolutePath());
					section.add(img);
					file.delete();
				}
				break;
			case PNG:
				for (IChart chart : set.getCharts()) {
					file = this.exportChartAsPng(chart, rootFolder, option);
					img = Image.getInstance(file.getAbsolutePath());
					section.add(img);
					file.delete();
				}
				break;
			default:
				break;
		}
	}

	/**
	 * Creates the set detail.
	 *
	 * @param parentSection the parent section
	 * @param set the set
	 * @param option the option
	 */
	protected void createSetDetail(Section parentSection, Set set,
			BasicExportOption option) {
		Section section = parentSection.addSection(new Paragraph("Detail",
				secFont_2));
		section.add(new Paragraph(" "));

		PdfPTable table = new PdfPTable(4);
		PdfPCell cell;
		// Header
		for (String header : Arrays.asList("Game", "Players", "Winner",
				"Result")) {
			cell = new PdfPCell(new Phrase(header));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
		}
		table.setHeaderRows(1);

		// Entries
		for (Game game : set.getGames()) {
			for (String header : Arrays.asList(game.getIndex(),
					game.getPlayers(), game.getWinner(), game.getResult())) {
				cell = new PdfPCell(new Phrase(header));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}
		}
		section.add(table);
	}

	/**
	 * Adds the game content.
	 *
	 * @param parentSection the parent section
	 * @param game the game
	 * @param option the option
	 * @param rootFolder the root folder
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws BadElementException 
	 */
	protected void addGameContent(Section parentSection, Game game,
			BasicExportOption option, File rootFolder)
			throws BadElementException, MalformedURLException, IOException {
		Section section = parentSection.addSection(new Paragraph("Game "
				+ game.getIndex(), secFont_2));
		section.add(new Paragraph(" "));

		// Set Detail
		section.add(new Paragraph(game.getDescription()));

		this.createGameStatus(section, game, option);
		this.createGameStatistics(section, game, option);
		if (option.isExportChart()) {
			this.createGameCharts(section, game, option, rootFolder);
		}
		if (game instanceof org.opendarts.ui.export.x01.model.GameX01) {
			this.createGameDetail(section,
					(org.opendarts.ui.export.x01.model.GameX01) game, option);
		}
	}

	/**
	 * Creates the game status.
	 *
	 * @param section the section
	 * @param game the game
	 * @param option the option
	 */
	protected void createGameStatus(Section parentSection, Game game,
			BasicExportOption option) {
		Section section = parentSection.addSection(new Paragraph("Status",
				secFont_3));
		section.add(new Paragraph(" "));

		// Started
		section.add(new Paragraph("Started at: " + game.getStart()));
		// End
		if (game.getEnd() != null) {
			section.add(new Paragraph("Ended at: " + game.getEnd()));
		}
		// Winner
		section.add(new Paragraph("Winner: " + game.getWinner()));

		if (game instanceof org.opendarts.ui.export.x01.model.GameX01) {
			org.opendarts.ui.export.x01.model.GameX01 gameX01 = (org.opendarts.ui.export.x01.model.GameX01) game;

			section.add(new Paragraph("Starting Score: "
					+ gameX01.getStartingScore()));
			section.add(new Paragraph("Nb. Games to Win: "
					+ gameX01.getNbGameToWin()));
		}
	}

	/**
	 * Creates the game statistics.
	 *
	 * @param section the section
	 * @param game the game
	 * @param option the option
	 */
	protected void createGameStatistics(Section parentSection, Game game,
			BasicExportOption option) {
		Section section = parentSection.addSection(new Paragraph("Statistics",
				secFont_3));
		section.add(new Paragraph(" "));

		List<IPlayer> players;
		PdfPTable table;
		String sVal;
		for (Stats<IGame> stat : game.getStats()) {
			players = stat.getPlayers();
			table = new PdfPTable(players.size() + 1);

			// Header
			table.addCell("");
			PdfPCell cell;
			PlayerToStringFunction func = new PlayerToStringFunction();
			for (IPlayer player : players) {
				cell = new PdfPCell(new Phrase(func.apply(player)));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}
			table.setHeaderRows(1);

			// entries
			for (StatsEntry<IGame> entry : stat.getEntries()) {
				cell = new PdfPCell(new Phrase(entry.getLabel()));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				for (StatsValue val : entry.getPlayersValues()) {
					if (val != null) {
						sVal = val.toString();
					} else {
						sVal = "-";
					}
					cell = new PdfPCell(new Phrase(sVal));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}
			}
			section.add(table);
		}

	}

	/**
	 * Creates the game charts.
	 *
	 * @param section the section
	 * @param game the game
	 * @param option the option
	 * @param rootFolder the root folder
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws BadElementException 
	 */
	protected void createGameCharts(Section parentSection, Game game,
			BasicExportOption option, File rootFolder)
			throws BadElementException, MalformedURLException, IOException {
		Section section = parentSection.addSection(new Paragraph("Charts",
				secFont_3));
		section.add(new Paragraph(" "));

		Image img;
		File file;
		switch (option.getChartImageType()) {
			case JPEG:
				for (IChart chart : game.getCharts()) {
					file = this.exportChartAsJpeg(chart, rootFolder, option);
					img = Image.getInstance(file.getAbsolutePath());
					section.add(img);
					file.delete();
				}
				break;
			case PNG:
				for (IChart chart : game.getCharts()) {
					file = this.exportChartAsPng(chart, rootFolder, option);
					img = Image.getInstance(file.getAbsolutePath());
					section.add(img);
					file.delete();
				}
				break;
			default:
				break;
		}
	}

	/**
	 * Creates the game detail.
	 *
	 * @param section the section
	 * @param game the game
	 * @param option the option
	 */
	protected void createGameDetail(Section parentSection,
			org.opendarts.ui.export.x01.model.GameX01 game,
			BasicExportOption option) {
		Section section = parentSection.addSection(new Paragraph("Detail",
				secFont_3));
		section.add(new Paragraph(" "));

		PdfPTable table = new PdfPTable(1+game.getPlayerList().size());
		PdfPCell cell;
		// Header
		table.addCell("");
		PlayerToStringFunction func = new PlayerToStringFunction();
		for (IPlayer player : game.getPlayerList()) {
			cell = new PdfPCell(new Phrase(func.apply(player)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
		}
		table.setHeaderRows(1);

		// Entries
		for (GameEntry entry : game.getEntries()) {
			cell = new PdfPCell(new Phrase(entry.getLabel()));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			for (Integer score : entry.getScores()) {
				cell = new PdfPCell(new Phrase(String.valueOf(score)));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}
		}
		section.add(table);
	}
}
