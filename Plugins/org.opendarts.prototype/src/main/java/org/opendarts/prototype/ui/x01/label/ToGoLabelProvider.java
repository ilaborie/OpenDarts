/*
 * 
 */
package org.opendarts.prototype.ui.x01.label;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.opendarts.prototype.internal.model.dart.ThreeDartsThrow;
import org.opendarts.prototype.internal.model.dart.x01.WinningX01DartsThrow;
import org.opendarts.prototype.internal.model.game.x01.GameX01;
import org.opendarts.prototype.internal.model.game.x01.GameX01Entry;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.ui.utils.OpenDartsFormsToolkit;

/**
 * The Class ScoreLabelProvider.
 */
public class ToGoLabelProvider extends ColumnLabelProvider {

	/** The player. */
	private final IPlayer player;

	/**
	 * Instantiates a new score label provider.
	 *
	 * @param player the player
	 */
	public ToGoLabelProvider(IPlayer player) {
		super();
		this.player = player;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof GameX01Entry) {
			String result;
			GameX01Entry gameEntry = (GameX01Entry) element;
			ThreeDartsThrow dartThrow = gameEntry.getPlayerThrow().get(
					this.player);
			if (dartThrow == null) {
				result = "";
			} else {
				GameX01 game = gameEntry.getGame();
				result = String.valueOf(game.getScore(this.player));
			}
			return result;
		}
		return super.getText(element);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getFont(java.lang.Object)
	 */
	@Override
	public Font getFont(Object element) {
		return OpenDartsFormsToolkit
				.getFont(OpenDartsFormsToolkit.FONT_SCORE_SHEET_BOLD);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getBackground(java.lang.Object)
	 */
	@Override
	public Color getBackground(Object element) {
		if (element instanceof GameX01Entry) {
			GameX01Entry entry = (GameX01Entry) element;
			ThreeDartsThrow dartThrow = entry.getPlayerThrow().get(this.player);
			if (dartThrow instanceof WinningX01DartsThrow) {
				return OpenDartsFormsToolkit.getToolkit().getColors()
						.getColor(OpenDartsFormsToolkit.COLOR_WINNING);
			}
		}
		return super.getBackground(element);
	}

}
