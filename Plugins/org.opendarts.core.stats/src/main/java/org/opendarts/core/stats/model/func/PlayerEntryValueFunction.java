package org.opendarts.core.stats.model.func;

import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.IStatValue;
import org.opendarts.core.stats.model.IStatsEntry;
import org.opendarts.core.stats.model.IElementStats.IEntry;

import com.google.common.base.Function;

/**
 * The Class PlayerEntryValueFunction.
 */
public class PlayerEntryValueFunction implements Function<IPlayer, Object> {

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
	public Object apply(IPlayer p) {
		Object result = "-";
		IStatsEntry entry = ((IEntry) e).getPlayerEntry(p);
		if (entry != null) {
			IStatValue value = entry.getValue();
			if (value != null) {
				result = value.getValue();
			}
		}
		return result;
	}
}