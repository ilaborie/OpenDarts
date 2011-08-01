package org.opendarts.ui.x01.utils.shortcut;

import java.text.MessageFormat;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.opendarts.core.model.dart.InvalidDartThrowException;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.service.game.IGameService;
import org.opendarts.core.x01.model.DartX01Util;
import org.opendarts.core.x01.model.GameX01;
import org.opendarts.core.x01.model.WinningX01DartsThrow;
import org.opendarts.ui.utils.shortcut.AbstractShortcut;
import org.opendarts.ui.utils.shortcut.IShortcut;

/**
 * The Class ValueShortcut.
 */
public class FinishShortcut extends AbstractShortcut implements IShortcut {

	/** The nb darts. */
	private final int nbDarts;

	/**
	 * Instantiates a new value shortcut.
	 *
	 * @param keyCode the key code
	 * @param stateMask the state mask
	 * @param value the value
	 */
	public FinishShortcut(int keyCode, int stateMask, int nbDarts) {
		super(keyCode, stateMask);
		this.nbDarts = nbDarts;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.ui.x01.utils.IShortcut#getLabel()
	 */
	@Override
	public String getLabel() {
		switch (this.nbDarts) {
			case 1:
				return "1st Dart";
			case 2:
				return "1nd Dart";
			case 3:
				return "3rd Dart";
			default:
				return MessageFormat.format("{0} Darts", this.nbDarts);
		}
	}

	@Override
	public String getKeyLabel() {
		return super.getKeyLabel();
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
		try {
			Integer leftScore = game.getScore(player);
			if (DartX01Util.couldFinish(leftScore, this.nbDarts)) {
				WinningX01DartsThrow dartThrow = new WinningX01DartsThrow(
						leftScore, this.nbDarts);
				gameService.addWinningPlayerThrow(game, player, dartThrow);
			} else {
				String msg = MessageFormat.format(
						"Could not finish {0} with {1} dart(s)", leftScore,
						this.nbDarts);
				this.applyError(msg, dec, inputText);
			}
		} catch (InvalidDartThrowException e) {
			String msg = e.getMessage();
			this.applyError(msg, dec, inputText);
		}
	}

}
