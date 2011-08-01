package org.opendarts.core.x01.service.entry;

import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.impl.AverageStatsEntry;
import org.opendarts.core.x01.model.WinningX01DartsThrow;

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