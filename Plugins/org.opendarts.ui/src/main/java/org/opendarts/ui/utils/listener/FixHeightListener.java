package org.opendarts.ui.utils.listener;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * Fix component height (table)
 */
public class FixHeightListener implements Listener {
	/**
	 * Instantiates a new fix height listener.
	 *
	 * @param height the height
	 */
	public FixHeightListener() {
		super();
	}

	/**
	 * Handle event.
	 *
	 * @param event the event
	 */
	@Override
	public void handleEvent(Event event) {
		double ratio = 1d;
		if (false) {
			// if win XP ???
			ratio = 2d;
		}
		event.height = (int) (ratio * event.gc.getFontMetrics().getHeight());
	}
}
