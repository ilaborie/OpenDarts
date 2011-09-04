package org.opendarts.ui.x01.model;

import java.util.Map;

import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.IStatsEntry;
import org.opendarts.core.stats.model.impl.AvgEntry;

/**
 * The Class AvgDistributionChartX01.
 */
public class AvgDistributionChartX01 extends DisbributionChartX01<AvgEntry> {

	/**
	 * Instantiates a new avg leg chart x01.
	 *
	 * @param name the name
	 * @param statKey the stat key
	 * @param entries the entries
	 */
	public AvgDistributionChartX01(String name, String statKey, Object elt,
			Map<IPlayer, IStatsEntry<AvgEntry>> entries) {
		super(name, statKey, elt, entries);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.x01.model.DisbributionChartX01#getDistribution(org.opendarts.core.stats.model.IStatsEntry)
	 */
	@Override
	protected double[] getDistribution(IStatsEntry<AvgEntry> stEntry) {
		double[] result = null;
		if (stEntry.getValue() != null && stEntry.getValue().getValue() != null) {
			AvgEntry value = stEntry.getValue().getValue();
			result = value.getDistribution();
		}
		return result;
	}

}
