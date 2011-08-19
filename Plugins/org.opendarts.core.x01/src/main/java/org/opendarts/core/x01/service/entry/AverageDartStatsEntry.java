package org.opendarts.core.x01.service.entry;

import org.eclipse.core.runtime.Platform;
import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.impl.AverageStatsEntry;
import org.opendarts.core.x01.model.BrokenX01DartsThrow;
import org.opendarts.core.x01.model.WinningX01DartsThrow;

/**
 * The Class AverageDartStatsEntry.
 */
public class AverageDartStatsEntry extends AverageStatsEntry {

	/** The allow broken. */
	private final boolean allowBroken;

	/**
	 * Instantiates a new average dart stats entry.
	 *
	 * @param key the key
	 */
	public AverageDartStatsEntry(String key) {
		super(key);
		this.allowBroken = Platform.getPreferencesService().getBoolean(
				"org.opendarts.ui.stats", "OpenDarts.pref.stats.broken", true,
				null);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AbstractStatsEntry#getInput(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	protected Number getEntryValue(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		Number val = null;
		if (dartsThrow != null) {
			if (this.allowBroken
					|| !(dartsThrow instanceof BrokenX01DartsThrow)) {
				val = dartsThrow.getScore();
			} else {
				val = 0;
			}
		}
		return val;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.stats.AverageStatsEntry#getEntryIncr(org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.game.IGameEntry, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	protected Number getEntryIncr(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		int incr = 3;
		if (dartsThrow instanceof WinningX01DartsThrow) {
			WinningX01DartsThrow winThrow = (WinningX01DartsThrow) dartsThrow;
			incr = winThrow.getNbDartToFinish();
		}
		return incr;
	}

}