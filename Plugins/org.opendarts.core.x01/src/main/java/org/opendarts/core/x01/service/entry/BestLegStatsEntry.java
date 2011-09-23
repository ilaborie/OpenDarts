package org.opendarts.core.x01.service.entry;

import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.impl.BestStatsEntry;
import org.opendarts.core.x01.model.GameX01Entry;
import org.opendarts.core.x01.model.WinningX01DartsThrow;
import org.opendarts.core.x01.service.LegComparator;

/**
 * The Class BestLegStatsEntry.
 */
public class BestLegStatsEntry extends BestStatsEntry<Integer> {

	/**
	 * Instantiates a new best leg stats entry.
	 *
	 * @param key the key
	 */
	public BestLegStatsEntry(String key) {
		super(key, new LegComparator());
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#getInput(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	public Integer getInput(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		Integer result = null;
		if ((dartsThrow != null)
				&& (dartsThrow instanceof WinningX01DartsThrow)) {
			GameX01Entry gEntry = (GameX01Entry) gameEntry;
			result = gEntry.getNbPlayedDart();
		}
		return result;

	}
}
