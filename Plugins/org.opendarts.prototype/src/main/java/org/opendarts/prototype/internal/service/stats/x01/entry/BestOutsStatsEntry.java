package org.opendarts.prototype.internal.service.stats.x01.entry;

import org.opendarts.prototype.internal.model.dart.x01.WinningX01DartsThrow;
import org.opendarts.prototype.internal.model.stats.AbstractStatsEntry;
import org.opendarts.prototype.model.dart.IDartsThrow;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.game.IGameEntry;
import org.opendarts.prototype.model.player.IPlayer;

/**
 * The Class BestOutsStatsEntry.
 */
public class BestOutsStatsEntry extends
			AbstractStatsEntry<String> {

	/**
	 * Instantiates a new best outs stats entry.
	 *
	 * @param key the key
	 */
		public BestOutsStatsEntry(String key) {
			super(key);
		}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#getInput(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
		@Override
		protected String getInput(IGame game, IPlayer player,
				IGameEntry gameEntry, IDartsThrow dartsThrow) {
			String result = null;
			if (dartsThrow instanceof WinningX01DartsThrow) {
				int out = dartsThrow.getScore();
				if (out >= 100) {
					String current = this.getValue().getValue();
					if ((current == null) || "".equals(current)) {
						result = String.valueOf(out);
					} else {
						result = current + ", " + out;
					}
				}
			}
			return result;
		}
	}
