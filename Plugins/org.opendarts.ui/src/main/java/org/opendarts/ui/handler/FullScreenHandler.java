package org.opendarts.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.custom.CBanner;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * The Class NewGameHandler.
 */
public class FullScreenHandler extends AbstractHandler implements IHandler {

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Command command = event.getCommand();
		boolean oldValue = HandlerUtil.toggleCommandState(command);
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		// Set full screen
		Shell shell = window.getShell();
		shell.setFullScreen(!oldValue);
		// Hide status and menu bar
		Control[] children = shell.getChildren();
		for (Control child : children) {
			if (child.getClass().equals(CBanner.class)) {
				child.setVisible(oldValue);
			}
		}
		shell.layout();
		return null;
	}

}
