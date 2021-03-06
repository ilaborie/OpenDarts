package org.opendarts.ui.x01.utils.shortcut;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.opendarts.core.model.dart.InvalidDartThrowException;
import org.opendarts.core.model.dart.impl.ThreeDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.service.game.IGameService;
import org.opendarts.core.x01.model.GameX01;
import org.opendarts.core.x01.model.WinningX01DartsThrow;
import org.opendarts.ui.utils.shortcut.AbstractShortcut;
import org.opendarts.ui.utils.shortcut.IShortcut;
import org.opendarts.ui.x01.utils.DartThrowUtil;

/**
 * The Class ValueShortcut.
 */
public class LeftValueShortcut extends AbstractShortcut implements IShortcut {

	/**
	 * Instantiates a new value shortcut.
	 *
	 * @param keyCode the key code
	 * @param stateMask the state mask
	 * @param value the value
	 */
	public LeftValueShortcut(int keyCode, int stateMask) {
		super(keyCode, stateMask);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.ui.x01.utils.IShortcut#getLabel()
	 */
	@Override
	public String getLabel() {
		return "Left";
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.ui.x01.utils.IShortcut#execute(org.eclipse.swt.widgets.Shell, org.eclipse.swt.widgets.Text, org.opendarts.prototype.model.game.IGame, org.opendarts.prototype.model.player.IPlayer, org.eclipse.jface.fieldassist.ControlDecoration)
	 */
	@Override
	public void execute(Shell parentShell, Text inputText, IGame igame,
			IPlayer player, ControlDecoration dec) {
		// decoration
		if (dec != null) {
			dec.hide();
		}

		GameX01 game = (GameX01) igame;
		IGameService gameService = game.getParentSet().getGameDefinition()
				.getGameService();
		DartThrowUtil dartThrowUtil = new DartThrowUtil(parentShell, game,
				player);

		try {
			String text = inputText.getText();
			if (!text.isEmpty()) {
				Integer score = Integer.parseInt(text);
				Integer leftScore = game.getScore(player);
				int value = leftScore - score;
				// handle score
				ThreeDartsThrow dartThrow = dartThrowUtil.getDartThrow(value,
						leftScore);
				if ((dartThrow == null) && !inputText.isDisposed()) {
					inputText.setFocus();
					inputText.selectAll();
				} else if (dartThrow instanceof WinningX01DartsThrow) {
					gameService.addWinningPlayerThrow(game, player, dartThrow);
				} else {
					gameService.addPlayerThrow(game, player, dartThrow);

				}
			}
		} catch (InvalidDartThrowException e) {
			String msg = e.getMessage();
			this.applyError(msg, dec, inputText);
		}
	}
}
