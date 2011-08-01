package org.opendarts.core.x01.service.entry;

import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.impl.IncrementStatsEntry;

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
