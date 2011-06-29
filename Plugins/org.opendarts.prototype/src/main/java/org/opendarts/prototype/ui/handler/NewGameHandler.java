package org.opendarts.prototype.ui.handler;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.handlers.HandlerUtil;
import org.opendarts.prototype.ProtoPlugin;
import org.opendarts.prototype.model.IGame;
import org.opendarts.prototype.model.IGameDefinition;
import org.opendarts.prototype.model.IPlayer;
import org.opendarts.prototype.model.ISession;
import org.opendarts.prototype.model.ISet;
import org.opendarts.prototype.service.IGameService;
import org.opendarts.prototype.service.ISessionService;
import org.opendarts.prototype.service.ISetService;
import org.opendarts.prototype.ui.dialog.NewGameDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class NewGameHandler.
 */
public class NewGameHandler extends AbstractHandler implements IHandler {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(NewGameHandler.class);

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		LOG.info("New Game...");
		LOG.info("New Game...");

		// The session
		ISessionService sessionService = ProtoPlugin
				.getService(ISessionService.class);
		ISession session = sessionService.getSession();

		// Dialog
		NewGameDialog dialog = new NewGameDialog(
				HandlerUtil.getActiveShell(event));
		if (dialog.open() == Window.OK) {
			// Set
			ISetService setService = ProtoPlugin.getService(ISetService.class);
			int nbGame = dialog.getNumberSetGames();
			ISet set = setService.createNewSet(session, nbGame);

			// Game
			IGameService gameService = ProtoPlugin
					.getService(IGameService.class);
			IGameDefinition gameDef = dialog.getGameDefinition();
			List<IPlayer> players = dialog.getPlayers();
			IGame game = gameService.createGame(set, gameDef, players);

			LOG.info("New game created: {}", game);
		} else {
			LOG.info("Canncel NewGame creation");
		}

		return null;
	}

}
