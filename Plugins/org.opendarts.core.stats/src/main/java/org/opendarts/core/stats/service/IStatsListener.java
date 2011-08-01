package org.opendarts.core.stats.service;

import org.opendarts.core.stats.model.IStats;
import org.opendarts.core.stats.model.IStatsEntry;

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
