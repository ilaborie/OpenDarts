/*
 * 
 */
package org.opendarts.prototype.ui.x01.label;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.TextStyle;
import org.opendarts.prototype.internal.model.dart.ThreeDartThrow;
import org.opendarts.prototype.internal.model.dart.x01.WinningX01DartsThrow;
import org.opendarts.prototype.internal.model.game.x01.DummyX01Entry;
import org.opendarts.prototype.internal.model.game.x01.GameX01;
import org.opendarts.prototype.internal.model.game.x01.GameX01Entry;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.ui.utils.OpenDartsFormsToolkit;

/**
 * The Class ScoreLabelProvider.
 */
public class ToGoLabelProvider extends ColumnLabelProvider implements
		IStyledLabelProvider {

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
			ThreeDartThrow dartThrow = gameEntry.getPlayerThrow().get(
					this.player);
			if (dartThrow == null) {
				result = "";
			} else {
				GameX01 game = gameEntry.getGame();
				result = String.valueOf(game.getScore(this.player));
			}
			return result;
		} else if (element instanceof DummyX01Entry) {
			DummyX01Entry entry = (DummyX01Entry) element;
			return String.valueOf(entry.getGame().getScoreToDo());
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
	 * @see org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider#getStyledText(java.lang.Object)
	 */
	// FIXME solve issue
	@Override
	public StyledString getStyledText(final Object element) {
		String text = this.getText(element);

		final int score;
		if (element instanceof GameX01Entry) {
			GameX01Entry gameEntry = (GameX01Entry) element;
			ThreeDartThrow dartThrow = gameEntry.getPlayerThrow().get(
					this.player);
			if (dartThrow != null) {
				score = dartThrow.getScore();
			} else {
				score = 0;
			}
		} else {
			score = 0;
		}

		Styler styler = new Styler() {
			@Override
			public void applyStyles(TextStyle textStyle) {
				textStyle.background = ToGoLabelProvider.this
						.getBackground(element);
				textStyle.font = ToGoLabelProvider.this.getFont(element);
				textStyle.foreground = ToGoLabelProvider.this
						.getForeground(element);
				textStyle.underline = ((score / 111) > 0)
						&& ((score % 111) == 0);
			}
		};

		StyledString result = new StyledString(text, styler);

		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getBackground(java.lang.Object)
	 */
	@Override
	public Color getBackground(Object element) {
		if (element instanceof GameX01Entry) {
			GameX01Entry entry = (GameX01Entry) element;
			ThreeDartThrow dartThrow = entry.getPlayerThrow().get(this.player);
			if (dartThrow instanceof WinningX01DartsThrow) {
				return OpenDartsFormsToolkit.getToolkit().getColors()
						.getColor(OpenDartsFormsToolkit.COLOR_WINNING);
			}
		}
		return super.getBackground(element);
	}

}
