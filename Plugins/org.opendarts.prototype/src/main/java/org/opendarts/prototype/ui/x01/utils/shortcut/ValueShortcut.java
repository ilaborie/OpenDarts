package org.opendarts.prototype.ui.x01.utils.shortcut;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.opendarts.prototype.internal.model.dart.ThreeDartsThrow;
import org.opendarts.prototype.internal.model.dart.x01.WinningX01DartsThrow;
import org.opendarts.prototype.internal.model.game.x01.GameX01;
import org.opendarts.prototype.model.dart.InvalidDartThrowException;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.service.game.IGameService;
import org.opendarts.prototype.ui.utils.shortcut.AbstractShortcut;
import org.opendarts.prototype.ui.utils.shortcut.IShortcut;
import org.opendarts.prototype.ui.x01.utils.DartThrowUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ValueShortcut.
 */
public class ValueShortcut extends AbstractShortcut implements IShortcut {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(ValueShortcut.class);

	/** The value. */
	private final int value;

	/**
	 * Instantiates a new value shortcut.
	 *
	 * @param keyCode the key code
	 * @param stateMask the state mask
	 * @param value the value
	 */
	public ValueShortcut(int keyCode, int stateMask, int value) {
		super(keyCode, stateMask);
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.ui.x01.utils.IShortcut#getLabel()
	 */
	@Override
	public String getLabel() {
		return String.valueOf(this.value);
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

		// handle score
		Integer leftScore = game.getScore(player);
		ThreeDartsThrow dartThrow;
		try {
			dartThrow = dartThrowUtil.getDartThrow(this.value, leftScore);
			if ((dartThrow == null) && (inputText != null)
					&& !inputText.isDisposed()) {
				inputText.setFocus();
				inputText.selectAll();
			} else if (dartThrow instanceof WinningX01DartsThrow) {
				gameService.addWinningPlayerThrow(game, player, dartThrow);
			} else {
				gameService.addPlayerThrow(game, player, dartThrow);
			}
		} catch (NumberFormatException e) {
			LOG.warn("Invalid format: " + e);
			String msg = "Not a number!";
			this.applyError(msg, dec, inputText);
		} catch (InvalidDartThrowException e) {
			String msg = e.getMessage();
			this.applyError(msg, dec, inputText);
		}
	}

}
