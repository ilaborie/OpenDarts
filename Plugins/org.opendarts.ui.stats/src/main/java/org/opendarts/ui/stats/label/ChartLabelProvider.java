package org.opendarts.ui.stats.label;

import org.eclipse.swt.graphics.Image;
import org.opendarts.ui.label.OpenDartsLabelProvider;
import org.opendarts.ui.stats.model.IChart;
import org.opendarts.ui.stats.service.IStatsUiService;

/**
 * The Class ChartLabelProvider.
 */
public class ChartLabelProvider extends OpenDartsLabelProvider {

	/**
	 * Instantiates a new chart tree label provider.
	 */
	public ChartLabelProvider() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.label.OpenDartsLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		String result;
		if (element instanceof IChart) {
			IChart chart = (IChart) element;
			IStatsUiService service = chart.getService();
			result = service.getChartLabelProvider().getText(chart);
		} else {
			result = super.getText(element);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.label.OpenDartsLabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		Image result;
		if (element instanceof IChart) {
			IChart chart = (IChart) element;
			IStatsUiService service = chart.getService();
			result = service.getChartLabelProvider().getImage(chart);
		} else {
			result = super.getImage(element);
		}
		return result;
	}

}
