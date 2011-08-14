package org.opendarts.core.x01.service.entry;

import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.impl.IncrementStatsEntry;

/**
 * The Class WinningGameStatsEntry.
 */
public class PlayedSetStatsEntry extends IncrementStatsEntry {

	/**
	 * Instantiates a new winning game stats entry.
	 *
	 * @param key the key
	 */
	public PlayedSetStatsEntry(String key) {
		super(key);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.model.impl.IncrementStatsEntry#shouldIncrement(org.opendarts.core.model.game.IGame, org.opendarts.core.model.player.IPlayer, org.opendarts.core.model.game.IGameEntry, org.opendarts.core.model.dart.IDartsThrow)
	 */
	@Override
	protected boolean shouldIncrement(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		return (gameEntry.getRound() == 1 && game.getParentSet().getAllGame()
				.size() == 1);
	}

}
