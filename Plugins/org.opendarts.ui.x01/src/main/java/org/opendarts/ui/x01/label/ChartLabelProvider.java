package org.opendarts.ui.x01.label;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.opendarts.ui.stats.model.IChart;
import org.opendarts.ui.x01.ISharedImages;
import org.opendarts.ui.x01.X01UiPlugin;
import org.opendarts.ui.x01.chart.game.GameCategoryChartX01;
import org.opendarts.ui.x01.chart.game.GameHistoryChartX01;
import org.opendarts.ui.x01.chart.game.GamePieChartX01;
import org.opendarts.ui.x01.chart.session.SessionCategoryChartX01;
import org.opendarts.ui.x01.chart.session.SessionHistoryChartX01;
import org.opendarts.ui.x01.chart.session.SessionPieChartX01;
import org.opendarts.ui.x01.chart.session.SessionProgressChartX01;
import org.opendarts.ui.x01.chart.set.SetCategoryChartX01;
import org.opendarts.ui.x01.chart.set.SetHistoryChartX01;
import org.opendarts.ui.x01.chart.set.SetPieChartX01;
import org.opendarts.ui.x01.chart.set.SetProgressChartX01;

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
		Image result = null;

		if (element instanceof IChart) {
			IChart chart = (IChart) element;
			if (chart.getStatKey().contains(".time")) {
				result = X01UiPlugin.getImage(ISharedImages.IMG_CHART_TIME);
			} else if (element instanceof SessionCategoryChartX01) {
				result = X01UiPlugin.getImage(ISharedImages.IMG_CHART_BAR);
			} else if (element instanceof SetCategoryChartX01) {
				result = X01UiPlugin.getImage(ISharedImages.IMG_CHART_BAR);
			} else if (element instanceof GameCategoryChartX01) {
				result = X01UiPlugin.getImage(ISharedImages.IMG_CHART_BAR);

			} else if (element instanceof SessionProgressChartX01) {
				result = X01UiPlugin.getImage(ISharedImages.IMG_CHART_CURVE);
			} else if (element instanceof SetProgressChartX01) {
				result = X01UiPlugin.getImage(ISharedImages.IMG_CHART_CURVE);

			} else if (element instanceof SessionPieChartX01) {
				result = X01UiPlugin.getImage(ISharedImages.IMG_CHART_PIE);
			} else if (element instanceof SetPieChartX01) {
				result = X01UiPlugin.getImage(ISharedImages.IMG_CHART_PIE);
			} else if (element instanceof GamePieChartX01) {
				result = X01UiPlugin.getImage(ISharedImages.IMG_CHART_PIE);

			} else if (element instanceof SessionHistoryChartX01) {
				result = X01UiPlugin.getImage(ISharedImages.IMG_CHART_CURVE);
			} else if (element instanceof SetHistoryChartX01) {
				result = X01UiPlugin.getImage(ISharedImages.IMG_CHART_CURVE);
			} else if (element instanceof GameHistoryChartX01) {
				result = X01UiPlugin.getImage(ISharedImages.IMG_CHART_CURVE);
			}
		}
		if (result == null) {
			result = super.getImage(element);
		}
		return result;
	}

}
