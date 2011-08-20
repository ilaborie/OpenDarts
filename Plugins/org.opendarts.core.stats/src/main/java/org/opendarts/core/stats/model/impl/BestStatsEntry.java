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
public abstract class BestStatsEntry<T> extends AbstractStatsEntry<T> {

	/** The comparator. */
	private final Comparator<T> comparator;

	/** The old best. */
	private final Map<T, T> oldBest;

	/**
	 * Instantiates a new best stats entry.
	 *
	 * @param comparator the comparator
	 */
	public BestStatsEntry(String key, Comparator<T> comparator) {
		super(key);
		this.comparator = comparator;
		this.oldBest = new HashMap<T, T>();
	}
	
	@Override
	public Comparator<T> getComparator() {
		return this.comparator;
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
				// backup
				this.oldBest.put(input, null);
			} else {
				T oldValue = value.getValue();
				if (this.comparator.compare(input, oldValue) > 0) {
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
	protected boolean undoNewInput(T input) {
		boolean result = false;
		if (input != null) {
			StatsValue<T> value = (StatsValue<T>) this.getValue();
			if (value != null) {
				T oldValue = value.getValue();
				if (this.comparator.compare(input, oldValue) == 0) {
					T newBest = this.oldBest.get(input);
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
	protected T getUndoInput(IGame game, IPlayer player, IGameEntry gameEntry,
			IDartsThrow dartsThrow) {
		return this.getInput(game, player, gameEntry, dartsThrow);
	}

}
