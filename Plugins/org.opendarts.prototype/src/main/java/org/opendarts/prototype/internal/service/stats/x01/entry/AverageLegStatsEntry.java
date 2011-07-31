package org.opendarts.prototype.internal.service.stats.x01.entry;

import org.opendarts.prototype.internal.model.dart.x01.WinningX01DartsThrow;
import org.opendarts.prototype.internal.model.game.x01.GameX01Entry;
import org.opendarts.prototype.internal.model.stats.AverageStatsEntry;
import org.opendarts.prototype.model.dart.IDartsThrow;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.game.IGameEntry;
import org.opendarts.prototype.model.player.IPlayer;

/**
 * The Class AverageLegStatsEntry.
 */
public class AverageLegStatsEntry extends AverageStatsEntry {

	/**
	 * Instantiates a new average leg stats entry.
	 *
	 * @param key the key
	 */
	public AverageLegStatsEntry(String key) {
		super(key);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AverageStatsEntry#getEntryValue(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	protected Number getEntryValue(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		Number result = null;
		if ((dartsThrow != null)
				&& (dartsThrow instanceof WinningX01DartsThrow)) {
			GameX01Entry gEntry = (GameX01Entry) gameEntry;
			result = gEntry.getNbPlayedDart();
		}
		return result;
	}
}
