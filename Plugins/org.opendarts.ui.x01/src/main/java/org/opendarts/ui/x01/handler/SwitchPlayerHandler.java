package org.opendarts.ui.x01.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.handlers.HandlerUtil;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISet;
import org.opendarts.ui.editor.SetEditorInput;
import org.opendarts.ui.player.dialog.PlayersOrdersDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class SetInfoHandler.
 */
public class SwitchPlayerHandler extends AbstractHandler implements IHandler {
	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(SwitchPlayerHandler.class);

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveShell(event);
		IEditorInput editorInput = HandlerUtil.getActiveEditorInput(event);
		if (editorInput != null) {
			if (editorInput instanceof SetEditorInput) {
				SetEditorInput setEditorInput = (SetEditorInput) editorInput;
				ISet set = setEditorInput.getSet();
				if (set != null) {
					IGame game = set.getCurrentGame();
					if (game != null && game.getGameEntries().size() == 1) {
						this.switchPlayers(game, shell);
					}
				}
			}
		}
		return null;
	}

	/**
	 * Switch players.
	 *
	 * @param game the game
	 * @param shell 
	 */
	private void switchPlayers(IGame game, Shell shell) {
		LOG.info("Switch player for {}", game);
		List<IPlayer> players = new ArrayList<IPlayer>(game.getPlayers());
		if (players.size() > 2) {
			PlayersOrdersDialog dialog = new PlayersOrdersDialog(shell, players);
			if (dialog.open() != Window.OK) {
				return;
			}
			players = dialog.getPlayers();
		} else {
			Collections.rotate(players, 1);
		}
		// update Game Definition
		game.updatePlayers(players);
	}
}