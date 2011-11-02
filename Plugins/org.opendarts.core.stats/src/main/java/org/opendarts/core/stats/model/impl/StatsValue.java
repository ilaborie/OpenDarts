package org.opendarts.core.stats.model.impl;

import org.opendarts.core.stats.model.IStatValue;
import org.opendarts.core.utils.FormaterUtils;

/**
 * The Class StatsValue.
 *
 * @param <T> the generic type
 */
public class StatsValue<T> implements IStatValue<T> {

	/** The value. */
	private T value;
	
	private final FormaterUtils formatters;

	/**
	 * Instantiates a new stats value.
	 */
	public StatsValue() {
		super();
		this.formatters = FormaterUtils.getFormatters();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getValueAsString();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.stats.IStatValue#getValue()
	 */
	@Override
	public T getValue() {
		return this.value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(T value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.stats.IStatValue#getValueAsString()
	 */
	@Override
	public String getValueAsString() {
		return this.displayValue(this.value);
	}

	/**
	 * Extracted.
	 *
	 * @param value the value
	 * @return the string
	 */
	private String displayValue(Object value) {
		String result;
		if (value == null) {
			result = "";
		} else if (value instanceof Double) {
			result = this.formatters.getDecimalFormat().format(value);
		} else if (value instanceof Float) {
			result = this.formatters.getDecimalFormat().format(value);
		} else if (value instanceof Long) {
			result = this.formatters.getNumberFormat().format(value);
		} else if (value instanceof Integer) {
			result = this.formatters.getNumberFormat().format(value);
		} else if (value instanceof AvgEntry) {
			result = this.displayValue(((AvgEntry) value).getAvg());
		} else {
			result = String.valueOf(this.value);
		}
		return result;
	}

}
