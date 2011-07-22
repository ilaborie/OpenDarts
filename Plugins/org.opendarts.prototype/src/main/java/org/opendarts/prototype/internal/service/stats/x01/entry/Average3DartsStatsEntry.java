package org.opendarts.prototype.internal.service.stats.x01.entry;

import org.opendarts.prototype.internal.model.dart.x01.WinningX01DartsThrow;
import org.opendarts.prototype.internal.model.stats.AverageStatsEntry;
import org.opendarts.prototype.model.dart.IDartsThrow;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.game.IGameEntry;
import org.opendarts.prototype.model.player.IPlayer;

/**
 * The Class Average3DartsStatsEntry.
 */
public class Average3DartsStatsEntry extends AverageStatsEntry {

	/**
	 * Instantiates a new average3 darts stats entry.
	 *
	 * @param key the key
	 */
	public Average3DartsStatsEntry(String key) {
		super(key);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#getInput(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	protected Number getInput(IGame game, IPlayer player, IGameEntry gameEntry,
			IDartsThrow dartsThrow) {
		int score = dartsThrow.getScore();
		Number result = score;
		if (dartsThrow instanceof WinningX01DartsThrow) {
			WinningX01DartsThrow winThrow = (WinningX01DartsThrow) dartsThrow;
			int nbDarts = winThrow.getNbDartToFinish();
			if (nbDarts<3) {
				result = ((double) 3) * (((double) score) / ((double) nbDarts));
			}
		}
		return result;
	}
}