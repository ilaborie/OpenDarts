package org.opendarts.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.handlers.HandlerUtil;
import org.opendarts.core.model.game.IGameDefinition;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.service.session.ISessionService;
import org.opendarts.core.service.session.ISetService;
import org.opendarts.ui.OpenDartsUiPlugin;
import org.opendarts.ui.dialog.NewSessionDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class NewGameHandler.
 */
public class NewSessionHandler extends AbstractHandler implements IHandler {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(NewSessionHandler.class);

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		LOG.info("New Session...");

		// The session
		ISessionService sessionService = OpenDartsUiPlugin
				.getService(ISessionService.class);

		// Dialog
		NewSessionDialog dialog = new NewSessionDialog(
				HandlerUtil.getActiveShell(event));
		if (dialog.open() == Window.OK) {
			IGameDefinition gameDef = dialog.getGameDefinition();
			int nbSets = dialog.getNbSets();

			// New Session
			ISession session = sessionService.createNewSession(nbSets, gameDef);

			// Create and start first set
			ISetService setService = OpenDartsUiPlugin
					.getService(ISetService.class);
			ISet set = setService.createNewSet(session, gameDef);
			setService.startSet(set);
		} else {
			LOG.info("Cancel NewSession creation");
		}
		return null;
	}

}
