/*
 * 
 */
package org.opendarts.ui.x01.label;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.ui.forms.IFormColors;
import org.opendarts.core.x01.model.GameX01Entry;
import org.opendarts.ui.pref.IGeneralPrefs;
import org.opendarts.ui.utils.OpenDartsFormsToolkit;

/**
 * The Class ScoreLabelProvider.
 */
public class TurnLabelProvider extends ColumnLabelProvider {

	/** The formatter. */
	private final NumberFormat formatter;

	/**
	 * Instantiates a new score label provider.
	 *
	 * @param player the player
	 */
	public TurnLabelProvider() {
		super();
		this.formatter = new DecimalFormat("00");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof GameX01Entry) {
			GameX01Entry gameEntry = (GameX01Entry) element;
			int round = gameEntry.getRound();
			return this.formatter.format(round * 3);
			//			return MessageFormat.format("#{1} ({0})", (round * 3), round);
		}
		return super.getText(element);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getBackground(java.lang.Object)
	 */
	@Override
	public Color getBackground(Object element) {
		return OpenDartsFormsToolkit.getToolkit().getColors()
				.getColor(IFormColors.H_GRADIENT_START);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getForeground(java.lang.Object)
	 */
	@Override
	public Color getForeground(Object element) {
		return OpenDartsFormsToolkit.getToolkit().getColors()
				.getColor(IFormColors.TITLE);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getFont(java.lang.Object)
	 */
	@Override
	public Font getFont(Object element) {
		return OpenDartsFormsToolkit
				.getFont(IGeneralPrefs.FONT_SCORE_SHEET);
	}

}
