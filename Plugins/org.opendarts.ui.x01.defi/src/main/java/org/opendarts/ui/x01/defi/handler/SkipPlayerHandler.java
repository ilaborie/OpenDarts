package org.opendarts.ui.x01.defi.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.handlers.HandlerUtil;
import org.opendarts.core.model.dart.InvalidDartThrowException;
import org.opendarts.core.model.dart.impl.SkipedDartsThrow;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.service.game.IGameService;
import org.opendarts.core.x01.defi.model.GameX01Defi;
import org.opendarts.ui.editor.SetEditorInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class SetInfoHandler.
 */
public class SkipPlayerHandler extends AbstractHandler implements IHandler {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(SkipPlayerHandler.class);

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorInput editorInput = HandlerUtil.getActiveEditorInput(event);
		if (editorInput != null) {
			if (editorInput instanceof SetEditorInput) {
				SetEditorInput setEditorInput = (SetEditorInput) editorInput;
				ISet set = setEditorInput.getSet();
				if (set != null) {
					GameX01Defi game = (GameX01Defi) set.getCurrentGame();
					if (game != null) {
						IGameService gameService = set.getGameService();
						IPlayer player = game.getCurrentPlayer();

						try {
							gameService.addPlayerThrow(game, player,
									new SkipedDartsThrow());
						} catch (InvalidDartThrowException e) {
							LOG.warn("WTF !", e);
						}
					}
				}
			}
		}
		return null;
	}

}