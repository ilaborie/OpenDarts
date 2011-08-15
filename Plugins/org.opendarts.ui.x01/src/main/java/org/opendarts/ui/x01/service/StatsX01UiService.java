package org.opendarts.ui.x01.service;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.opendarts.ui.stats.service.IStatsUiService;
import org.opendarts.ui.x01.label.StatsX01LabelProvider;

/**
 * The Class StatsX01UiService.
 */
public class StatsX01UiService implements IStatsUiService {

	/**
	 * Gets the stats label provider.
	 *
	 * @return the stats label provider
	 */
	@Override
	public ColumnLabelProvider getStatsLabelProvider() {
		return new StatsX01LabelProvider();
	}

}
