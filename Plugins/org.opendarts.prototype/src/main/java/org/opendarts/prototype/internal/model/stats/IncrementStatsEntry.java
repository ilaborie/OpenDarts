package org.opendarts.prototype.internal.model.stats;

import org.opendarts.prototype.model.dart.IDartsThrow;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.game.IGameEntry;
import org.opendarts.prototype.model.player.IPlayer;

/**
 * The Class MaxStatsEntry.
 *
 */
public abstract class IncrementStatsEntry extends AbstractStatsEntry<Number> {

	/** The count. */
	public int count;

	/**
	 * Instantiates a new best stats entry.
	 *
	 * @param key the key
	 */
	public IncrementStatsEntry(String key) {
		super(key);
		this.count = 0;
	}

	/**
	 * Adds the new input.
	 *
	 * @param counter the counter
	 * @return true, if successful
	 */
	@Override
	protected boolean addNewInput(Number counter) {
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

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#getInput(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	protected Number getInput(IGame game, IPlayer player, IGameEntry gameEntry,
			IDartsThrow dartsThrow) {
		if (this.shouldIncrement(game, player, gameEntry, dartsThrow)) {
			this.increment();
		}
		return this.count;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#getUndoInput(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	protected Number getUndoInput(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		if (this.shouldIncrement(game, player, gameEntry, dartsThrow)) {
			this.decrement();
		}
		return this.count;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#undoDartsThrow(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	public boolean undoDartsThrow(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		if (this.shouldIncrement(game, player, gameEntry, dartsThrow)) {
			this.increment();
		}
		return false;
	}

	/**
	 * Increment.
	 */
	protected void increment() {
		this.count++;
	}

	/**
	 * Decrement.
	 */
	protected void decrement() {
		this.count--;
	}

	/**
	 * Should increment.
	 *
	 * @param game the game
	 * @param player the player
	 * @param gameEntry the game entry
	 * @param dartsThrow the darts throw
	 * @return true, if successful
	 */
	protected abstract boolean shouldIncrement(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow);
}
