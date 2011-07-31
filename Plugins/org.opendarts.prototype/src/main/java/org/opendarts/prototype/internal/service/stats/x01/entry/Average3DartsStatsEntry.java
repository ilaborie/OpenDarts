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
	 * @see org.opendarts.prototype.internal.model.stats.AverageStatsEntry#getEntryValue(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	protected Number getEntryValue(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		Number val = null;
		if (dartsThrow != null) {
			val = dartsThrow.getScore();
		}
		return val;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AverageStatsEntry#getEntryIncr(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	protected Number getEntryIncr(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		double incr = 1D;
		if (dartsThrow instanceof WinningX01DartsThrow) {
			WinningX01DartsThrow winThrow = (WinningX01DartsThrow) dartsThrow;
			incr = winThrow.getNbDartToFinish() / 3d;
		}
		return incr;
	}
}