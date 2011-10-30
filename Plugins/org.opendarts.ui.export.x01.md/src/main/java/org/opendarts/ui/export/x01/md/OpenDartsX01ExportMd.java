package org.opendarts.ui.export.x01.md;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class OpenDartsX01ExportMd extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.opendarts.ui.export.x01.md"; //$NON-NLS-1$

	// The shared instance
	private static OpenDartsX01ExportMd plugin;
	
	/**
	 * The constructor
	 */
	public OpenDartsX01ExportMd() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static OpenDartsX01ExportMd getDefault() {
		return plugin;
	}

}
