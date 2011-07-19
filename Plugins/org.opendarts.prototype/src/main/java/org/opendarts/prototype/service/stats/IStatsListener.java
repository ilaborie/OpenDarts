package org.opendarts.prototype.service.stats;

import org.opendarts.prototype.model.stats.IStatEntry;
import org.opendarts.prototype.model.stats.IStatValue;
import org.opendarts.prototype.model.stats.IStats;

/**
 * Stats listener
 */
public interface IStatsListener<T> {

	/**
	 * Created entry.
	 *
	 * @param stats the stats
	 * @param entry the entry
	 */
	<U> void createdEntry(IStats<T> stats, IStatEntry<U> entry);

	/**
	 * Updated entry.
	 *
	 * @param <U> the generic type
	 * @param stats the stats
	 * @param oldValue the old value
	 * @param entry the entry
	 */
	<U> void updatedEntry(IStats<T> stats, IStatValue<U> oldValue,
			IStatEntry<U> entry);
}
