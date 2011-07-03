package org.opendarts.prototype.ui.application;

import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * The Class OpenDartsWorkbenchWindowAdvisor.
 */
public class OpenDartsWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	/**
	 * Instantiates a new application workbench window advisor.
	 *
	 * @param configurer the configurer
	 */
	public OpenDartsWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#createActionBarAdvisor(org.eclipse.ui.application.IActionBarConfigurer)
	 */
	@Override
	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer) {
		return new OpenDartsActionBarAdvisor(configurer);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#preWindowOpen()
	 */
	@Override
	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = this.getWindowConfigurer();
		configurer.setInitialSize(new Point(1024, 786));
		configurer.setTitle("OpenDarts");
		configurer.setShowFastViewBars(true);
		configurer.setShowProgressIndicator(true);
	}
}
