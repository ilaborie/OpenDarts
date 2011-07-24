package org.opendarts.prototype.internal.model.stats.x01;

import org.opendarts.prototype.internal.model.stats.IncrementStatsEntry;
import org.opendarts.prototype.model.dart.IDartsThrow;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.game.IGameEntry;
import org.opendarts.prototype.model.player.IPlayer;

/**
 * The Class DartScoreStatsEntry.
 */
public class DartScoreStatsEntry extends IncrementStatsEntry {

	/** The score. */
	private final int score;

	/**
	 * Instantiates a new dart score stats entry.
	 *
	 * @param key the key
	 * @param score the score
	 */
	public DartScoreStatsEntry(String key, int score) {
		super(key);
		this.score = score;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.IncrementStatsEntry#shouldIncrement(org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	protected boolean shouldIncrement(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		return (dartsThrow != null) && (this.score == dartsThrow.getScore());
	}

}
