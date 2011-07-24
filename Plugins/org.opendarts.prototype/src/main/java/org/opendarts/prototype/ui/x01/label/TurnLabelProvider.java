/*
 * 
 */
package org.opendarts.prototype.ui.x01.label;

import java.text.MessageFormat;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.ui.forms.IFormColors;
import org.opendarts.prototype.internal.model.game.x01.DummyX01Entry;
import org.opendarts.prototype.internal.model.game.x01.GameX01Entry;
import org.opendarts.prototype.ui.utils.OpenDartsFormsToolkit;

/**
 * The Class ScoreLabelProvider.
 */
public class TurnLabelProvider extends ColumnLabelProvider {

	/**
	 * Instantiates a new score label provider.
	 *
	 * @param player the player
	 */
	public TurnLabelProvider() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof GameX01Entry) {
			GameX01Entry gameEntry = (GameX01Entry) element;
			int round = gameEntry.getRound();
			return MessageFormat.format("#{1} ({0})", (round * 3), round);
		} else if (element instanceof DummyX01Entry) {
			return "";
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
				.getFont(OpenDartsFormsToolkit.FONT_SCORE_SHEET);
	}

}
