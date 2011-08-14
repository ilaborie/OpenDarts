/*
 * 
 */
package org.opendarts.ui.x01.label;

import java.text.MessageFormat;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.ui.forms.IFormColors;
import org.opendarts.core.x01.model.GameX01Entry;
import org.opendarts.ui.pref.IGeneralPrefs;
import org.opendarts.ui.utils.OpenDartsFormsToolkit;
import org.opendarts.ui.x01.X01UiPlugin;
import org.opendarts.ui.x01.pref.IX01Prefs;

/**
 * The Class ScoreLabelProvider.
 */
public class TurnLabelProvider extends ColumnLabelProvider {

	private final String pattern;

	/**
	 * Instantiates a new score label provider.
	 *
	 * @param player the player
	 */
	public TurnLabelProvider() {
		super();
		IPreferenceStore store = X01UiPlugin.getX01Preferences();
		if (store.getBoolean(IX01Prefs.SHOW_ROW_NUMBER)) {
			this.pattern = "#{0}";
		} else {
			this.pattern = "{1,number,00}";
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof GameX01Entry) {
			GameX01Entry gameEntry = (GameX01Entry) element;
			int round = gameEntry.getRound();
			return MessageFormat.format(this.pattern, round, round * 3);
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
		return OpenDartsFormsToolkit.getFont(IGeneralPrefs.FONT_SCORE_SHEET);
	}

}
