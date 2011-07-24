/*
 * 
 */
package org.opendarts.prototype.internal.model.stats;

import org.opendarts.prototype.model.dart.IDartsThrow;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.game.IGameEntry;
import org.opendarts.prototype.model.player.IPlayer;

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
	protected boolean addNewInput(Number input) {
		if (input != null) {
			StatsValue<Number> value = (StatsValue<Number>) this.getValue();
			if (value == null) {
				// new value
				value = new StatsValue<Number>();
				this.setValue(value);
				this.counter = 1;
				this.sum = input.doubleValue();
				this.updateValue(value);
			} else {
				this.counter++;
				this.sum += input.doubleValue();
				this.updateValue(value);
			}
		}
		return true;
	}

	/**
	 * Update value.
	 *
	 * @param value the value
	 */
	private void updateValue(StatsValue<Number> value) {
		double avg = this.sum / this.counter;
		value.setValue(avg);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#getUndoInput(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	protected Number getUndoInput(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		return this.getInput(game, player, gameEntry, dartsThrow);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#undoNewInput(java.lang.Object)
	 */
	@Override
	protected boolean undoNewInput(Number input) {
		if (input != null) {
			StatsValue<Number> value = (StatsValue<Number>) this.getValue();
			if (value != null) {
				this.counter--;
				this.sum -= input.doubleValue();
				this.updateValue(value);
			}
		}
		return true;
	}

}
