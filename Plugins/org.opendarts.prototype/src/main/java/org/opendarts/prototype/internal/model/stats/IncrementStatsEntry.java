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
public abstract class IncrementStatsEntry extends AbstractStatsEntry<Number> {
	public int count;

	/**
	 * Instantiates a new best stats entry.
	 *
	 * @param comparator the comparator
	 */
	public IncrementStatsEntry(String key) {
		super(key);
		this.count = 0;
	}

	/**
	 * Adds the new input.
	 *
	 * @param input the input
	 */
	@Override
	public boolean addNewInput(Number counter) {
		boolean result = false;
		StatsValue<Number> value = (StatsValue<Number>) this.getValue();
		if (value == null) {
			// new value
			value = new StatsValue<Number>();
			value.setValue(counter);
			this.setValue(value);
			result = true;
		} else {
			if (this.count != value.getValue().intValue()) {
				value.setValue(counter);
				result = true;
			}
		}
		return result;
	}

	@Override
	protected Number getInput(IGame game, IPlayer player, IGameEntry gameEntry,
			IDartsThrow dartsThrow) {
		if (this.shouldIncrement(game, player, gameEntry, dartsThrow)) {
			this.count++;
		}
		return this.count;
	}

	/**
	 * Should increment.
	 *
	 * @param dartsThrow the darts throw
	 * @return true, if successful
	 */
	protected abstract boolean shouldIncrement(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow);
}
