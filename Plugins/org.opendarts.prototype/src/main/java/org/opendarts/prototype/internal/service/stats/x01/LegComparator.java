package org.opendarts.prototype.internal.service.stats.x01;

import java.util.Comparator;

/**
 * The Class LegComparator.
 */
public class LegComparator implements Comparator<Integer> {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Integer o1, Integer o2) {
		// Reverse
		return o2 - o1;
	}
}
