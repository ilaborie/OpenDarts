package org.opendarts.prototype.ui.x01.editor;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.menus.IMenuService;
import org.opendarts.prototype.ProtoPlugin;
import org.opendarts.prototype.internal.model.game.x01.DummyX01Entry;
import org.opendarts.prototype.internal.model.game.x01.GameX01;
import org.opendarts.prototype.model.game.GameEvent;
import org.opendarts.prototype.model.game.IGameEntry;
import org.opendarts.prototype.model.game.IGameListener;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.ui.ISharedImages;
import org.opendarts.prototype.ui.utils.OpenDartsFormsToolkit;
import org.opendarts.prototype.ui.x01.label.ScoreLabelProvider;
import org.opendarts.prototype.ui.x01.label.ToGoLabelProvider;
import org.opendarts.prototype.ui.x01.label.TurnLabelProvider;
import org.opendarts.prototype.ui.x01.utils.PlayerStatusComposite;
import org.opendarts.prototype.ui.x01.utils.TextInputListener;
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
	private final Map<IPlayer, Text> playerScoreLeft;

	/** The player score input. */
	private final Map<IPlayer, Text> playerScoreInput;

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
		this.playerScoreLeft = new HashMap<IPlayer, Text>();
		this.playerScoreInput = new HashMap<IPlayer, Text>();
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

		// First Player Status
		GridDataFactory playerData = GridDataFactory.fillDefaults()
				.grab(false, true).hint(150, SWT.DEFAULT);
		Composite cmpPlayerOne = this.createPlayerComposite(body,
				this.game.getFirstPlayer());
		;
		playerData.copy().applyTo(cmpPlayerOne);

		// Score
		Composite cmpScore = this.createScoreTableComposite(body);
		GridDataFactory.fillDefaults().grab(true, true).span(2, 1)
				.applyTo(cmpScore);

		// Second Player Status
		Composite cmpPlayerTwo = this.createPlayerComposite(body,
				this.game.getSecondPlayer());
		playerData.copy().applyTo(cmpPlayerTwo);

		// PlayerOne Left Score
		GridDataFactory scoreData = GridDataFactory.fillDefaults()
				.grab(true, false).span(2, 1).hint(SWT.DEFAULT, 200);
		Composite cmpPlayerOneLeftScore = this.createPlayerScoreLeftComposite(
				body, this.game.getFirstPlayer());
		scoreData.copy().applyTo(cmpPlayerOneLeftScore);

		// PlayerTwo Left Score
		Composite cmpPlayerTwoLeftScore = this.createPlayerScoreLeftComposite(
				body, this.game.getSecondPlayer());

		scoreData.copy().applyTo(cmpPlayerTwoLeftScore);

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
	private Composite createScoreTableComposite(Composite parent) {
		Composite main = this.toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(main);

		// Table
		Table table = this.toolkit.createTable(main, SWT.V_SCROLL);
		GridDataFactory.fillDefaults().span(2, 4).grab(true, true)
				.applyTo(table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		this.scoreViewer = new TableViewer(table);
		this.scoreViewer.setContentProvider(new ArrayContentProvider());

		this.addColumns();
		this.toolkit.paintBordersFor(main);

		IPlayer player;
		// Score input
		player = this.game.getFirstPlayer();
		this.createInputScoreText(main, player);

		player = this.game.getSecondPlayer();
		this.createInputScoreText(main, player);

		return main;
	}

	/**
	 * Creates the input score text.
	 *
	 * @param main the main
	 * @param player the player
	 */
	private void createInputScoreText(Composite main, IPlayer player) {
		Text inputScoreText = this.toolkit.createText(main, "", SWT.CENTER);
		inputScoreText.setFont(toolkit
				.getFont(OpenDartsFormsToolkit.FONT_SCORE_INPUT));
		inputScoreText.setEnabled(false);
		this.playerScoreInput.put(player, inputScoreText);

		// layout
		int indent = FieldDecorationRegistry.getDefault()
				.getMaximumDecorationWidth() + 2;
		GridDataFactory.fillDefaults().grab(true, false)
				.indent(indent, SWT.DEFAULT).hint(SWT.DEFAULT, 80)
				.applyTo(inputScoreText);

		// decoration
		ControlDecoration dec = new ControlDecoration(inputScoreText, SWT.TOP
				| SWT.LEFT);

		// listener
		TextInputListener listener = new TextInputListener(this.getSite()
				.getShell(), inputScoreText, this.game, player, dec);
		inputScoreText.addKeyListener(listener);
	}

	/**
	 * Adds the columns.
	 */
	private void addColumns() {
		int turnWidth = 70;

		List<IPlayer> players = this.game.getPlayers();
		if (players.size() == 2) {
			this.createPlayerColumns(this.game.getFirstPlayer());
			this.toolkit.createTableColumn("", this.scoreViewer, turnWidth,
					SWT.CENTER, new TurnLabelProvider());
			this.createPlayerColumns(this.game.getSecondPlayer());
		} else {
			this.toolkit.createTableColumn("", this.scoreViewer, turnWidth,
					SWT.CENTER, new TurnLabelProvider());
			for (IPlayer player : players) {
				this.createPlayerColumns(player);
			}
		}
	}

	/**
	 * Creates the player columns.
	 *
	 * @param player the player
	 */
	private void createPlayerColumns(IPlayer player) {
		Shell shell = this.getSite().getShell();
		int width = 100;
		int style = SWT.CENTER;
		TableViewerColumn column;
		column = this.toolkit.createTableColumn("Scored", this.scoreViewer,
				width, style, new ScoreLabelProvider(player),
				new ScoreX01EditingSupport(shell, this.game, player,
						this.scoreViewer));
		this.playerColumn.put(player, column);

		this.toolkit.createTableColumn("To Go", this.scoreViewer, width, style,
				new ToGoLabelProvider(player));
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

		PlayerStatusComposite cmpStatus = new PlayerStatusComposite(client,
				player, this.game);
		GridDataFactory.fillDefaults().grab(true, false)
				.applyTo(cmpStatus.getControl());

		this.toolkit.paintBordersFor(client);
		secPlayer.setClient(client);

		return main;
	}

	/**
	 * Creates the player score sheet composite.
	 *
	 * @param parent the parent
	 * @param iPlayer the i player
	 * @return the composite
	 */
	private Composite createPlayerScoreLeftComposite(Composite parent,
			IPlayer player) {
		Composite main = this.toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().applyTo(main);

		Text txtScore = this.toolkit.createText(main,
				this.getPlayerCurrentScore(player), SWT.READ_ONLY | SWT.CENTER);
		txtScore.setFont(toolkit.getFont(OpenDartsFormsToolkit.FONT_SCORE_LEFT));
		GridDataFactory.fillDefaults().grab(true, true).applyTo(txtScore);

		this.playerScoreLeft.put(player, txtScore);

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
			result = "";
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
			IGameEntry entry = event.getEntry();
			switch (event.getType()) {
				case GAME_INITIALIZED:
					for (IPlayer p : this.game.getPlayers()) {
						txt = this.playerScoreLeft.get(p);
						txt.setText(this.getPlayerCurrentScore(p));
					}
					this.scoreViewer.setInput(this.game.getGameEntries());
					// add dummy entry
					this.scoreViewer.add(new DummyX01Entry(this.game));
					break;
				case GAME_ENTRY_CREATED:
					this.scoreViewer.add(entry);
					this.scoreViewer.reveal(entry);
					break;
				case GAME_ENTRY_UPDATED:
					this.scoreViewer.update(entry, null);
					player = event.getPlayer();
					if (player != null) {
						txt = this.playerScoreLeft.get(player);
						txt.setText(this.getPlayerCurrentScore(player));
					}
					break;
				case NEW_CURRENT_PLAYER:
					player = event.getPlayer();
					// mark column
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
					// enable/disable inputs & focus
					Text playerInputTxt = this.playerScoreInput.get(player);
					for (Text inputTxt : this.playerScoreInput.values()) {
						if (playerInputTxt.equals(inputTxt)) {
							inputTxt.setEnabled(true);
							inputTxt.setBackground(OpenDartsFormsToolkit
									.getToolkit()
									.getColors()
									.getColor(
											OpenDartsFormsToolkit.COLOR_ACTIVE));
							inputTxt.setFocus();
						} else {
							inputTxt.setEnabled(false);
							inputTxt.setText("");
							inputTxt.setBackground(OpenDartsFormsToolkit
									.getToolkit()
									.getColors()
									.getColor(
											OpenDartsFormsToolkit.COLOR_INACTIVE));
						}
					}
					break;
				case GAME_FINISHED:
					player = event.getPlayer();
					column = this.playerColumn.get(player);
					column.getColumn().setImage(
							ProtoPlugin.getImage(ISharedImages.IMG_TICK_DECO));
					// remove edition
					for (Text inputTxt : this.playerScoreInput.values()) {
						inputTxt.setEnabled(false);
					}
					for (TableViewerColumn col : this.playerColumn.values()) {
						col.setEditingSupport(null);
					}

				case GAME_CANCELED:
					// TODO cleanup
			}
		}
	}

}
