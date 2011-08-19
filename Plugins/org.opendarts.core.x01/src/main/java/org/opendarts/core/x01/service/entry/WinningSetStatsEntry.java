package org.opendarts.core.x01.service.entry;

import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.model.impl.IncrementStatsEntry;
import org.opendarts.core.x01.model.WinningX01DartsThrow;

/**
 * The Class WinningGameStatsEntry.
 */
public class WinningSetStatsEntry extends IncrementStatsEntry {

	/**
	 * Instantiates a new winning game stats entry.
	 *
	 * @param key the key
	 */
	public WinningSetStatsEntry(String key) {
		super(key);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.model.impl.IncrementStatsEntry#shouldIncrement(org.opendarts.core.model.game.IGame, org.opendarts.core.model.player.IPlayer, org.opendarts.core.model.game.IGameEntry, org.opendarts.core.model.dart.IDartsThrow)
	 */
	@Override
	protected boolean shouldIncrement(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		ISet set = game.getParentSet();
		int games = set.getWinningGames(player);
		return ((dartsThrow instanceof WinningX01DartsThrow) && ((games + 1) == set
				.getGameDefinition().getNbGameToWin()));
	}

}
