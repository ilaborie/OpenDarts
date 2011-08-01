package org.opendarts.core.x01.service.entry;

import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.impl.IncrementStatsEntry;

/**
 * The Class DartRangeStatsEntry.
 */
public class DartRangeStatsEntry extends IncrementStatsEntry {

	/** The score min. */
	private final int scoreMin;

	/** The score max. */
	private final int scoreMax;

	/**
	 * Instantiates a new dart score stats entry.
	 *
	 * @param key the key
	 * @param scoreMin the score min
	 * @param scoreMax the score max
	 */
	public DartRangeStatsEntry(String key, int scoreMin, int scoreMax) {
		super(key);
		this.scoreMin = Math.min(scoreMin, scoreMax);
		this.scoreMax = Math.max(scoreMin, scoreMax);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.IncrementStatsEntry#shouldIncrement(org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	protected boolean shouldIncrement(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		return (dartsThrow != null) && (this.scoreMin <= dartsThrow.getScore())
				&& (this.scoreMax >= dartsThrow.getScore());
	}

}
