package org.opendarts.prototype.internal.service.stats.x01.entry;

import org.opendarts.prototype.internal.model.dart.x01.WinningX01DartsThrow;
import org.opendarts.prototype.internal.model.stats.AverageStatsEntry;
import org.opendarts.prototype.model.dart.IDartsThrow;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.game.IGameEntry;
import org.opendarts.prototype.model.player.IPlayer;

/**
 * The Class AverageDartStatsEntry.
 */
public class AverageDartStatsEntry extends AverageStatsEntry {

	/**
	 * Instantiates a new average dart stats entry.
	 *
	 * @param key the key
	 */
	public AverageDartStatsEntry(String key) {
		super(key);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#getInput(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	protected Number getInput(IGame game, IPlayer player, IGameEntry gameEntry,
			IDartsThrow dartsThrow) {
		if (dartsThrow != null) {
			int score = dartsThrow.getScore();
			int nbDarts = 3;
			if (dartsThrow instanceof WinningX01DartsThrow) {
				WinningX01DartsThrow winThrow = (WinningX01DartsThrow) dartsThrow;
				nbDarts = winThrow.getNbDartToFinish();
			}
			return ((double) score) / ((double) nbDarts);
		}
		return null;
	}
}