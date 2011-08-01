package org.opendarts.core.x01.service.entry;

import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.impl.AverageStatsEntry;
import org.opendarts.core.x01.model.GameX01Entry;
import org.opendarts.core.x01.model.WinningX01DartsThrow;

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