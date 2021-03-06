/*
 * 
 */
package org.opendarts.core.stats.model.impl;

import java.util.Comparator;

import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.comparator.NumberComparator;

/**
 * The Class MaxStatsEntry.
 *
 * @param <T> the generic type
 */
public abstract class SumStatsEntry extends AbstractStatsEntry<Number> {

	/**
	 * Instantiates a new best stats entry.
	 *
	 * @param comparator the comparator
	 */
	public SumStatsEntry(String key) {
		super(key);
	}
	
	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.model.IStatsEntry#getComparator()
	 */
	@Override
	public Comparator<Number> getComparator() {
		return new NumberComparator();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#addNewInput(java.lang.Object)
	 */
	@Override
	protected boolean addNewInput(Number input) {
		boolean result = false;
		if (input != null) {
			result = true;
			StatsValue<Number> value = (StatsValue<Number>) this.getValue();
			if (value == null) {
				// new value
				value = new StatsValue<Number>();
				value.setValue(input);
				this.setValue(value);
			} else {
				Number sum;
				if (input instanceof Integer) {
					sum = value.getValue().intValue() + input.intValue();
				} else {
					sum = value.getValue().doubleValue() + input.doubleValue();
				}
				value.setValue(sum);
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#getUndoInput(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	public Number getUndoInput(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		return this.getInput(game, player, gameEntry, dartsThrow);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#undoNewInput(java.lang.Object)
	 */
	@Override
	protected boolean undoNewInput(Number input) {
		boolean result = false;
		if (input != null) {
			result = true;
			StatsValue<Number> value = (StatsValue<Number>) this.getValue();
			if (value != null) {
				Number sum;
				if (input instanceof Integer) {
					sum = value.getValue().intValue() - input.intValue();
				} else {
					sum = value.getValue().doubleValue() - input.doubleValue();
				}
				value.setValue(sum);
			}
		}
		return result;
	}
}