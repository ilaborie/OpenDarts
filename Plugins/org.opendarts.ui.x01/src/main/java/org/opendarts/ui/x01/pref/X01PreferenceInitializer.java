package org.opendarts.ui.x01.pref;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;
import org.opendarts.core.x01.service.StatsX01Service;
import org.opendarts.ui.x01.X01UiPlugin;

/**
 * The Class X01PreferenceInitializer.
 */
public class X01PreferenceInitializer extends AbstractPreferenceInitializer
		implements IX01Prefs {

	/**
	 * Instantiates a new open darts preference initializer.
	 */
	public X01PreferenceInitializer() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = X01UiPlugin.getX01Preferences();

		// Omit DEFAUlT_GAME_DEFINITION

		// Basic
		store.setDefault(SWITCH_USER_POSITION, false);
		store.setDefault(SHOW_SCORE_LEFT, true);
		store.setDefault(SHOW_ROW_NUMBER, false);

		// Colors
		RGB rgbNormal = new RGB(121, 121, 121);
		RGB rgb60 = new RGB(0, 128, 64);
		RGB rgb100 = new RGB(0, 0, 255);
		RGB rgb180 = new RGB(128, 0, 128);

		PreferenceConverter.setDefault(store, COLOR_NORMAL, rgbNormal);
		PreferenceConverter.setDefault(store, COLOR_60, rgb60);
		PreferenceConverter.setDefault(store, COLOR_100, rgb100);
		PreferenceConverter.setDefault(store, COLOR_180, rgb180);

		store.setDefault(COLOR_NORMAL_GRADIENT, false);
		store.setDefault(COLOR_60_GRADIENT, true);
		store.setDefault(COLOR_100_GRADIENT, true);

		List<String> stats;
		// Session Stats
		stats = Arrays.asList(StatsX01Service.SESSION_TONS,
				StatsX01Service.SESSION_180s, StatsX01Service.SESSION_60_PLUS,
				StatsX01Service.SESSION_TONS_PLUS,
				StatsX01Service.SESSION_AVG_DART,
				StatsX01Service.SESSION_AVG_3_DARTS,
				StatsX01Service.SESSION_AVG_LEG,
				StatsX01Service.SESSION_BEST_LEG,
				StatsX01Service.SESSION_BEST_OUT);
		store.setDefault(SESSION_STATS,
				PreferencesConverterUtils.getListAsString(stats));

		// Set Stats
		stats = Arrays.asList(StatsX01Service.SET_AVG_DART,
				StatsX01Service.SET_AVG_3_DARTS, StatsX01Service.SET_AVG_LEG,
				StatsX01Service.SET_BEST_LEG);
		store.setDefault(SET_STATS,
				PreferencesConverterUtils.getListAsString(stats));

		// Leg Stats
		stats = Arrays.asList(StatsX01Service.GAME_AVG_DART,
				StatsX01Service.GAME_AVG_3_DARTS);
		store.setDefault(GAME_STATS,
				PreferencesConverterUtils.getListAsString(stats));
	}

}
