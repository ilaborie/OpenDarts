package org.opendarts.core.x01.defi.service.entry;

import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.impl.SumStatsEntry;

/**
 * The Class TimeTotalStatsEntry.
 */
public class TimeTotalStatsEntry extends SumStatsEntry {

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
		return TimeUtils.getTime(game, dartsThrow);
	}

}
