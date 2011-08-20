package org.opendarts.core.stats.model.impl;

import java.util.Comparator;

public class NumberComparator implements Comparator<Number> {
	/**
	 * Compare.
	 *
	 * @param o1 the o1
	 * @param o2 the o2
	 * @return the int
	 */
	@Override
	public int compare(Number o1, Number o2) {
		int result;
		if (o1 == null && o2 != null) {
			result = -1;
		} else if (o2 == null && o1 != null) {
			result = 1;
		} else if (o1 == null && o2 == null) {
			result = 0;
		} else {
			double diff = o1.doubleValue() - o2.doubleValue();
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