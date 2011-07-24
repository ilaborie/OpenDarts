package org.opendarts.prototype.internal.service.stats.x01.entry;

import org.opendarts.prototype.internal.model.stats.AbstractStatsEntry;
import org.opendarts.prototype.model.dart.IDartsThrow;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.game.IGameEntry;
import org.opendarts.prototype.model.player.IPlayer;

/**
 * The Class TotalDartStatsEntry.
 */
public class TotalDartStatsEntry extends AbstractStatsEntry<Integer> {

	/**
	 * Instantiates a new total dart stats entry.
	 *
	 * @param key the key
	 */
	public TotalDartStatsEntry(String key) {
		super(key);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#getInput(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	protected Integer getInput(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		Integer result = null;
		if (dartsThrow != null) {
			result = dartsThrow.getScore();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#getUndoInput(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	protected Integer getUndoInput(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		int result = 0;
		if (dartsThrow != null) {
			result = -dartsThrow.getScore();
		}
		return result;
	}
}
