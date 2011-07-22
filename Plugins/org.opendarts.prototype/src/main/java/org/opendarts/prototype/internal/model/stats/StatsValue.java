package org.opendarts.prototype.internal.model.stats;

import java.text.DecimalFormat;
import java.text.MessageFormat;

import org.opendarts.prototype.model.stats.IStatValue;

/**
 * The Class StatsValue.
 *
 * @param <T> the generic type
 */
public class StatsValue<T> implements IStatValue<T> {

	/** The Constant DOUBLE_FORMATTER. */
	private static final DecimalFormat DOUBLE_FORMATTER = new DecimalFormat(
			"0.00");

	/** The value. */
	private T value;

	/**
	 * Instantiates a new stats value.
	 */
	public StatsValue() {
		super();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return MessageFormat.format("{0}", value);
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
		String result;
		if (this.value == null) {
			result = "-";
		} else if (this.value instanceof Double) {
			result = DOUBLE_FORMATTER.format((Double) this.value);
		} else {
			result = String.valueOf(this.value);
		}
		return result;
	}

}