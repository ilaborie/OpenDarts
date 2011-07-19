package org.opendarts.prototype.internal.service.stats.x01.entry;

import org.opendarts.prototype.internal.model.dart.x01.WinningX01DartsThrow;
import org.opendarts.prototype.internal.model.game.x01.GameX01Entry;
import org.opendarts.prototype.internal.model.stats.BestStatsEntry;
import org.opendarts.prototype.internal.service.stats.x01.LegComparator;
import org.opendarts.prototype.model.dart.IDartsThrow;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.game.IGameEntry;
import org.opendarts.prototype.model.player.IPlayer;

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
	protected Integer getInput(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		Integer result = null;
		if (dartsThrow instanceof WinningX01DartsThrow) {
			GameX01Entry gEntry = (GameX01Entry) gameEntry;
			result = gEntry.getNbPlayedDart();
		}
		return result;

	}
}
