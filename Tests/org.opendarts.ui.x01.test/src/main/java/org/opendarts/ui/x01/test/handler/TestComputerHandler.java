package org.opendarts.ui.x01.test.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.opendarts.core.service.player.IPlayerService;
import org.opendarts.core.service.session.ISessionService;
import org.opendarts.core.service.session.ISetService;
import org.opendarts.ui.OpenDartsUiPlugin;
import org.opendarts.ui.x01.test.dialog.ComputerLevelUITester;

/**
 * The Class TestComputerHandler.
 */
public class TestComputerHandler extends AbstractHandler implements IHandler {

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ComputerLevelUITester dialog = new ComputerLevelUITester();
		dialog.setPlayerService(OpenDartsUiPlugin.getService(IPlayerService.class));
		dialog.setSessionService(OpenDartsUiPlugin.getService(ISessionService.class));
		dialog.setSetService(OpenDartsUiPlugin.getService(ISetService.class));
		
		dialog.startup();
		return null;
	}
}