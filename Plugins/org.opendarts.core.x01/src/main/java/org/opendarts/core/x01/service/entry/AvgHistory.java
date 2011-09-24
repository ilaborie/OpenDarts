package org.opendarts.core.x01.service.entry;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * The Class AvgHistory.
 */
public class AvgHistory implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3714700224571017806L;
	
	/** The values. */
	private final TreeMap<Long, Double> values;
	
	/**
	 * Instantiates a new avg history.
	 */
	public AvgHistory() {
		super();
		this.values = new TreeMap<Long, Double>();
	}
	
	/**
	 * Gets the values.
	 *
	 * @return the values
	 */
	public Map<Long, Double> getValues() {
		return this.values;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		Double value = this.values.lastEntry().getValue();
		return NumberFormat.getNumberInstance().format(value);
	}

	/**
	 * Adds the history.
	 *
	 * @param timestamp the timestamp
	 * @param avg the avg
	 */
	public void addHistory(long timestamp, double avg) {
		this.values.put(timestamp, avg);
	}

	/**
	 * Gets the last value.
	 *
	 * @return the last value
	 */
	public Double getLastValue() {
		Double result = null;
		Entry<Long, Double> entry = this.values.lastEntry();
		if (entry!=null) {
			result = entry.getValue();
		}
		return result;
	}

}
