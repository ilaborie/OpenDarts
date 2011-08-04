package org.opendarts.core.x01.service;

import java.io.Serializable;
import java.util.Comparator;

/**
 * The Class LegComparator.
 */
public class LegComparator implements Comparator<Integer>, Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1807631292393227124L;

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Integer o1, Integer o2) {
		// Reverse
		return o2 - o1;
	}
}
