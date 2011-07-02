package org.opendarts.prototype.ui.handler;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.opendarts.prototype.ProtoPlugin;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.game.IGameDefinition;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.model.session.ISession;
import org.opendarts.prototype.model.session.ISet;
import org.opendarts.prototype.service.game.IGameService;
import org.opendarts.prototype.service.session.ISessionService;
import org.opendarts.prototype.service.session.ISetService;
import org.opendarts.prototype.ui.dialog.NewGameDialog;
import org.opendarts.prototype.ui.editor.GameEditorInput;
import org.opendarts.prototype.ui.x01.editor.GameX01Editor;
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
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();

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

			// GameX01
			IGameService gameService = ProtoPlugin
					.getService(IGameService.class);
			IGameDefinition gameDef = dialog.getGameDefinition();
			List<IPlayer> players = dialog.getPlayers();
			IGame game = gameService.createGame(set, gameDef, players);

			LOG.info("New game created: {}", game);

			// Open Editor
			this.openEditor(page, game);
		} else {
			LOG.info("Cancel NewGame creation");
		}

		return null;
	}

	/**
	 * Open Game editor.
	 *
	 * @param page the page
	 * @param game the game
	 */
	private void openEditor(IWorkbenchPage page, IGame game) {
		GameEditorInput input = new GameEditorInput(game);
		try {
			page.openEditor(input, GameX01Editor.ID);
		} catch (PartInitException e) {
			LOG.error("Could not open editor", e);
		}
	}

}
