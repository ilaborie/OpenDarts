package org.opendarts.core.x01.defi.service.entry;

import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.dart.impl.SkipedDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.impl.IncrementStatsEntry;

/**
 * The Class PlaysThrowsStatsEntry.
 */
public class PlaysThrowsStatsEntry extends IncrementStatsEntry{

	/**
	 * Instantiates a new plays throws stats entry.
	 *
	 * @param key the key
	 */
	public PlaysThrowsStatsEntry(String key) {
		super(key);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.model.impl.IncrementStatsEntry#shouldIncrement(org.opendarts.core.model.game.IGame, org.opendarts.core.model.player.IPlayer, org.opendarts.core.model.game.IGameEntry, org.opendarts.core.model.dart.IDartsThrow)
	 */
	@Override
	protected boolean shouldIncrement(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		return (dartsThrow!=null && !(dartsThrow instanceof SkipedDartsThrow));
	}

}
