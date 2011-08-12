package org.opendarts.ui.x01.pref;

import java.util.List;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.opendarts.core.x01.OpenDartsX01Bundle;
import org.opendarts.ui.label.StatsLabelProvider;

/**
 * The Class StatsSelectionDialog.
 */
public class StatsSelectionDialog extends ElementListSelectionDialog {

	/** The stats. */
	private String stats;

	/**
	 * Instantiates a new player selection dialog.
	 *
	 * @param parent the parent
	 * @param allStats the all stats
	 */
	public StatsSelectionDialog(Shell parent, List<String> allStats) {
		super(parent, new StatsLabelProvider(OpenDartsX01Bundle.getStatsX01Service()));
		this.setTitle("Select Statistics");

		this.setElements(allStats.toArray());
		this.setEmptyListMessage("Please select at least one statistics");
		this.setHelpAvailable(false);
		this.setMessage("Choose statistics");
		this.setMultipleSelection(false);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.SelectionStatusDialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		if (this.getSelectedElements().length > 0) {
			this.stats = (String) this.getSelectedElements()[0];
		} else {
			this.stats = null;
		}
		super.okPressed();
	}

	/**
	 * Gets the stats.
	 *
	 * @return the stats
	 */
	public String getStats() {
		return this.stats;
	}

}
