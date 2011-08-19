package org.opendarts.core.stats.model.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class AvgEntry.
 */
public class IntegerListEntry implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2968485162231543621L;

	/** The list. */
	private final List<Integer> list;

	/**
	 * Instantiates a new avg entry.
	 */
	public IntegerListEntry() {
		super();
		this.list = new ArrayList<Integer>();
	}

	/**
	 * Adds the value.
	 *
	 * @param val the val
	 */
	public void addValue(int val) {
		this.list.add(val);
	}

	/**
	 * Removes the value.
	 *
	 * @param val the val
	 */
	public void removeValue(Integer val) {
		this.list.remove(val);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		boolean first = true;
		for (int i : this.list) {
			if (first) {
				first = false;
			} else {
				sb.append(", ");
			}
			sb.append(i);
		}
		return sb.toString();
	}
}
