package org.opendarts.ui.export.model;

import org.opendarts.core.stats.model.IStatValue;

/**
 * The Class StatsEntry.
 *
 */
@SuppressWarnings("rawtypes")
public class StatsValue {

	/** The value. */
	private final IStatValue value;

	/** The best. */
	private final boolean best;

	/**
	 * Instantiates a new stats entry.
	 *
	 * @param value the value
	 * @param best the best
	 */
	public StatsValue(IStatValue value, boolean best) {
		super();
		this.value = value;
		this.best = best;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.value.toString();
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public IStatValue getValue() {
		return this.value;
	}

	/**
	 * Checks if is best.
	 *
	 * @return true, if is best
	 */
	public boolean isBest() {
		return this.best;
	}

}
