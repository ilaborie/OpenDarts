package org.opendarts.core.x01.service.entry;

import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.IStatValue;
import org.opendarts.core.stats.model.impl.AbstractStatsEntry;
import org.opendarts.core.stats.model.impl.IntegerListEntry;
import org.opendarts.core.stats.model.impl.StatsValue;
import org.opendarts.core.x01.model.WinningX01DartsThrow;

/**
 * The Class OutsOver100StatsEntry.
 */
public class OutsOver100StatsEntry extends AbstractStatsEntry<IntegerListEntry> {

	/**
	 * Instantiates a new best outs stats entry.
	 *
	 * @param key the key
	 */
	public OutsOver100StatsEntry(String key) {
		super(key);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#getInput(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	protected IntegerListEntry getInput(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		StatsValue<IntegerListEntry> val = (StatsValue<IntegerListEntry>) this
				.getValue();
		if (val == null) {
			val = new StatsValue<IntegerListEntry>();
			this.setValue(val);
		}

		IntegerListEntry lst = val.getValue();
		if (lst == null) {
			lst = new IntegerListEntry();
			val.setValue(lst);
		}

		if (dartsThrow instanceof WinningX01DartsThrow) {
			int out = dartsThrow.getScore();
			if (out >= 100) {
				lst.addValue(out);
			}
		}
		return lst;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#getUndoInput(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	protected IntegerListEntry getUndoInput(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		IStatValue<IntegerListEntry> val = this.getValue();
		IntegerListEntry lst = val.getValue();

		if (dartsThrow != null) {
			if (dartsThrow instanceof WinningX01DartsThrow) {
				int out = dartsThrow.getScore();
				if (out >= 100) {
					lst.removeValue(out);
				}
			}
		}
		return lst;
	}

}
