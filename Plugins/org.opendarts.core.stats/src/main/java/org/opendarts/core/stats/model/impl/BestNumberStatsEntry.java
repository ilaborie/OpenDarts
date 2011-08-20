package org.opendarts.core.stats.model.impl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;

/**
 * The Class MaxStatsEntry.
 *
 * @param <T> the generic type
 */
public abstract class BestNumberStatsEntry extends AbstractStatsEntry<Number> {

	/** The old best. */
	private final Map<Number, Number> oldBest;

	/**
	 * Instantiates a new best stats entry.
	 *
	 * @param comparator the comparator
	 */
	public BestNumberStatsEntry(String key) {
		super(key);
		this.oldBest = new HashMap<Number, Number>();
	}
	
	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.model.IStatsEntry#getComparator()
	 */
	@Override
	public Comparator<Number> getComparator() {
		return new NumberComparator();
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
				// backup
				this.oldBest.put(input, null);
			} else {
				Number oldValue = value.getValue();
				if (input.doubleValue() > oldValue.doubleValue()) {
					value.setValue(input);
					result = true;
					// backup
					this.oldBest.put(input, oldValue);
				}
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#undoNewInput(java.lang.Object)
	 */
	@Override
	protected boolean undoNewInput(Number input) {
		boolean result = false;
		if (input != null) {
			StatsValue<Number> value = (StatsValue<Number>) this.getValue();
			if (value != null) {
				Number oldValue = value.getValue();
				if (input.doubleValue() == oldValue.doubleValue()) {
					Number newBest = this.oldBest.get(input);
					value.setValue(newBest);
					result = true;
					// backup
					this.oldBest.remove(input);
				}
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#getUndoInput(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	protected Number getUndoInput(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		return this.getInput(game, player, gameEntry, dartsThrow);
	}

}
