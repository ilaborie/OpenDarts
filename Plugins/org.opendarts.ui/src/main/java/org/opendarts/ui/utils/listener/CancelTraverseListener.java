package org.opendarts.ui.utils.listener;

import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;

/**
 * The Class CancelTraverseListene.
 */
public class CancelTraverseListener implements TraverseListener {

	/**
	 * Instantiates a new cancel traverse listener.
	 */
	public CancelTraverseListener() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.TraverseListener#keyTraversed(org.eclipse.swt.events.TraverseEvent)
	 */
	@Override
	public void keyTraversed(TraverseEvent e) {
		e.doit = false;
	}
}
