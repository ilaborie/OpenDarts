package org.opendarts.core.stats.model.impl;

import java.io.Serializable;

/**
 * The Class AvgEntry.
 */
public class AvgEntry implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6504076456407189539L;

	/** The count. */
	private double count;

	/** The sum. */
	private double sum;

	/**
	 * Instantiates a new avg entry.
	 */
	public AvgEntry() {
		super();
		this.count = 0;
		this.sum = 0.0D;
	}

	/**
	 * Instantiates a new avg entry.
	 *
	 * @param val the val
	 */
	public AvgEntry(Number val) {
		this();
		this.addValue(val);
	}

	/**
	 * Adds the value.
	 *
	 * @param val the val
	 */
	public void addValue(Number val) {
		this.addValue(1d, val);
	}

	/**
	 * Adds the value.
	 *
	 * @param val the val
	 */
	public void addValue(Number incr, Number val) {
		this.count += incr.doubleValue();
		this.sum += val.doubleValue();
	}

	/**
	 * Removes the value.
	 *
	 * @param val the val
	 */
	public void removeValue(Number val) {
		this.removeValue(1d, val);
	}

	/**
	 * Removes the value.
	 *
	 * @param val the val
	 */
	public void removeValue(Number incr, Number val) {
		this.count -= incr.doubleValue();
		this.sum -= val.doubleValue();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result;
		if (this.count < 1) {
			result = "-";
		} else {
			double d = this.sum / this.count;
			result = StatsValue.DOUBLE_FORMATTER.format(d);
		}
		return result;
	}
}