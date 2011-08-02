package org.opendarts.ui.stats.label;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.IElementStats.IEntry;
import org.opendarts.core.stats.model.IStatValue;
import org.opendarts.core.stats.model.IStatsEntry;

/**
 * The Class PlayerStatsLabelProvider.
 */
public class PlayerStatsLabelProvider extends ColumnLabelProvider {

	/** The player. */
	private final IPlayer player;

	/**
	 * Instantiates a new player stats label provider.
	 *
	 * @param player the player
	 */
	public PlayerStatsLabelProvider(IPlayer player) {
		super();
		this.player = player;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String getText(Object element) {
		String result = "-";
		IStatsEntry entry = ((IEntry) element).getPlayerEntry(this.player);
		IStatValue value = entry.getValue();
		if (value != null) {
			result = value.getValueAsString();
		}
		return result;
	}

}
