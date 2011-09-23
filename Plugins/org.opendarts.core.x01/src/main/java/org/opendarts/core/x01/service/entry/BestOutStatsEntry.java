package org.opendarts.core.x01.service.entry;

import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.impl.BestNumberStatsEntry;
import org.opendarts.core.x01.model.WinningX01DartsThrow;

/**
 * The Class BestOutStatsEntry.
 */
public class BestOutStatsEntry extends BestNumberStatsEntry {

	/**
	 * Instantiates a new best out stats entry.
	 *
	 * @param key the key
	 */
	public BestOutStatsEntry(String key) {
		super(key);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#getInput(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	public Number getInput(IGame game, IPlayer player, IGameEntry gameEntry,
			IDartsThrow dartsThrow) {
		Integer result = null;
		if ((dartsThrow != null)
				&& (dartsThrow instanceof WinningX01DartsThrow)) {
			result = dartsThrow.getScore();
		}
		return result;
	}
}