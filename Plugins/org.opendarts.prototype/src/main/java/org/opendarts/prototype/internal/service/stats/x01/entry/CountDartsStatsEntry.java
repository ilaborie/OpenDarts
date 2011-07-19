package org.opendarts.prototype.internal.service.stats.x01.entry;

import org.opendarts.prototype.internal.model.dart.x01.WinningX01DartsThrow;
import org.opendarts.prototype.internal.model.stats.AbstractStatsEntry;
import org.opendarts.prototype.model.dart.IDartsThrow;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.game.IGameEntry;
import org.opendarts.prototype.model.player.IPlayer;

/**
 * The Class CountDartsStatsEntry.
 */
public class CountDartsStatsEntry extends
		AbstractStatsEntry<Integer> {

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
		int nbDarts = (gameEntry.getRound() - 1) * 3;
		if (dartsThrow instanceof WinningX01DartsThrow) {
			WinningX01DartsThrow wdt = (WinningX01DartsThrow) dartsThrow;
			nbDarts += wdt.getNbDartToFinish();
		} else {
			nbDarts += 3;
		}
		return nbDarts;
	}
}
