package org.opendarts.ui.x01.label;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.opendarts.ui.stats.model.IChart;
import org.opendarts.ui.x01.ISharedImages;
import org.opendarts.ui.x01.X01UiPlugin;
import org.opendarts.ui.x01.model.SessionCategoryChartX01;
import org.opendarts.ui.x01.model.SessionProgressChartX01;
import org.opendarts.ui.x01.model.SessionThrowPieChartX01;
import org.opendarts.ui.x01.model.SetProgressChartX01;

/**
 * The Class ChartLabelProvider.
 */
public class ChartLabelProvider extends ColumnLabelProvider {

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
			result = ((IChart) element).getName();
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
		if (element instanceof SessionCategoryChartX01) {
			result = X01UiPlugin.getImage(ISharedImages.IMG_CHART_BAR);
		} else if (element instanceof SessionProgressChartX01) {
			result = X01UiPlugin.getImage(ISharedImages.IMG_CHART_CURVE);
		} else if (element instanceof SetProgressChartX01) {
			result = X01UiPlugin.getImage(ISharedImages.IMG_CHART_CURVE);
		} else if (element instanceof SessionThrowPieChartX01) {
			result = X01UiPlugin.getImage(ISharedImages.IMG_CHART_PIE);
		} else {
			result = super.getImage(element);
		}
		return result;
	}

}