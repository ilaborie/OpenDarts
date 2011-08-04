/*
 * 
 */
package org.opendarts.ui.x01.label;

import java.util.List;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.model.IStatValue;
import org.opendarts.core.stats.model.IStats;
import org.opendarts.core.stats.model.IStatsEntry;
import org.opendarts.core.stats.service.IStatsService;

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
}