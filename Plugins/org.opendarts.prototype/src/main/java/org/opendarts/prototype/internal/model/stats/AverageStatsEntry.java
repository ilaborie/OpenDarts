package org.opendarts.prototype.internal.model.stats;

/**
 * The Class MaxStatsEntry.
 *
 * @param <T> the generic type
 */
public abstract class AverageStatsEntry extends AbstractStatsEntry<Number> {

	private int counter;

	/** The sum. */
	private double sum;

	/**
	 * Instantiates a new best stats entry.
	 *
	 * @param comparator the comparator
	 */
	public AverageStatsEntry(String key) {
		super(key);
		this.counter = 0;
	}

	/**
	 * Adds the new input.
	 *
	 * @param input the input
	 * @return true, if successful
	 */
	@Override
	public boolean addNewInput(Number input) {
		if (input != null) {
			StatsValue<Number> value = (StatsValue<Number>) this.getValue();
			if (value == null) {
				// new value
				value = new StatsValue<Number>();
				value.setValue(input);
				this.setValue(value);

				this.sum = input.doubleValue();
			} else {
				this.counter++;
				this.sum += input.doubleValue();

				double avg = this.sum / this.counter;
				value.setValue(avg);
			}
		}
		return true;
	}

}
