package org.opendarts.ui.x01.defi.editor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.ColumnLayout;
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
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.core.x01.defi.OpenDartsX01DefiBundle;
import org.opendarts.core.x01.defi.model.GameX01Defi;
import org.opendarts.core.x01.defi.service.StatsX01DefiService;
import org.opendarts.ui.pref.IGeneralPrefs;
import org.opendarts.ui.stats.service.IStatsUiProvider;
import org.opendarts.ui.stats.service.IStatsUiService;
import org.opendarts.ui.utils.OpenDartsFormsToolkit;
import org.opendarts.ui.x01.X01UiPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class PlayerStatusComposite.
 */
@SuppressWarnings("rawtypes")
public class PlayerDefiStatusComposite implements ISetListener,
		ISessionListener, IStatsListener {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(PlayerDefiStatusComposite.class);

	/** The player. */
	private final IPlayer player;

	/** The game. */
	private final GameX01Defi game;

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

	/** The stats ui provider. */
	private final IStatsUiProvider statsUiProvider;

	/**
	 * Instantiates a new player status composite.
	 * 
	 * @param parent
	 *            the parent
	 * @param player
	 *            the player
	 * @param game
	 *            the game
	 */
	@SuppressWarnings("unchecked")
	public PlayerDefiStatusComposite(Composite parent, IPlayer player,
			GameX01Defi game) {
		super();
		this.player = player;
		this.game = game;
		this.set = this.game.getParentSet();
		this.session = this.set.getParentSession();
		this.toolkit = OpenDartsFormsToolkit.getToolkit();
		this.statsLabel = new HashMap<String, Label>();

		// Stats
		this.statsServices = new ArrayList<IStatsService>();
		this.statsServices.add(OpenDartsX01DefiBundle.getStatsX01Service());

		this.statsUiProvider = X01UiPlugin.getService(IStatsUiProvider.class);
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
	 * @param parent
	 *            the parent
	 */
	private void createContents(Composite main) {
		// Game stats
		List<String> col1Stats = new ArrayList<String>();
		List<String> col2Stats = new ArrayList<String>();

		List<String> statsList = Arrays.asList(StatsX01DefiService.GAME_140,
				StatsX01DefiService.GAME_180s,

				StatsX01DefiService.GAME_TONS,
				StatsX01DefiService.GAME_TONS_PLUS,

				StatsX01DefiService.GAME_60, StatsX01DefiService.GAME_60_PLUS,

				StatsX01DefiService.GAME_AVG_DART,
				StatsX01DefiService.GAME_AVG_3_DARTS,

				StatsX01DefiService.GAME_COUNT_THROWS,
				StatsX01DefiService.GAME_AVG_TIME);
		String key;
		for (int i = 0; i < statsList.size(); i++) {
			key = statsList.get(i);
			if ((i % 2) == 0) {
				col1Stats.add(key);
			} else {
				col2Stats.add(key);
			}
		}

		Composite client = this.toolkit.createComposite(main, SWT.WRAP);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(client);
		client.setLayout(new ColumnLayout());

		GridDataFactory lblData = GridDataFactory.fillDefaults()
				.grab(false, false).align(SWT.END, SWT.CENTER);
		GridDataFactory valData = GridDataFactory.fillDefaults()
				.grab(true, false).align(SWT.BEGINNING, SWT.CENTER);

		// Col1
		Composite col1 = this.toolkit.createComposite(client);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(col1);

		for (String col1Stat : col1Stats) {
			this.createStatEntry(col1, col1Stat, lblData, valData);
		}

		// Col2
		Composite col2 = this.toolkit.createComposite(client);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(col2);

		for (String col2Stat : col2Stats) {
			this.createStatEntry(col2, col2Stat, lblData, valData);
		}

	}

	/**
	 * Creates the stat entry.
	 * 
	 * @param client
	 *            the client
	 * @param statsKey
	 *            the stats key
	 * @param label
	 *            the label
	 * @param lblData
	 *            the lbl data
	 * @param valData
	 *            the val data
	 */
	private void createStatEntry(Composite client, String statsKey,
			GridDataFactory lblLayoutData, GridDataFactory valLayoutData) {
		String label = "";
		String description = null;
		String s;
		IStatsUiService statsUiService;
		for (IStatsService statsService : this.statsServices) {
			statsUiService = this.statsUiProvider
					.getStatsUiService(statsService);
			if (statsUiService != null) {
				ColumnLabelProvider labelProvider = statsUiService
						.getStatsLabelProvider();
				description = labelProvider.getToolTipText(statsKey);
				s = labelProvider.getText(statsKey);
				label = s + ":";
				break;
			}
		}

		// Label
		Label lbl = this.toolkit.createLabel(client, label);
		lbl.setFont(OpenDartsFormsToolkit.getFont(IGeneralPrefs.FONT_STATS));
		if (description != null) {
			lbl.setToolTipText(description);
		}
		lblLayoutData.applyTo(lbl);

		// Value
		lbl = this.toolkit.createLabel(client, "");
		lbl.setFont(OpenDartsFormsToolkit
				.getFont(IGeneralPrefs.FONT_STATS_LABEL));
		for (IStatsService statsService : this.statsServices) {
			IStatsEntry entry = statsService.getStatsEntry(this.session,
					this.set, this.game, this.player, statsKey);
			lbl.setText("      ");
			if (entry != null) {
				IStatValue value = entry.getValue();
				if (value != null) {
					lbl.setText(value.getValueAsString());
				}
				break;
			}
		}
		valLayoutData.applyTo(lbl);

		// register stats
		this.statsLabel.put(statsKey, lbl);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opendarts.prototype.model.session.ISetListener#notifySetEvent(org
	 * .opendarts.prototype.model.session.SetEvent)
	 */
	@Override
	public void notifySetEvent(SetEvent event) {
		if (this.set.equals(event.getSet())) {
			LOG.trace("New Set Event: {}", event);
			switch (event.getType()) {
				case NEW_CURRENT_GAME:
					int win = this.set.getWinningGames(this.player);
					if ((this.lblLegs != null) && !this.lblLegs.isDisposed()) {
						this.lblLegs.setText(String.valueOf(win));
					}
					break;
				default:
					break;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opendarts.prototype.model.session.ISessionListener#notifySessionEvent
	 * (org.opendarts.prototype.model.session.SessionEvent)
	 */
	@Override
	public void notifySessionEvent(SessionEvent event) {
		if (this.session.equals(event.getSession())) {
			LOG.trace("New Session Event: {}", event);
			switch (event.getType()) {
				case NEW_CURRENT_SET:
					int win = this.session.getWinningSet(this.player);
					if ((this.lblSets != null) && !this.lblSets.isDisposed()) {
						this.lblSets.setText(String.valueOf(win));
					}
					break;
				default:
					break;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opendarts.core.model.session.ISessionListener#sessionCreated(org.
	 * opendarts.core.model.session.ISession)
	 */
	@Override
	public void sessionCreated(ISession session) {
		// Nothing to do
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opendarts.prototype.service.stats.IStatsListener#updatedEntry(org
	 * .opendarts.prototype.model.stats.IStats,
	 * org.opendarts.prototype.model.stats.IStatsEntry)
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
	 * @param entry
	 *            the entry
	 */
	private void updateLabel(IStatsEntry entry) {
		final Label label = this.statsLabel.get(entry.getKey());
		if (label != null) {
			IStatValue value = entry.getValue();
			if (value != null) {
				final String val = value.getValueAsString();
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						if (!label.isDisposed()) {
							label.setText(val);
							label.getParent().layout(new Control[] { label });
						}
					}
				});
			}
		}
	}

}
