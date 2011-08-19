package org.opendarts.ui.pref;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.opendarts.ui.OpenDartsUiPlugin;

/**
 * The Class OpenDartsPreferenceInitializer.
 */
public class OpenDartsPreferenceInitializer extends
		AbstractPreferenceInitializer implements IGeneralPrefs {

	/**
	 * Instantiates a new open darts preference initializer.
	 */
	public OpenDartsPreferenceInitializer() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = OpenDartsUiPlugin.getOpenDartsPreference();

		// FullScreen
		store.setDefault(STARTING_FULLSCREN, true);

		// Colors
		PreferenceConverter.setDefault(store, COLOR_ACTIVE, new RGB(0xF2, 0xDA,
				0x2E));
		PreferenceConverter.setDefault(store, COLOR_INACTIVE, new RGB(0xC1,
				0xC1, 0xC1));
		PreferenceConverter
				.setDefault(store, COLOR_WINNING, Display.getDefault()
						.getSystemColor(SWT.COLOR_LIST_SELECTION).getRGB());
		PreferenceConverter.setDefault(store, COLOR_BROKEN, new RGB(0xD0, 0x6D,
				0x58));

		// Font
		Font defaultFont = JFaceResources.getDefaultFont();

		FontData[] scoreSheet = this.getFontData(defaultFont, 32, SWT.NORMAL);
		FontData[] scoreSheetLeft = this.getFontData(defaultFont, 32, SWT.BOLD);
		FontData[] stats = this.getFontData(defaultFont, 18, SWT.NORMAL);
		FontData[] statsLabel = this.getFontData(defaultFont, 16, SWT.BOLD);
		FontData[] scoreInput = this.getFontData(defaultFont, 64, SWT.BOLD);
		FontData[] scoreLeft = this.getFontData(defaultFont, 96, SWT.BOLD);

		PreferenceConverter.setDefault(store, FONT_SCORE_SHEET, scoreSheet);
		PreferenceConverter.setDefault(store, FONT_STATS, stats);
		PreferenceConverter.setDefault(store, FONT_STATS_LABEL, statsLabel);
		PreferenceConverter.setDefault(store, FONT_SCORE_SHEET_LEFT,
				scoreSheetLeft);
		PreferenceConverter.setDefault(store, FONT_SCORE_INPUT, scoreInput);
		PreferenceConverter.setDefault(store, FONT_SCORE_LEFT, scoreLeft);
	}

	/**
	 * Gets the font data.
	 *
	 * @param font the font
	 * @param height the height
	 * @return the font data
	 */
	private FontData[] getFontData(Font font, int height, int style) {
		FontData[] fontData = font.getFontData();
		for (FontData element : fontData) {
			element.setHeight(height);
			element.setStyle(style);
		}
		return fontData;
	}

}
