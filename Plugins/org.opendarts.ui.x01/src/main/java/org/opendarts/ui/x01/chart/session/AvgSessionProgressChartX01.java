package org.opendarts.ui.x01.chart.session;

import org.opendarts.core.model.session.ISession;
import org.opendarts.core.stats.model.IStatValue;
import org.opendarts.core.stats.model.IStatsEntry;
import org.opendarts.core.stats.model.impl.AvgEntry;
import org.opendarts.core.stats.service.IStatsService;

/**
 * The Class AvgSessionProgressChartX01.
 */
public class AvgSessionProgressChartX01 extends
		SessionProgressChartX01<AvgEntry> {

	/**
	 * Instantiates a new avg session progress chart x01.
	 *
	 * @param name the name
	 * @param statKey the stat key
	 * @param session the session
	 * @param service the service
	 */
	public AvgSessionProgressChartX01(String name, String statKey,
			ISession session, IStatsService service) {
		super(name, statKey, session, service);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.x01.chart.SessionProgressChartX01#getValue(org.opendarts.core.stats.model.IStatsEntry)
	 */
	@Override
	protected Double getValue(IStatsEntry<AvgEntry> stEntry) {
		Double result = null;
		if (stEntry != null) {
			IStatValue<AvgEntry> value = stEntry.getValue();
			if ((value != null) && (value.getValue() != null)) {
				result = value.getValue().getAvg();
			}
		}
		return result;
	}
}
