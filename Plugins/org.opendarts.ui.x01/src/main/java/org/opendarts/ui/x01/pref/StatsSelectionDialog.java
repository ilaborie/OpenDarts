package org.opendarts.ui.x01.pref;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

/**
 * The Class StatsSelectionDialog.
 */
public class StatsSelectionDialog extends ElementListSelectionDialog {

	/** The stats. */
	private List<String> stats;

	/**
	 * Instantiates a new player selection dialog.
	 *
	 * @param parent the parent
	 * @param allStats the all stats
	 * @param labelProvider the label provider
	 */
	public StatsSelectionDialog(Shell parent, List<String> allStats,
			ILabelProvider labelProvider) {
		super(parent, labelProvider);
		this.setTitle("Select Statistics");

		this.setElements(allStats.toArray());
		this.setEmptyListMessage("Please select at least one statistics");
		this.setHelpAvailable(false);
		this.setMessage("Choose statistics");
		this.setMultipleSelection(true);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.SelectionStatusDialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		Object[] elements = this.getSelectedElements();
		if (elements.length > 0) {
			this.stats = new ArrayList<String>();
			for (Object obj : elements) {
				this.stats.add((String) obj);
			}
		} else {
			this.stats = Collections.emptyList();
		}
		super.okPressed();
	}

	/**
	 * Gets the stats.
	 *
	 * @return the stats
	 */
	public List<String> getStats() {
		return this.stats;
	}

}
