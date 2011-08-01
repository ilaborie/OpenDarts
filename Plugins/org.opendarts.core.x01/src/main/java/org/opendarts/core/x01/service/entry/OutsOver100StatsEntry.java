package org.opendarts.core.x01.service.entry;

import java.util.ArrayList;
import java.util.List;

import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.impl.AbstractStatsEntry;
import org.opendarts.core.x01.model.WinningX01DartsThrow;

/**
 * The Class OutsOver100StatsEntry.
 */
public class OutsOver100StatsEntry extends AbstractStatsEntry<String> {

	private final List<Integer> bestOuts;

	/**
	 * Instantiates a new best outs stats entry.
	 *
	 * @param key the key
	 */
	public OutsOver100StatsEntry(String key) {
		super(key);
		this.bestOuts = new ArrayList<Integer>();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#getInput(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	protected String getInput(IGame game, IPlayer player, IGameEntry gameEntry,
			IDartsThrow dartsThrow) {
		String result = null;
		if (dartsThrow instanceof WinningX01DartsThrow) {
			int out = dartsThrow.getScore();
			if (out >= 100) {
				this.bestOuts.add(out);
				result = this.buildResult();
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#getUndoInput(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	protected String getUndoInput(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		String result = null;
		if (dartsThrow != null) {
			if (dartsThrow instanceof WinningX01DartsThrow) {
				int out = dartsThrow.getScore();
				if (out >= 100) {
					this.bestOuts.remove(out);
				}
			}
		}
		result = this.buildResult();
		return result;
	}

	/**
	 * Builds the result.
	 *
	 * @return the string
	 */
	private String buildResult() {
		StringBuffer sb = new StringBuffer();
		boolean first = true;
		for (int out : this.bestOuts) {
			if (first) {
				first = false;
			} else {
				sb.append(", ");
			}
			sb.append(out);
		}
		return sb.toString();
	}

}
