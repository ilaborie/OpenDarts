package org.opendarts.core.stats.model.comparator;

import java.util.Comparator;

import org.opendarts.core.stats.model.impl.AvgEntry;

/**
 * The Class AvgEntryComparator.
 */
public class AvgEntryComparator implements Comparator<AvgEntry> {
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(AvgEntry o1, AvgEntry o2) {
		int result;
		if (o1 == null && o2 != null) {
			result = -1;
		} else if (o2 != null && o1 == null) {
			result = 1;
		} else if (o1 == null && o2 == null) {
			result = 0;
		} else {
			double diff = o1.getAvg() - o2.getAvg();
			if (Math.abs(diff) < 0.005) {
				result = 0;
			} else if (diff > 0) {
				result = 1;
			} else {
				result = -1;
			}
		}
		return result;
	}
}