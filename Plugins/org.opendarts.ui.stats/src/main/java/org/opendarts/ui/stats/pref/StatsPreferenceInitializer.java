package org.opendarts.ui.stats.pref;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
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
	}

}
