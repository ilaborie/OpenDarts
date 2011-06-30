package org.opendarts.prototype.ui.editor;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.opendarts.prototype.internal.model.Game501;
import org.opendarts.prototype.model.IPlayer;
import org.opendarts.prototype.ui.utils.OpenDartsFormsToolkit;

/**
 * The Class Game501Page.
 */
public class Game501Page extends FormPage implements IFormPage {

	/** The Constant SCORE_FORMAT. */
	private static final NumberFormat SCORE_FORMAT = DecimalFormat
			.getIntegerInstance();

	/** The toolkit. */
	private OpenDartsFormsToolkit toolkit;

	/** The game. */
	private Game501 game;

	/**
	 * Instantiates a new game page.
	 *
	 * @param gameEditor the game editor
	 */
	public Game501Page(Game501Editor gameEditor) {
		super(gameEditor, "main", "main");
		this.game = (Game501) gameEditor.getGame();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormPage#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		// form
		ScrolledForm form = managedForm.getForm();
		this.toolkit = OpenDartsFormsToolkit.getToolkit();
		form.setText(this.game.getName());
		this.toolkit.decorateFormHeading(form.getForm());

		// body
		Composite body = form.getBody();
		GridLayoutFactory.fillDefaults().margins(5, 5).numColumns(4)
				.equalWidth(true)
				.applyTo(body);

		// Top Panel
		//		Composite top = toolkit.createComposite(body);
		//		GridLayoutFactory.fillDefaults().numColumns(4).equalWidth(true).

		// First Player
		GridDataFactory playerData = GridDataFactory.fillDefaults()
				.grab(false, true).hint(150, SWT.DEFAULT);
		Composite cmpPlayerOne = this.createPlayerOneComposite(body);
		playerData.copy().applyTo(cmpPlayerOne);

		// ScoreSheet
		Composite cmpScoreSheet = this.createScoreSheetComposite(body);
		GridDataFactory.fillDefaults().grab(true, true).span(2, 1)
				.applyTo(cmpScoreSheet);

		// Second Player
		Composite cmpPlayerTwo = this.createPlayerTwoComposite(body);
		playerData.copy().applyTo(cmpPlayerTwo);

		// Score PlayerOne
		GridDataFactory scoreData = GridDataFactory.fillDefaults()
				.grab(true, false).span(2, 1).hint(SWT.DEFAULT, 200);
		Composite cmpPlayerOneScore = this
				.createPlayerOneScoreSheetComposite(body);
		scoreData.copy().applyTo(cmpPlayerOneScore);

		// Score PlayerOne 
		Composite cmpPlayerTwoScore = this
				.createPlayerTwoScoreSheetComposite(body);

		scoreData.copy().applyTo(cmpPlayerTwoScore);

		// Command
		//		ToolBarManager manager = (ToolBarManager) form.getToolBarManager();
		//		IMenuService menuService = (IMenuService) getSite().getService(
		//				IMenuService.class);
		//		menuService.populateContributionManager(manager, "popup:formsToolBar");
		//		manager.update(true);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormPage#setFocus()
	 */
	@Override
	public void setFocus() {
		// TODO
		super.setFocus();
	}

	/**
	 * Creates the score composite.
	 *
	 * @param parent the parent
	 * @return the composite
	 */
	private Composite createScoreSheetComposite(Composite parent) {
		Composite main = this.toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().applyTo(main);

		// Table
		Table table = this.toolkit.createTable(main, SWT.V_SCROLL);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(table);
		table.setHeaderVisible(true);

		TableViewer viewer = new TableViewer(table);

		TableViewerColumn column;
		int width = 90;
		int style = SWT.CENTER;
		// Columns
		column = this.buildColumn("Scored", viewer, width, style);
		column = this.buildColumn("*To Go", viewer, width, style);
		column = this.buildColumn("", viewer, width + 10, style);
		column = this.buildColumn("Scored", viewer, width, style);
		column = this.buildColumn("To Go", viewer, width, style);

		this.toolkit.paintBordersFor(main);
		return main;
	}

	/**
	 * Builds the column.
	 *
	 * @param title the title
	 * @param viewer the viewer
	 * @param width the width
	 * @param style the style
	 * @return the table viewer column
	 */
	private TableViewerColumn buildColumn(String title, TableViewer viewer,
			int width, int style) {
		TableViewerColumn column = new TableViewerColumn(viewer, style);
		column.getColumn().setText(title);
		column.getColumn().setWidth(width);
		column.getColumn().setResizable(false);
		return column;
	}

	/**
	 * Creates the player one composite.
	 *
	 * @param parent the parent
	 * @return the composite
	 */
	private Composite createPlayerOneComposite(Composite parent) {
		return this
				.createPlayerComposite(parent, this.game.getPlayers().get(0));
	}

	/**
	 * Creates the player two composite.
	 *
	 * @param parent the parent
	 * @return the composite
	 */
	private Composite createPlayerTwoComposite(Composite parent) {
		return this
				.createPlayerComposite(parent, this.game.getPlayers().get(1));
	}

	/**
	 * Creates the player composite.
	 *
	 * @param parent the parent
	 * @param iPlayer the i player
	 * @return the composite
	 */
	private Composite createPlayerComposite(Composite parent, IPlayer player) {
		Composite main = this.toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().applyTo(main);

		Section secPlayer = this.toolkit.createSection(main, Section.TITLE_BAR);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(secPlayer);
		secPlayer.setText(player.getName());

		Composite client = this.toolkit.createComposite(secPlayer, SWT.WRAP);
		GridLayoutFactory.fillDefaults().applyTo(client);
		
		GridDataFactory childData = GridDataFactory.fillDefaults().grab(true,
				false);
		// Session
		Composite cmpSession = this.createSessionComposite(client, player);
		childData.copy().applyTo(cmpSession);

		// Total
		Composite cmpTotal = this.createTotalComposite(client, player);
		childData.copy().applyTo(cmpTotal);

		// Best
		Composite cmpBest = this.createBestComposite(client, player);
		childData.copy().applyTo(cmpBest);

		// Average
		Composite cmpAvg = this.createAverageComposite(client, player);
		childData.copy().applyTo(cmpAvg);

		this.toolkit.paintBordersFor(client);
		secPlayer.setClient(client);

		return main;
	}

	/**
	 * Creates the session composite.
	 *
	 * @param parent the parent
	 * @param player the player
	 * @return the composite
	 */
	private Composite createSessionComposite(Composite parent, IPlayer player) {
		Composite main = this.toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().applyTo(main);

		// Section
		Section section = this.toolkit.createSection(main, Section.TWISTIE
				| Section.CLIENT_INDENT | Section.EXPANDED);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
		section.setText("Session");

		// Section body
		Composite client = this.toolkit.createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(client);

		Label lbl;
		GridDataFactory lblData = GridDataFactory.fillDefaults().grab(true,
				false);
		GridDataFactory valData = GridDataFactory.fillDefaults().align(SWT.END,
				SWT.CENTER);

		// Legs
		lbl = this.toolkit.createLabel(client, "Legs:");
		lblData.copy().applyTo(lbl);

		lbl = this.toolkit.createLabel(client, "-");
		valData.copy().applyTo(lbl);

		// Sets
		lbl = this.toolkit.createLabel(client, "Sets:");
		lblData.copy().applyTo(lbl);

		lbl = this.toolkit.createLabel(client, "-");
		valData.copy().applyTo(lbl);

		// End section
		this.toolkit.paintBordersFor(client);
		section.setClient(client);
		return main;
	}

	/**
	 * Creates the total composite.
	 *
	 * @param parent the parent
	 * @param player the player
	 * @return the composite
	 */
	private Composite createTotalComposite(Composite parent, IPlayer player) {
		Composite main = this.toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().applyTo(main);

		// Section
		Section section = this.toolkit.createSection(main, Section.TWISTIE
				| Section.CLIENT_INDENT | Section.EXPANDED);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
		section.setText("Totals");

		// Section body
		Composite client = this.toolkit.createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(client);

		Label lbl;
		GridDataFactory lblData = GridDataFactory.fillDefaults().grab(true,
				false);
		GridDataFactory valData = GridDataFactory.fillDefaults().align(SWT.END,
				SWT.CENTER);
		// 180
		lbl = this.toolkit.createLabel(client, "180's:");
		lblData.copy().applyTo(lbl);

		lbl = this.toolkit.createLabel(client, "-");
		valData.copy().applyTo(lbl);

		// +140
		lbl = this.toolkit.createLabel(client, "+140:");
		lblData.copy().applyTo(lbl);

		lbl = this.toolkit.createLabel(client, "-");
		valData.copy().applyTo(lbl);

		// +100
		lbl = this.toolkit.createLabel(client, "+100:");
		lblData.copy().applyTo(lbl);

		lbl = this.toolkit.createLabel(client, "-");
		valData.copy().applyTo(lbl);

		// End section
		this.toolkit.paintBordersFor(client);
		section.setClient(client);
		return main;
	}

	/**
	 * Creates the best composite.
	 *
	 * @param parent the parent
	 * @param player the player
	 * @return the composite
	 */
	private Composite createBestComposite(Composite parent, IPlayer player) {
		Composite main = this.toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().applyTo(main);

		// Section
		Section section = this.toolkit.createSection(main, Section.TWISTIE
				| Section.CLIENT_INDENT | Section.EXPANDED);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
		section.setText("Best");

		// Section body
		Composite client = this.toolkit.createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(client);

		Label lbl;
		GridDataFactory lblData = GridDataFactory.fillDefaults().grab(true,
				false);
		GridDataFactory valData = GridDataFactory.fillDefaults().align(SWT.END,
				SWT.CENTER);
		// High out
		lbl = this.toolkit.createLabel(client, "High Out:");
		lblData.copy().applyTo(lbl);

		lbl = this.toolkit.createLabel(client, "-");
		valData.copy().applyTo(lbl);

		// Best leg
		lbl = this.toolkit.createLabel(client, "Best Leg:");
		lblData.copy().applyTo(lbl);

		lbl = this.toolkit.createLabel(client, "-");
		valData.copy().applyTo(lbl);

		// End section
		this.toolkit.paintBordersFor(client);
		section.setClient(client);
		return main;
	}

	/**
	 * Creates the average composite.
	 *
	 * @param parent the parent
	 * @param player the player
	 * @return the composite
	 */
	private Composite createAverageComposite(Composite parent, IPlayer player) {
		Composite main = this.toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().applyTo(main);

		// Section
		Section section = this.toolkit.createSection(main, Section.TWISTIE
				| Section.CLIENT_INDENT | Section.EXPANDED);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
		section.setText("Average");

		// Section body
		Composite client = this.toolkit.createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(client);

		Label lbl;
		GridDataFactory lblData = GridDataFactory.fillDefaults().grab(true,
				false);
		GridDataFactory valData = GridDataFactory.fillDefaults().align(SWT.END,
				SWT.CENTER);
		// By darts
		lbl = this.toolkit.createLabel(client, "By dart:");
		lblData.copy().applyTo(lbl);

		lbl = this.toolkit.createLabel(client, "-");
		valData.copy().applyTo(lbl);

		// 3Darts
		lbl = this.toolkit.createLabel(client, "3 darts:");
		lblData.copy().applyTo(lbl);

		lbl = this.toolkit.createLabel(client, "-");
		valData.copy().applyTo(lbl);

		// nb darts
		lbl = this.toolkit.createLabel(client, "Nb darts:");
		lblData.copy().applyTo(lbl);

		lbl = this.toolkit.createLabel(client, "-");
		valData.copy().applyTo(lbl);

		// End section
		this.toolkit.paintBordersFor(client);
		section.setClient(client);
		return main;
	}

	/**
	 * Creates the player one score sheet composite.
	 *
	 * @param parent the parent
	 * @return the composite
	 */
	private Composite createPlayerOneScoreSheetComposite(Composite parent) {
		return this.createPlayerScoreSheetComposite(parent, this.game
				.getPlayers().get(0));
	}

	/**
	 * Creates the player two score sheet composite.
	 *
	 * @param parent the parent
	 * @return the composite
	 */
	private Composite createPlayerTwoScoreSheetComposite(Composite parent) {
		return this.createPlayerScoreSheetComposite(parent, this.game
				.getPlayers().get(1));
	}

	/**
	 * Creates the player score sheet composite.
	 *
	 * @param parent the parent
	 * @param iPlayer the i player
	 * @return the composite
	 */
	private Composite createPlayerScoreSheetComposite(Composite parent,
			IPlayer player) {
		Composite main = this.toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().applyTo(main);
		

		Text txtScore = this.toolkit.createText(main,
				this.getPlayerCurrentScore(player), SWT.READ_ONLY | SWT.CENTER);
		txtScore.setFont(toolkit.getScoreFont());
		GridDataFactory.fillDefaults().grab(true, true).applyTo(txtScore);
		
		return main;
	}

	/**
	 * Gets the player current score.
	 *
	 * @param player the player
	 * @return the player current score
	 */
	private String getPlayerCurrentScore(IPlayer player) {
		String result = "";
		Integer score = this.game.getScore(player);
		if (score == null) {
			result = "<NotStarted>";
		} else {
			result = SCORE_FORMAT.format(score);
		}
		return result;
	}

}
