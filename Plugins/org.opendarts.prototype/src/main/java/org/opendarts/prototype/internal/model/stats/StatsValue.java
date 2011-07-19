package org.opendarts.prototype.internal.model.stats;

import org.opendarts.prototype.model.stats.IStatValue;

/**
 * The Class StatsValue.
 *
 * @param <T> the generic type
 */
public class StatsValue<T> implements IStatValue<T> {

	/** The value. */
	private T value;

	/**
	 * Instantiates a new stats value.
	 */
	public StatsValue() {
		super();
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
		return String.valueOf(this.value);
	}

}
