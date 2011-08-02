package org.opendarts.ui.x01.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISessionListener;
import org.opendarts.core.model.session.ISetListener;
import org.opendarts.core.model.session.SessionEvent;
import org.opendarts.core.model.session.SetEvent;
import org.opendarts.core.model.session.impl.GameSet;
import org.opendarts.core.stats.model.IStatValue;
import org.opendarts.core.stats.model.IStats;
import org.opendarts.core.stats.model.IStatsEntry;
import org.opendarts.core.stats.model.impl.GameStats;
import org.opendarts.core.stats.model.impl.SetStats;
import org.opendarts.core.stats.service.IStatsListener;
import org.opendarts.core.stats.service.IStatsProvider;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.core.x01.model.GameX01;
import org.opendarts.core.x01.service.StatsX01Service;
import org.opendarts.ui.utils.OpenDartsFormsToolkit;
import org.opendarts.ui.x01.X01UiPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class PlayerStatusComposite.
 */
@SuppressWarnings("rawtypes")
public class PlayerStatusComposite implements ISetListener, ISessionListener,
		IStatsListener {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(PlayerStatusComposite.class);

	/** The player. */
	private final IPlayer player;

	/** The game. */
	private final GameX01 game;

	/** The toolkit. */
	private final OpenDartsFormsToolkit toolkit;

	/** The main. */
	private final Composite main;

	/** The set. */
	private final GameSet set;

	/** The session. */
	private final ISession session;

	/** The lbl legs. */
	private Label lblLegs;

	/** The lbl sets. */
	private Label lblSets;

	/** The stats label. */
	private final Map<String, Label> statsLabel;

	/** The stats service. */
	private final List<IStatsService> statsServices;

	/**
	 * Instantiates a new player status composite.
	 *
	 * @param parent the parent
	 * @param player the player
	 * @param game the game
	 */
	@SuppressWarnings("unchecked")
	public PlayerStatusComposite(Composite parent, IPlayer player, GameX01 game) {
		super();
		this.player = player;
		this.game = game;
		this.set = this.game.getParentSet();
		this.session = this.set.getParentSession();
		this.toolkit = OpenDartsFormsToolkit.getToolkit();
		this.statsLabel = new HashMap<String, Label>();

		// Stats
		this.statsServices = X01UiPlugin.getService(IStatsProvider.class)
				.getGameStats(game);
		for (IStatsService statsService : this.statsServices) {
			statsService.addStatsListener(this);
		}

		this.main = this.toolkit.createComposite(parent);

		GridLayoutFactory.fillDefaults().applyTo(this.main);
		this.createContents(this.main);

		// listener
		this.set.addListener(this);
		this.session.addListener(this);
	}

	/**
	 * Gets the control.
	 *
	 * @return the control
	 */
	public Composite getControl() {
		return this.main;
	}

	/**
	 * Creates the contents.
	 *
	 * @param parent the parent
	 */
	private void createContents(Composite client) {
		GridDataFactory childData = GridDataFactory.fillDefaults().grab(true,
				false);

		// Session
		Composite cmpSession = this.createSessionComposite(client);
		childData.copy().applyTo(cmpSession);

		// Set
		Composite cmpTotal = this.createSetComposite(client);
		childData.copy().applyTo(cmpTotal);

		// Game
		Composite cmpBest = this.createGameComposite(client);
		childData.copy().applyTo(cmpBest);

	}

	/**
	 * Creates the session composite.
	 *
	 * @param parent the parent
	 * @param player the player
	 * @return the composite
	 */
	private Composite createSessionComposite(Composite parent) {
		Composite main = this.toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().applyTo(main);
		// Section
		Section section = this.toolkit.createSection(main,
				ExpandableComposite.SHORT_TITLE_BAR
						| ExpandableComposite.EXPANDED
						| ExpandableComposite.TWISTIE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
		section.setText("Session");
		section.setFont(OpenDartsFormsToolkit
				.getFont(OpenDartsFormsToolkit.FONT_STATS_BOLD));

		// Section body
		Composite client = this.toolkit.createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().numColumns(4).applyTo(client);

		Label lbl;
		GridDataFactory lblData = GridDataFactory.fillDefaults()
				.grab(false, false).align(SWT.END, SWT.CENTER);
		GridDataFactory valData = GridDataFactory.fillDefaults()
				.grab(true, false).align(SWT.BEGINNING, SWT.CENTER);
		int win;
		// Sets
		lbl = this.toolkit.createLabel(client, "Sets:");
		lbl.setFont(OpenDartsFormsToolkit
				.getFont(OpenDartsFormsToolkit.FONT_STATS));
		lblData.copy().applyTo(lbl);

		win = this.session.getWinningSet(this.player);
		this.lblSets = this.toolkit.createLabel(client, String.valueOf(win));
		this.lblSets.setFont(OpenDartsFormsToolkit
				.getFont(OpenDartsFormsToolkit.FONT_STATS_BOLD));
		valData.copy().applyTo(this.lblSets);

		// Legs
		lbl = this.toolkit.createLabel(client, "Legs:");
		lbl.setFont(OpenDartsFormsToolkit
				.getFont(OpenDartsFormsToolkit.FONT_STATS));
		lblData.copy().applyTo(lbl);

		win = this.set.getWinningGames(this.player);
		this.lblLegs = this.toolkit.createLabel(client, String.valueOf(win));
		this.lblLegs.setFont(OpenDartsFormsToolkit
				.getFont(OpenDartsFormsToolkit.FONT_STATS_BOLD));
		valData.copy().applyTo(this.lblLegs);
		// 100
		this.createStatEntry(client, StatsX01Service.SESSION_TONS, "Tons:",
				lblData, valData);
		// 180
		this.createStatEntry(client, StatsX01Service.SESSION_180s, "180's:",
				lblData, valData);
		// 60+
		this.createStatEntry(client, StatsX01Service.SESSION_60_PLUS, "+60:",
				lblData, valData);
		// 100+
		this.createStatEntry(client, StatsX01Service.SESSION_TONS_PLUS,
				"+100:", lblData, valData);

		// Avg. Dart
		this.createStatEntry(client, StatsX01Service.SESSION_AVG_DART,
				"Avg. Dart:", lblData, valData);
		// Avg. 3 Darts
		this.createStatEntry(client, StatsX01Service.SESSION_AVG_3_DARTS,
				"Avg. 3 Darts:", lblData, valData);

		// Avg. Leg
		this.createStatEntry(client, StatsX01Service.SESSION_AVG_LEG,
				"Avg. leg:", lblData, valData);
		// Best Leg
		this.createStatEntry(client, StatsX01Service.SESSION_BEST_LEG,
				"Best leg:", lblData, valData);

		// High outs
		this.createStatEntry(client, StatsX01Service.SESSION_OUT_OVER_100,
				"High outs:", lblData, valData.copy().span(3, 1));

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
	private Composite createSetComposite(Composite parent) {
		Composite main = this.toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().applyTo(main);

		// Section
		Section section = this.toolkit.createSection(main,
				ExpandableComposite.SHORT_TITLE_BAR
						| ExpandableComposite.EXPANDED
						| ExpandableComposite.TWISTIE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
		section.setText("Set");
		section.setFont(OpenDartsFormsToolkit
				.getFont(OpenDartsFormsToolkit.FONT_STATS_BOLD));

		// Section body
		Composite client = this.toolkit.createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().numColumns(4).applyTo(client);

		GridDataFactory lblData = GridDataFactory.fillDefaults()
				.grab(false, false).align(SWT.END, SWT.CENTER);
		GridDataFactory valData = GridDataFactory.fillDefaults()
				.grab(true, false).align(SWT.BEGINNING, SWT.CENTER);

		// Avg. Darts
		this.createStatEntry(client, StatsX01Service.SET_AVG_DART,
				"Avg. Dart:", lblData, valData);
		// Avg. 3 Darts
		this.createStatEntry(client, StatsX01Service.SET_AVG_3_DARTS,
				"Avg. 3 Darts:", lblData, valData);

		// Avg. Leg
		this.createStatEntry(client, StatsX01Service.SET_AVG_LEG, "Avg. leg:",
				lblData, valData);
		// Best Leg
		this.createStatEntry(client, StatsX01Service.SET_BEST_LEG, "Best leg:",
				lblData, valData);

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
	private Composite createGameComposite(Composite parent) {
		Composite main = this.toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().applyTo(main);

		// Section
		Section section = this.toolkit.createSection(main,
				ExpandableComposite.SHORT_TITLE_BAR
						| ExpandableComposite.EXPANDED
						| ExpandableComposite.TWISTIE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
		section.setText("Current Leg");
		section.setFont(OpenDartsFormsToolkit
				.getFont(OpenDartsFormsToolkit.FONT_STATS_BOLD));

		// Section body
		Composite client = this.toolkit.createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().numColumns(4).applyTo(client);

		GridDataFactory lblData = GridDataFactory.fillDefaults()
				.grab(false, false).align(SWT.END, SWT.CENTER);
		GridDataFactory valData = GridDataFactory.fillDefaults()
				.grab(true, false).align(SWT.BEGINNING, SWT.CENTER);

		// Avg. Darts
		this.createStatEntry(client, StatsX01Service.GAME_AVG_DART,
				"Avg. Dart:", lblData, valData);
		// Avg. 3 Darts
		this.createStatEntry(client, StatsX01Service.GAME_AVG_3_DARTS,
				"Avg. 3 Darts:", lblData, valData);

		// End section
		this.toolkit.paintBordersFor(client);
		section.setClient(client);
		return main;
	}

	/**
	 * Creates the stat entry.
	 *
	 * @param client the client
	 * @param statsKey the stats key
	 * @param label the label
	 * @param lblData the lbl data
	 * @param valData the val data
	 */
	private void createStatEntry(Composite client, String statsKey,
			String label, GridDataFactory lblData, GridDataFactory valData) {
		// Label
		Label lbl = this.toolkit.createLabel(client, label);
		lbl.setFont(OpenDartsFormsToolkit
				.getFont(OpenDartsFormsToolkit.FONT_STATS));
		lblData.copy().applyTo(lbl);

		// Value
		lbl = this.toolkit.createLabel(client, "");
		lbl.setFont(OpenDartsFormsToolkit
				.getFont(OpenDartsFormsToolkit.FONT_STATS_BOLD));
			for(IStatsService statsService : this.statsServices) {
				IStatsEntry entry = statsService.getStatsEntry(this.session,
						this.set, this.game, this.player, statsKey);
				if (entry != null) {
					IStatValue value = entry.getValue();
					if (value != null) {
						lbl.setText(value.getValueAsString());
					}
					break;
				}
			}
		valData.copy().applyTo(lbl);

		// register stats
		this.statsLabel.put(statsKey, lbl);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.session.ISetListener#notifySetEvent(org.opendarts.prototype.model.session.SetEvent)
	 */
	@Override
	public void notifySetEvent(SetEvent event) {
		if (this.set.equals(event.getSet())) {
			LOG.trace("New Set Event: {}", event);
			switch (event.getType()) {
				case NEW_CURRENT_GAME:
					int win = this.set.getWinningGames(this.player);
					this.lblLegs.setText(String.valueOf(win));
					break;
				default:
					break;
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.session.ISessionListener#notifySessionEvent(org.opendarts.prototype.model.session.SessionEvent)
	 */
	@Override
	public void notifySessionEvent(SessionEvent event) {
		if (this.session.equals(event.getSession())) {
			LOG.trace("New Session Event: {}", event);
			switch (event.getType()) {
				case NEW_CURRENT_SET:
					int win = this.session.getWinningSet(this.player);
					this.lblSets.setText(String.valueOf(win));
					break;
				default:
					break;
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.stats.IStatsListener#updatedEntry(org.opendarts.prototype.model.stats.IStats, org.opendarts.prototype.model.stats.IStatsEntry)
	 */
	@Override
	public void updatedEntry(IStats stats, IStatsEntry entry) {
		LOG.trace("Stat updated {}", entry);
		if (this.game.equals(this.set.getCurrentGame())
				&& this.player.equals(stats.getPlayer())) {
			if (stats instanceof GameStats) {
				GameStats gStats = (GameStats) stats;
				if (this.game.equals(gStats.getElement())) {
					this.updateLabel(entry);
				}
			} else if (stats instanceof SetStats) {
				SetStats setStats = (SetStats) stats;
				if (this.game.getParentSet().equals(setStats.getElement())) {
					this.updateLabel(entry);
				}
			} else {
				// always update sessions stats
				this.updateLabel(entry);
			}
		}
	}

	/**
	 * Update label.
	 *
	 * @param entry the entry
	 */
	private void updateLabel(IStatsEntry entry) {
		Label label = this.statsLabel.get(entry.getKey());
		if (label != null) {
			IStatValue value = entry.getValue();
			if (value != null) {
				String val = value.getValueAsString();
				if (!label.isDisposed()) {
					label.setText(val);
					label.getParent().layout(new Control[] { label });
				}
			}
		}
	}

}
