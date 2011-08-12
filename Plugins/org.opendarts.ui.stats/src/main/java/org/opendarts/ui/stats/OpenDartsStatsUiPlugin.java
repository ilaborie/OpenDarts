package org.opendarts.ui.stats;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The Class OpenDartsStatsUiPlugin.
 */
public class OpenDartsStatsUiPlugin extends AbstractUIPlugin {
	
	/** The plugin. */
	private static OpenDartsStatsUiPlugin plugin;

	/**
	 * Instantiates a new open darts stats ui plugin.
	 */
	public OpenDartsStatsUiPlugin() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
	
	/**
	 * Gets the open darts stats.
	 *
	 * @return the open darts stats
	 */
	public static IPreferenceStore getOpenDartsStats() {
		return plugin.getPreferenceStore();
	}

}
