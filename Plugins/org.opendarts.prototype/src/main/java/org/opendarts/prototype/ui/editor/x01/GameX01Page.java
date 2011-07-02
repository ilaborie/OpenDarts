package org.opendarts.prototype.ui.editor.x01;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
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
import org.eclipse.ui.menus.IMenuService;
import org.opendarts.prototype.ProtoPlugin;
import org.opendarts.prototype.internal.model.game.x01.GameX01;
import org.opendarts.prototype.model.game.GameEvent;
import org.opendarts.prototype.model.game.IGameListener;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.ui.ISharedImages;
import org.opendarts.prototype.ui.utils.OpenDartsFormsToolkit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class GameX01Page.
 */
public class GameX01Page extends FormPage implements IFormPage, IGameListener {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(GameX01Page.class);

	/** The Constant SCORE_FORMAT. */
	private static final NumberFormat SCORE_FORMAT = DecimalFormat
			.getIntegerInstance();

	/** The toolkit. */
	private OpenDartsFormsToolkit toolkit;

	/** The game. */
	private GameX01 game;

	/** The player score. */
	private final Map<IPlayer, Text> playerScore;

	/** The player score. */
	private final Map<IPlayer, TableViewerColumn> playerColumn;

	/** The score viewer. */
	private TableViewer scoreViewer;

	/**
	 * Instantiates a new game page.
	 *
	 * @param gameEditor the game editor
	 */
	public GameX01Page(GameX01Editor gameEditor) {
		super(gameEditor, "main", "main");
		this.game = (GameX01) gameEditor.getGame();
		this.playerScore = new HashMap<IPlayer, Text>();
		this.playerColumn = new HashMap<IPlayer, TableViewerColumn>();
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
				.equalWidth(true).applyTo(body);

		// First Player
		GridDataFactory playerData = GridDataFactory.fillDefaults()
				.grab(false, true).hint(150, SWT.DEFAULT);
		Composite cmpPlayerOne = this.createPlayerComposite(body,
				this.game.getFirstPlayer());
		;
		playerData.copy().applyTo(cmpPlayerOne);

		// ScoreSheet
		Composite cmpScoreSheet = this.createScoreSheetComposite(body);
		GridDataFactory.fillDefaults().grab(true, true).span(2, 1)
				.applyTo(cmpScoreSheet);

		// Second Player
		Composite cmpPlayerTwo = this.createPlayerComposite(body,
				this.game.getSecondPlayer());
		playerData.copy().applyTo(cmpPlayerTwo);

		// Score PlayerOne
		GridDataFactory scoreData = GridDataFactory.fillDefaults()
				.grab(true, false).span(2, 1).hint(SWT.DEFAULT, 200);
		Composite cmpPlayerOneScore = this.createPlayerScoreSheetComposite(
				body, this.game.getFirstPlayer());
		scoreData.copy().applyTo(cmpPlayerOneScore);

		// Score PlayerOne 
		Composite cmpPlayerTwoScore = this.createPlayerScoreSheetComposite(
				body, this.game.getSecondPlayer());

		scoreData.copy().applyTo(cmpPlayerTwoScore);

		// Toolbar
		ToolBarManager manager = (ToolBarManager) form.getToolBarManager();
		IMenuService menuService = (IMenuService) getSite().getService(
				IMenuService.class);
		menuService.populateContributionManager(manager,
				"toolbar:openwis.editor.game.toolbar");
		manager.update(true);

		// Register listener
		this.game.addListener(this);
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
		table.setLinesVisible(true);

		this.scoreViewer = new TableViewer(table);
		this.scoreViewer.setContentProvider(new ArrayContentProvider());

		int width = 90;
		int style = SWT.CENTER;
		TableViewerColumn column;
		// Columns
		// TODO editors
		IPlayer player = this.game.getFirstPlayer();
		this.toolkit.createTableColumn("Scored", this.scoreViewer, width,
				style, new ScoreLabelProvider(player));
		column = this.toolkit.createTableColumn("To Go", this.scoreViewer,
				width, style, new GoToLabelProvider(player));
		this.playerColumn.put(player, column);

		this.toolkit.createTableColumn("", this.scoreViewer, width + 10, style,
				new TurnLabelProvider());

		player = this.game.getSecondPlayer();
		this.toolkit.createTableColumn("Scored", this.scoreViewer, width,
				style, new ScoreLabelProvider(player));
		column = this.toolkit.createTableColumn("To Go", this.scoreViewer,
				width, style, new GoToLabelProvider(player));
		this.playerColumn.put(player, column);

		this.toolkit.paintBordersFor(main);
		return main;
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

		Section secPlayer = this.toolkit.createSection(main, Section.TITLE_BAR
				| Section.CLIENT_INDENT);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(secPlayer);
		secPlayer.setText(player.getName());

		Composite client = this.toolkit.createComposite(secPlayer, SWT.WRAP);
		GridLayoutFactory.fillDefaults().margins(2, 2).applyTo(client);

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
				| Section.SHORT_TITLE_BAR | Section.CLIENT_INDENT
				| Section.EXPANDED);
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
				| Section.SHORT_TITLE_BAR | Section.CLIENT_INDENT
				| Section.EXPANDED);
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
				| Section.SHORT_TITLE_BAR | Section.CLIENT_INDENT
				| Section.EXPANDED);
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
				| Section.SHORT_TITLE_BAR | Section.CLIENT_INDENT
				| Section.EXPANDED);
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

		this.playerScore.put(player, txtScore);

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

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.game.IGameListener#notifyGameEvent(org.opendarts.prototype.model.game.GameEvent)
	 */
	@Override
	public void notifyGameEvent(GameEvent event) {
		if (event.getGame().equals(this.game)) {
			LOG.trace("New Game Event: {}", event);
			Text txt;
			IPlayer player;
			TableViewerColumn column;
			switch (event.getType()) {
				case GAME_INITIALIZED:
					for (IPlayer p : this.game.getPlayers()) {
						txt = this.playerScore.get(p);
						txt.setText(this.getPlayerCurrentScore(p));
					}
					this.scoreViewer.setInput(this.game.getGameEntries());
					// add dummy entry
					this.scoreViewer.add(new DummyX01Entry(this.game));
					break;
				case GAME_ENTRY_CREATED:
					this.scoreViewer.add(event.getEntry());
					break;
				case GAME_ENTRY_UPDATED:
					this.scoreViewer.update(event.getEntry(), null);
					player = event.getPlayer();
					if (player != null) {
						txt = this.playerScore.get(player);
						txt.setText(this.getPlayerCurrentScore(player));
					}
					break;
				case NEW_CURRENT_PLAYER:
					player = event.getPlayer();
					column = this.playerColumn.get(player);
					TableViewerColumn c;
					for (IPlayer p : this.game.getPlayers()) {
						c = this.playerColumn.get(p);
						if (c.equals(column)) {
							c.getColumn()
									.setImage(
											ProtoPlugin
													.getImage(ISharedImages.IMG_ARROW_DECO));
						} else {
							c.getColumn().setImage(null);
						}
					}
					break;
				case GAME_FINISHED:
					player = event.getPlayer();
					column = this.playerColumn.get(player);
					column.getColumn().setImage(
							ProtoPlugin.getImage(ISharedImages.IMG_TICK_DECO));
					// TODO , lock  ...
				case GAME_CANCELED:
					// TODO cleanup
			}
		}
	}

}
