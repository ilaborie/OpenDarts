package org.opendarts.prototype.internal.model.stats;

/**
 * The Class MaxStatsEntry.
 *
 * @param <T> the generic type
 */
public abstract class BestNumberStatsEntry extends AbstractStatsEntry<Number> {

	/**
	 * Instantiates a new best stats entry.
	 *
	 * @param comparator the comparator
	 */
	public BestNumberStatsEntry(String key) {
		super(key);
	}

	/**
	 * Adds the new input.
	 *
	 * @param input the input
	 */
	@Override
	public boolean addNewInput(Number input) {
		boolean result = false;
		if (input != null) {
			StatsValue<Number> value = (StatsValue<Number>) this.getValue();
			if (value == null) {
				// new value
				value = new StatsValue<Number>();
				value.setValue(input);
				this.setValue(value);
				result = true;
			} else {
				Number oldValue = value.getValue();
				if (input.doubleValue() > oldValue.doubleValue()) {
					value.setValue(input);
					result = true;
				}
			}
		}
		return result;
	}

}
