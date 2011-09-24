package org.opendarts.ui.x01.chart.set;

import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.model.IStatValue;
import org.opendarts.core.stats.model.IStatsEntry;
import org.opendarts.core.stats.model.impl.AvgEntry;
import org.opendarts.core.stats.service.IStatsService;

/**
 * The Class AvgSessionProgressChartX01.
 */
public class AvgSetProgressChartX01 extends SetProgressChartX01<AvgEntry> {

	/**
	 * Instantiates a new avg session progress chart x01.
	 *
	 * @param name the name
	 * @param statKey the stat key
	 * @param set the set
	 * @param service the service
	 */
	public AvgSetProgressChartX01(String name, String statKey, ISet set,
			IStatsService service) {
		super(name, statKey, set, service);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.x01.chart.SessionProgressChartX01#getValue(org.opendarts.core.stats.model.IStatsEntry)
	 */
	@Override
	protected Double getValue(IStatsEntry<AvgEntry> stEntry) {
		Double result = null;
		IStatValue<AvgEntry> value = stEntry.getValue();
		if ((value != null) && (value.getValue() != null)) {
			result = value.getValue().getAvg();
		}

		return result;
	}

}
