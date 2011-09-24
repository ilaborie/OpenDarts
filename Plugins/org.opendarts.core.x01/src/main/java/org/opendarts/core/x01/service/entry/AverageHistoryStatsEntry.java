package org.opendarts.core.x01.service.entry;

import java.util.Comparator;

import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.IStatValue;
import org.opendarts.core.stats.model.impl.AbstractStatsEntry;
import org.opendarts.core.stats.model.impl.AverageStatsEntry;
import org.opendarts.core.stats.model.impl.AvgEntry;
import org.opendarts.core.stats.model.impl.StatsValue;

/**
 * The Class AverageDartStatsEntry.
 */
public class AverageHistoryStatsEntry extends AbstractStatsEntry<AvgHistory> {

	/** The avg entry. */
	private final AverageStatsEntry avgEntry;

	/**
	 * Instantiates a new average dart history stats entry.
	 *
	 * @param key the key
	 */
	public AverageHistoryStatsEntry(String key, AverageStatsEntry avgEntry) {
		super(key);
		this.avgEntry = avgEntry;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.model.IStatsEntry#getComparator()
	 */
	@Override
	public Comparator<AvgHistory> getComparator() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.model.impl.AbstractStatsEntry#getInput(org.opendarts.core.model.game.IGame, org.opendarts.core.model.player.IPlayer, org.opendarts.core.model.game.IGameEntry, org.opendarts.core.model.dart.IDartsThrow)
	 */
	@Override
	public AvgHistory getInput(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		AvgHistory history = null;

		AvgEntry avg = null;
		IStatValue<AvgEntry> v = this.avgEntry.getValue();
		if (v!=null) {
			avg = v.getValue();
		}
		
		
		if (avg != null) {
			StatsValue<AvgHistory> val = (StatsValue<AvgHistory>) this
					.getValue();
			if (val == null) {
				val = new StatsValue<AvgHistory>();
				this.setValue(val);
			}

			history = val.getValue();
			if (history == null) {
				history = new AvgHistory();
				val.setValue(history);
			}
			history.addHistory(dartsThrow.getTimestamp(), avg.getAvg());
		}
		return history;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.model.impl.AbstractStatsEntry#getUndoInput(org.opendarts.core.model.game.IGame, org.opendarts.core.model.player.IPlayer, org.opendarts.core.model.game.IGameEntry, org.opendarts.core.model.dart.IDartsThrow)
	 */
	@Override
	public AvgHistory getUndoInput(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		AvgHistory history = null;

		AvgEntry avg = this.avgEntry.getUndoInput(game, player, gameEntry,
				dartsThrow);
		if (avg != null) {
			StatsValue<AvgHistory> val = (StatsValue<AvgHistory>) this
					.getValue();
			if (val == null) {
				val = new StatsValue<AvgHistory>();
				this.setValue(val);
			}

			history = val.getValue();
			if (history == null) {
				history = new AvgHistory();
				val.setValue(history);
			}
			history.addHistory(dartsThrow.getTimestamp(), avg.getAvg());
		}
		return history;
	}

}