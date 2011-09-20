
/*
 * 
 */
package org.opendarts.ui.x01.label;

import java.text.NumberFormat;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.opendarts.core.model.dart.impl.ThreeDartsThrow;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.x01.model.GameX01Entry;
import org.opendarts.core.x01.model.WinningX01DartsThrow;
import org.opendarts.ui.pref.IGeneralPrefs;
import org.opendarts.ui.utils.OpenDartsFormsToolkit;

/**
 * The Class ScoreLabelProvider.
 */
public class ToGoLabelProvider extends ColumnLabelProvider {

	/** The player. */
	private final IPlayer player;

	/** The use font. */
	private final boolean useFont;
	
	/** The format. */
	private final NumberFormat format;

	/**
	 * Instantiates a new score label provider.
	 *
	 * @param player the player
	 */
	public ToGoLabelProvider(IPlayer player) {
		this(player, true);
	}

	/**
	 * Instantiates a new to go label provider.
	 *
	 * @param player the player
	 * @param useFont the use font
	 */
	public ToGoLabelProvider(IPlayer player, boolean useFont) {
		super();
		this.player = player;
		this.useFont = useFont;
		this.format = NumberFormat.getIntegerInstance();
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
				Integer integer = gameEntry.getPlayerScoreLeft().get(this.player);
				result = format.format(integer);
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
		if (this.useFont) {
			return OpenDartsFormsToolkit
					.getFont(IGeneralPrefs.FONT_SCORE_SHEET_LEFT);
		}
		return super.getFont(this.player);
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
						.getColor(IGeneralPrefs.COLOR_WINNING);
			}
		}
		return super.getBackground(element);
	}

}
