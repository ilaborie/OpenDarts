package org.opendarts.ui.export.model;

import java.util.ArrayList;
import java.util.List;

import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.IElementStats.IEntry;
import org.opendarts.core.stats.model.func.PlayerEntryValueFunction;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.ui.stats.OpenDartsStatsUiPlugin;
import org.opendarts.ui.stats.service.IStatsUiProvider;
import org.opendarts.ui.stats.service.IStatsUiService;

/**
 * The Class StatsEntry.
 */
public class StatsEntry<E> {
	
	/** The stats ui provider. */
	private static IStatsUiProvider statsUiProvider;

	/** The player entry value. */
	private final PlayerEntryValueFunction playerEntryValue;
	
	/** The entry. */
	private final IEntry<?> entry;
	
	/** The parent. */
	private final Stats<E> parent;
	
	/** The stats service. */
	private final IStatsService statsService;

	/** The values. */
	private List<Object> values = null;
	
	/** The label. */
	private String label = null;
	
	static {
		statsUiProvider = OpenDartsStatsUiPlugin.getService(IStatsUiProvider.class);
	}

	/**
	 * Instantiates a new stats entry.
	 *
	 * @param statsService the stats service
	 * @param stats the stats
	 * @param e the e
	 * @param playerEntryValue the player entry value
	 */
	public StatsEntry(IStatsService statsService, Stats<E> stats, IEntry<?> e,
			PlayerEntryValueFunction playerEntryValue) {
		super();
		this.parent = stats;
		this.entry = e;
		this.statsService = statsService;
		this.playerEntryValue = playerEntryValue;
	}

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		if (this.label == null) {
			IStatsUiService statsUiService = statsUiProvider
					.getStatsUiService(this.statsService);
			if (statsUiService != null) {
				label = statsUiService.getStatsLabelProvider().getText(
						this.entry.getKey());
			}
		}
		return this.label;
	}

	/**
	 * Gets the players values.
	 *
	 * @return the players values
	 */
	public List<Object> getPlayersValues() {
		if (this.values == null) {
			this.values = new ArrayList<Object>();
			for (IPlayer p : this.parent.getPlayers()) {
				this.values.add(this.playerEntryValue.apply(p));
			}
		}
		return values;
	}

}
