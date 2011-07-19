package org.opendarts.prototype.internal.model.stats;

import java.util.Comparator;

/**
 * The Class MaxStatsEntry.
 *
 * @param <T> the generic type
 */
public abstract class BestStatsEntry<T> extends AbstractStatsEntry<T> {

	/** The comparator. */
	private final Comparator<T> comparator;

	/**
	 * Instantiates a new best stats entry.
	 *
	 * @param comparator the comparator
	 */
	public BestStatsEntry(String key, Comparator<T> comparator) {
		super(key);
		this.comparator = comparator;
	}

	/**
	 * Adds the new input.
	 *
	 * @param input the input
	 */
	@Override
	public boolean addNewInput(T input) {
		boolean result = false;
		if (input != null) {
			StatsValue<T> value = (StatsValue<T>) this.getValue();
			if (value == null) {
				// new value
				value = new StatsValue<T>();
				value.setValue(input);
				this.setValue(value);
				result = true;
			} else {
				T oldValue = value.getValue();
				if (this.comparator.compare(input, oldValue) > 0) {
					value.setValue(input);
					result = true;
				}
			}
		}
		return result;
	}

}
