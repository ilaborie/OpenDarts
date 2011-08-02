package org.opendarts.ui.stats.perspective;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.opendarts.ui.stats.view.StatsExplorerView;

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

		layout.addStandaloneView(StatsExplorerView.VIEW_ID, false,
				IPageLayout.LEFT, 0.25f, editorArea);

		layout.getViewLayout(StatsExplorerView.VIEW_ID).setCloseable(false);
	}

}
