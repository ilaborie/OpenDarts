package org.opendarts.prototype.ui.x01.utils;

import org.eclipse.swt.widgets.Shell;
import org.opendarts.prototype.internal.model.dart.ThreeDartThrow;
import org.opendarts.prototype.internal.model.game.x01.BrokenX01DartsThrow;
import org.opendarts.prototype.internal.model.game.x01.GameX01;
import org.opendarts.prototype.internal.model.game.x01.WinningX01DartsThrow;
import org.opendarts.prototype.model.dart.InvalidDartThrowException;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.ui.x01.dialog.GameX01FinishDialog;

/**
 * The Class DartThrowUtil.
 */
public class DartThrowUtil {

	/** The parent shell. */
	private final Shell parentShell;

	/** The game. */
	private final GameX01 game;

	/** The player. */
	private final IPlayer player;

	/**
	 * Instantiates a new dart throw util.
	 *
	 * @param parentShell the parent shell
	 * @param game the game
	 * @param player the player
	 */
	public DartThrowUtil(Shell parentShell, GameX01 game, IPlayer player) {
		super();
		this.parentShell = parentShell;
		this.game = game;
		this.player = player;
	}

	/**
	 * Gets the dart throw.
	 *
	 * @param value the value
	 * @param leftScore the left score
	 * @return the dart throw
	 * @throws NumberFormatException the number format exception
	 * @throws InvalidDartThrowException the invalid dart throw exception
	 */
	public ThreeDartThrow getDartThrow(String value, Integer leftScore)
			throws NumberFormatException, InvalidDartThrowException {
		ThreeDartThrow result = new ThreeDartThrow(value);
		// check finished
		if (leftScore == result.getScore()) {
			// Dialog for Broken, 1, 2, 3 darts finish
			GameX01FinishDialog dialog = new GameX01FinishDialog(
					this.parentShell, game, player);
			dialog.open();
			int nbDarts = dialog.getNbDarts();
			if (nbDarts > 0) {
				result = new WinningX01DartsThrow(result, nbDarts);
			} else {
				result = new BrokenX01DartsThrow(result);
			}
		}
		return result;
	}

}
