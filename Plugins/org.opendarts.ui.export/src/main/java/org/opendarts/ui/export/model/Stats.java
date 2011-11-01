package org.opendarts.ui.export.model;

import java.util.ArrayList;
import java.util.List;

import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.IElementStats;
import org.opendarts.core.stats.model.IElementStats.IEntry;
import org.opendarts.core.stats.model.func.PlayerEntryValueFunction;
import org.opendarts.core.stats.service.IStatsService;

/**
 * The Class Stats.
 */
public class Stats<E> {

	/** The parent. */
	private final AbstractBean<E> parent;

	/** The elt stats. */
	private final IElementStats<E> eltStats;

	/** The entries. */
	private List<StatsEntry<E>> entries = null;

	/** The stats service. */
	private final IStatsService statsService;

	/**
	 * Instantiates a new stats.
	 *
	 * @param parent the parent
	 * @param statsService the stats service
	 * @param eltStats the elt stats
	 */
	public Stats(AbstractBean<E> parent, IStatsService statsService,
			IElementStats<E> eltStats) {
		super();
		this.parent = parent;
		this.statsService = statsService;
		this.eltStats = eltStats;
	}

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		int index = this.parent.getStats().indexOf(this);
		return this.getParent().getRootName() + "-stats" + (index + 1) + ".csv";
	}

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public AbstractBean<E> getParent() {
		return this.parent;
	}

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	public List<IPlayer> getPlayers() {
		return this.parent.getPlayerList();
	}

	/**
	 * Gets the entries.
	 *
	 * @return the entries
	 */
	public List<StatsEntry<E>> getEntries() {
		if (this.entries == null) {
			this.entries = new ArrayList<StatsEntry<E>>();
			//
			PlayerEntryValueFunction playerEntryValue;
			for (IEntry<?> e : eltStats.getStatsEntries()) {
				if (e.isVisible()) {
					playerEntryValue = new PlayerEntryValueFunction(e);
					this.entries.add(new StatsEntry<E>(this.statsService, this,
							e, playerEntryValue));
				}
			}
		}
		return this.entries;
	}

}
