/*
 * 
 */
package org.opendarts.prototype.ui.x01.label;

import java.util.Map;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.model.session.ISet;
import org.opendarts.prototype.model.stats.IStatValue;
import org.opendarts.prototype.model.stats.IStats;
import org.opendarts.prototype.model.stats.IStatsEntry;
import org.opendarts.prototype.service.stats.IStatsService;

/**
 * The Class ScoreLabelProvider.
 */
public class SetStatsLabelProvider extends ColumnLabelProvider {

	/** The set. */
	private final ISet set;
	/** The stats service. */
	private final IStatsService statsService;

	/** The stats key. */
	private final String statsKey;

	/**
	 * Instantiates a new score label provider.
	 *
	 * @param statsService the stats service
	 * @param set the set
	 */
	public SetStatsLabelProvider(IStatsService statsService, ISet set,
			String statsKey) {
		super();
		this.statsService = statsService;
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
			Map<IPlayer, IStats<ISet>> setStats = this.statsService
					.getSetStats(this.set);
			if (setStats != null) {
				IStats<ISet> stats = setStats.get(player);
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
