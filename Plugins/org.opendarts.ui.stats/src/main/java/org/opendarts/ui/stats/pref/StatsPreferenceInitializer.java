package org.opendarts.ui.stats.pref;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;
import org.opendarts.ui.stats.OpenDartsStatsUiPlugin;

/**
 * The Class StatsPreferenceInitializer.
 */
public class StatsPreferenceInitializer extends AbstractPreferenceInitializer
		implements IStatsPrefs {

	/**
	 * Instantiates a new open darts preference initializer.
	 */
	public StatsPreferenceInitializer() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = OpenDartsStatsUiPlugin.getOpenDartsStats();

		store.setDefault(STORE_STATISTICS, true);
		store.setDefault(STATS_BROKEN, true);

		PreferenceConverter.setDefault(store, STATS_COLOR_EVEN, new RGB(0, 0,
				127));
		PreferenceConverter.setDefault(store, STATS_COLOR_ODD, new RGB(0, 127,
				0));
	}

}
