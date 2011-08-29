/*
 * 
 */
package org.opendarts.ui.x01.label;

import java.util.List;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.model.IElementStats;
import org.opendarts.core.stats.model.IStatValue;
import org.opendarts.core.stats.model.IStats;
import org.opendarts.core.stats.model.IStatsEntry;
import org.opendarts.core.stats.model.IElementStats.IEntry;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.ui.pref.IGeneralPrefs;
import org.opendarts.ui.utils.OpenDartsFormsToolkit;

/**
 * The Class ScoreLabelProvider.
 */
public class SetStatsLabelProvider extends ColumnLabelProvider {

	/** The set. */
	private final ISet set;
	/** The stats service. */
	private final List<IStatsService> statsServices;

	/** The stats key. */
	private final String statsKey;

	/**
	 * Instantiates a new score label provider.
	 *
	 * @param statsService the stats service
	 * @param set the set
	 */
	public SetStatsLabelProvider(List<IStatsService> statsServices, ISet set,
			String statsKey) {
		super();
		this.statsServices = statsServices;
		this.set = set;
		this.statsKey = statsKey;
	}

	/**
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof IPlayer) {
			IPlayer player = (IPlayer) element;
			for (IStatsService statsService : this.statsServices) {
				IStats<ISet> stats = statsService.getSetStats(this.set)
						.getPlayerStats(player);
				if (stats != null) {
					IStatsEntry<Object> entry = stats.getEntry(this.statsKey);
					if (entry != null) {
						IStatValue<Object> value = entry.getValue();
						if (value != null) {
							return value.getValueAsString();
						}
					}
				}
			}
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ColumnLabelProvider#getBackground(java.lang
	 * .Object)
	 */
	@Override
	public Color getBackground(Object element) {
		if (element instanceof IPlayer) {
			IPlayer player = (IPlayer) element;
			for (IStatsService statsService : this.statsServices) {
				IElementStats<ISet> setStats = statsService
						.getSetStats(this.set);
				IStats<ISet> stats = setStats.getPlayerStats(player);
				if (stats != null) {
					IStatsEntry<Object> entry = stats.getEntry(this.statsKey);
					if (entry != null) {
						IEntry<ISet> ientry = this.getEntry(entry.getKey(),
								setStats.getStatsEntries());
						@SuppressWarnings("rawtypes")
						IStatValue bestValue = ientry.getBestValue();

						IStatValue<Object> playerValue = entry.getValue();
						if (this.equalsValue(bestValue, playerValue)) {
							return OpenDartsFormsToolkit.getToolkit()
									.getColors()
									.getColor(IGeneralPrefs.COLOR_WINNING);
						}
					}
				}
			}
		}
		return super.getBackground(element);
	}

	/**
	 * Gets the entry.
	 *
	 * @param key the key
	 * @param statsEntries the stats entries
	 * @return the entry
	 */
	private IEntry<ISet> getEntry(String key, List<IEntry<ISet>> statsEntries) {
		IEntry<ISet> result = null;
		for (IEntry<ISet> entry : statsEntries) {
			if (key.equals(entry.getKey())) {
				result = entry;
				break;
			}
		}
		return result;
	}

	/**
	 * Equals value.
	 * 
	 * @param bestValue
	 *            the best value
	 * @param playerValue
	 *            the player value
	 * @return true, if successful
	 */
	@SuppressWarnings("rawtypes")
	private boolean equalsValue(IStatValue bestValue, IStatValue playerValue) {
		boolean result = false;
		Object o1 = null;
		if (bestValue != null) {
			o1 = bestValue.getValue();
		}

		Object o2 = null;
		if (playerValue != null) {
			o2 = playerValue.getValue();
		}

		if (o1 != null) {
			result = o1.equals(o2);
		}

		return result;
	}
}