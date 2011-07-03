package org.opendarts.prototype.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.opendarts.prototype.ProtoPlugin;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.service.game.IGameService;
import org.opendarts.prototype.ui.editor.ISetEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class NewGameHandler.
 */
public class StartGameHandler extends AbstractHandler implements IHandler {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(StartGameHandler.class);

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		LOG.info("Start Game");

		IEditorPart editor = HandlerUtil.getActiveEditor(event);
		if (editor instanceof ISetEditor) {
			ISetEditor gameEditor = (ISetEditor) editor;
			IGame game = gameEditor.getSet().getCurrentGame();

			IGameService gameService = ProtoPlugin
					.getService(IGameService.class);
			gameService.startGame(game);
		}
		return null;
	}

}
