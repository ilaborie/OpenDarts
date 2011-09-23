package org.opendarts.ui.x01.defi.label;

import org.eclipse.swt.graphics.Image;
import org.opendarts.ui.x01.ISharedImages;
import org.opendarts.ui.x01.X01UiPlugin;
import org.opendarts.ui.x01.defi.chart.HistoryCategoryChartX01;
import org.opendarts.ui.x01.label.ChartLabelProvider;

/**
 * The Class ChartDefiLabelProvider.
 */
public class ChartDefiLabelProvider extends ChartLabelProvider {

	/**
	 * Instantiates a new chart tree label provider.
	 */
	public ChartDefiLabelProvider() {
		super();
	}


	/* (non-Javadoc)
	 * @see org.opendarts.ui.label.OpenDartsLabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		Image result;
		if (element instanceof HistoryCategoryChartX01) {
			result = X01UiPlugin.getImage(ISharedImages.IMG_CHART_CURVE);
		} else {
				result = super.getImage(element);
		}
		return result;
	}

}
