package org.opendarts.ui.stats.perspective;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.opendarts.ui.stats.view.StatsView;

/**
 * The Class StatsPerspective.
 */
public class StatsPerspective implements IPerspectiveFactory {

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
	 */
	@Override
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);

		layout.addStandaloneView(StatsView.VIEW_ID, true, IPageLayout.RIGHT,
				IPageLayout.RATIO_MAX, editorArea);

		//		IFolderLayout folder = layout.createFolder("detail", IPageLayout.TOP,
		//				0.5f, editorArea);
		//		folder.addPlaceholder(StatsDetailView.VIEW_ID + ":*");
		//		folder.addView(StatsDetailView.VIEW_ID);
		//
		//		layout.getViewLayout(StatsExplorerView.VIEW_ID).setCloseable(false);

		layout.setFixed(true);
	}

}
