package org.opendarts.prototype.service.stats;

import org.opendarts.prototype.model.stats.IStats;
import org.opendarts.prototype.model.stats.IStatsEntry;

/**
 * Stats listener
 */
public interface IStatsListener<T> {

	/**
	 * Updated entry.
	 *
	 * @param <U> the generic type
	 * @param stats the stats
	 * @param entry the entry
	 */
	<U> void updatedEntry(IStats<T> stats, IStatsEntry<U> entry);
}
