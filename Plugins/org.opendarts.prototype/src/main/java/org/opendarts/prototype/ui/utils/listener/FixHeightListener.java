package org.opendarts.prototype.ui.utils.listener;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * Fix component height (table)
 */
public class FixHeightListener implements Listener {

	/** The height. */
	private final int height;

	/**
	 * Instantiates a new fix height listener.
	 *
	 * @param height the height
	 */
	public FixHeightListener(int height) {
		super();
		this.height = height;
	}

	/**
	 * Handle event.
	 *
	 * @param event the event
	 */
	@Override
	public void handleEvent(Event event) {
		event.height = this.height;
	}

}
