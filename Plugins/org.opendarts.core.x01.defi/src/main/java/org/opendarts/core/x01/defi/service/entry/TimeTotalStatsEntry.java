package org.opendarts.core.x01.defi.service.entry;

import java.util.HashMap;
import java.util.Map;

import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.impl.SumStatsEntry;

/**
 * The Class TimeTotalStatsEntry.
 */
public class TimeTotalStatsEntry extends SumStatsEntry {
	/** The timestamp. */
	private static Map<IGame, Long> timestamp = new HashMap<IGame, Long>();

	/** The last time. */
	private static Map<IGame, Double> lastTime = new HashMap<IGame, Double>();

	/** The last throw. */
	private static IDartsThrow lastThrow;

	/**
	 * Instantiates a new time total stats entry.
	 *
	 * @param key the key
	 */
	public TimeTotalStatsEntry(String key) {
		super(key);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.model.impl.AbstractStatsEntry#getInput(org.opendarts.core.model.game.IGame, org.opendarts.core.model.player.IPlayer, org.opendarts.core.model.game.IGameEntry, org.opendarts.core.model.dart.IDartsThrow)
	 */
	@Override
	public Number getInput(IGame game, IPlayer player, IGameEntry gameEntry,
			IDartsThrow dartsThrow) {
		Double time = null;
		long ts = dartsThrow.getTimestamp();
		if (dartsThrow.equals(lastThrow)) {
			time = lastTime.get(game);
		} else {
			Long lastTs = timestamp.get(game);
			if (lastTs == null) {
				lastTs = game.getStart().getTimeInMillis();
			}
			time = ((double) ts - lastTs) / 1000d;
		}

		timestamp.put(game, ts);
		lastThrow = dartsThrow;
		lastTime.put(game, time);
		return time;
	}

}
