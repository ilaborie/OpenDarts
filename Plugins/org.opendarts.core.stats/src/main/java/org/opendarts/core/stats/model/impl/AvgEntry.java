package org.opendarts.core.stats.model.impl;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.opendarts.core.utils.FormaterUtils;

/**
 * The Class AvgEntry.
 */
public class AvgEntry implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6504076456407189539L;

	/** The count. */
	private double count;

	/** The distrib. */
	private final List<Number> distrib;

	/** The sum. */
	private double sum;
	
	/** The format. */
	private final NumberFormat format;

	/**
	 * Instantiates a new avg entry.
	 */
	public AvgEntry() {
		super();
		this.format = FormaterUtils.getFormatters().getDecimalFormat();
		
		this.count = 0;
		this.sum = 0.0D;
		this.distrib = new ArrayList<Number>();
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
		this.distrib.add(val);
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
		this.distrib.remove(val);
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
			result = this.format.format(this.getAvg());
		}
		return result;
	}

	/**
	 * Gets the avg.
	 *
	 * @return the avg
	 */
	public double getAvg() {
		return this.sum / this.count;
	}

	/**
	 * Gets the distribution.
	 *
	 * @return the distribution
	 */
	public double[] getDistribution() {
		double[] result = null; 
		synchronized (this.distrib) {
			result = new double[this.distrib.size()];
			for (int i = 0; i < this.distrib.size(); i++) {
				result[i] = this.distrib.get(i).doubleValue();
			}
		}
		return result;
	}
}
