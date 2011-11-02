package org.opendarts.core.x01.defi.service.entry;

import java.util.HashMap;
import java.util.Map;

import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.game.IGame;

/**
 * The Class TimeUtils.
 */
public final class TimeUtils {
	/** The timestamp. */
	private static Map<IGame, Long> timestamp = new HashMap<IGame, Long>();

	/** The last time. */
	private static Map<IGame, Double> lastTime = new HashMap<IGame, Double>();

	/** The last throw. */
	private static IDartsThrow lastThrow;

	/**
	 * Instantiates a new time utils.
	 */
	private TimeUtils() {
		super();
	}

	/**
	 * Gets the time.
	 *
	 * @param game the game
	 * @param dartsThrow the darts throw
	 * @return the time
	 */
	public static Double getTime(IGame game, IDartsThrow dartsThrow) {
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
