package org.opendarts.core.x01.service.entry;

import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.impl.SumStatsEntry;
import org.opendarts.core.x01.model.WinningX01DartsThrow;

/**
 * The Class CountDartsStatsEntry.
 */
public class CountDartsStatsEntry extends SumStatsEntry {

	/**
	 * Instantiates a new sets the count darts stats entry.
	 *
	 * @param key the key
	 */
	public CountDartsStatsEntry(String key) {
		super(key);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#getInput(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	protected Integer getInput(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		int nbDarts = 0;
		if (dartsThrow != null) {
			nbDarts = 3;
			if (dartsThrow instanceof WinningX01DartsThrow) {
				WinningX01DartsThrow wdt = (WinningX01DartsThrow) dartsThrow;
				nbDarts = wdt.getNbDartToFinish();
			}
		}
		return nbDarts;
	}
}
