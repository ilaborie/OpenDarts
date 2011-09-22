package org.opendarts.ui.x01.defi.label;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.opendarts.ui.x01.label.StatsX01LabelProvider;

/**
 * The Class StatsX01DefiLabelProvider.
 */
public class StatsX01DefiLabelProvider extends StatsX01LabelProvider {

	/** The bundle. */
	private final ResourceBundle bundle;

	/**
	 * Instantiates a new stats x01 label provider.
	 */
	public StatsX01DefiLabelProvider() {
		super();
		this.bundle = ResourceBundle.getBundle("Messages");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		String result = null;
		try {
			result = this.bundle.getString(element.toString());
		} catch (MissingResourceException mre) {
			result = super.getText(element);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipText(java.lang.Object)
	 */
	@Override
	public String getToolTipText(Object element) {
		String result = null;
		try {
			result = this.bundle.getString("description." + element);
		} catch (MissingResourceException mre) {
			result = super.getToolTipText(element);
		}
		return result;
	}

}
