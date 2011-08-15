package org.opendarts.ui.x01.label;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class StatsX01LabelProvider.
 */
public class StatsX01LabelProvider extends ColumnLabelProvider {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(StatsX01LabelProvider.class);

	/** The bundle. */
	private final ResourceBundle bundle;

	/**
	 * Instantiates a new stats x01 label provider.
	 */
	public StatsX01LabelProvider() {
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
			LOG.warn("Could not retrieve label for key {}", element);
			result = element.toString();
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
			LOG.warn("Could not retrieve label for key {}", element);
			result = element.toString();
		}
		return result;
	}

}
