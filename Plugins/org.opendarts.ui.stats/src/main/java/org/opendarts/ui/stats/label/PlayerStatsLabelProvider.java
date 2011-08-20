package org.opendarts.ui.stats.label;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.IElementStats.IEntry;
import org.opendarts.core.stats.model.IStatValue;
import org.opendarts.core.stats.model.IStatsEntry;
import org.opendarts.ui.pref.IGeneralPrefs;
import org.opendarts.ui.utils.OpenDartsFormsToolkit;

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
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getBackground(java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Color getBackground(Object element) {
		IEntry entry = (IEntry) element;
		IStatValue bestValue = entry.getBestValue();
		IStatValue playerValue = entry.getPlayerEntry(this.player).getValue();
		if (bestValue!=null && bestValue.equals(playerValue)) {
			return OpenDartsFormsToolkit.getToolkit().getColors()
					.getColor(IGeneralPrefs.COLOR_WINNING);
		}
		return super.getBackground(element);
	}

}
