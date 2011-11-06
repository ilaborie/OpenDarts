package org.opendarts.ui.export.model;

import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.IElementStats.IEntry;
import org.opendarts.core.stats.model.IStatValue;
import org.opendarts.core.stats.model.IStatsEntry;

import com.google.common.base.Function;

/**
 * The Class PlayerEntryValueFunction.
 */
public class PlayerEntryValueFunction implements Function<IPlayer, StatsValue> {

	/** The e. */
	private final IEntry<?> e;

	/**
	 * Instantiates a new player entry value function.
	 *
	 * @param e the e
	 */
	public PlayerEntryValueFunction(IEntry<?> e) {
		this.e = e;
	}

	/* (non-Javadoc)
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public StatsValue apply(IPlayer p) {
		StatsValue result = null;
		boolean best = false;

		IStatsEntry entry = ((IEntry) e).getPlayerEntry(p);
		IStatValue bestValue = e.getBestValue();
		if (entry != null) {
			IStatValue value = entry.getValue();
			if (value != null) {
				best = this.equalsValue(bestValue, value);
				result = new StatsValue(value.getValue(), best);
			}
		}
		return result;
	}

	/**
	 * Equals value.
	 * 
	 * @param bestValue
	 *            the best value
	 * @param playerValue
	 *            the player value
	 * @return true, if successful
	 */
	@SuppressWarnings("rawtypes")
	private boolean equalsValue(IStatValue bestValue, IStatValue playerValue) {
		boolean result = false;
		Object o1 = null;
		if (bestValue != null) {
			o1 = bestValue.getValue();
		}

		Object o2 = null;
		if (playerValue != null) {
			o2 = playerValue.getValue();
		}

		if (o1 != null) {
			result = o1.equals(o2);
		}

		return result;
	}

}