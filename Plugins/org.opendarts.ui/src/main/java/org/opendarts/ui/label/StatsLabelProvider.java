package org.opendarts.ui.label;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.opendarts.core.stats.service.IStatsService;

/**
 * The Class PlayerLabelProvider.
 */
public class StatsLabelProvider extends ColumnLabelProvider {

	/** The stats service. */
	private final IStatsService statsService;

	/**
	 * Instantiates a new stats label provider.
	 *
	 * @param statsService the stats service
	 */
	public StatsLabelProvider(IStatsService statsService) {
		super();
		this.statsService = statsService;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (this.statsService != null) {
			if (element instanceof String) {
				String statsKey = (String) element;
				return this.statsService.getText(statsKey);
			}
		}
		return super.getText(element);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		if (this.statsService != null) {
			if (element instanceof String) {
				String statsKey = (String) element;
				return this.statsService.getImage(statsKey);
			}
		}
		return super.getImage(element);
	}
	

}
