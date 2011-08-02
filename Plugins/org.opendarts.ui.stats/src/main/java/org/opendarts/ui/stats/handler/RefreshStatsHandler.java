package org.opendarts.ui.stats.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.opendarts.ui.stats.view.StatsDetailView;

/**
 * The Class RefreshStatsHandler.
 */
public class RefreshStatsHandler extends AbstractHandler implements IHandler {

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPart part = HandlerUtil.getActivePart(event);
		if (part!= null) {
			if (part instanceof StatsDetailView) {
				StatsDetailView view = (StatsDetailView) part;
				view.refresh();
			}
		}
		return null;
	}
}