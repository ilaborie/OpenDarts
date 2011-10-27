package org.opendarts.ui.export;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.opendarts.ui.export.service.IExportProvider;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The activator class controls the plug-in life cycle.
 */
public class OpenDartsUiExportPlugin extends AbstractUIPlugin {

	// The plug-in ID
	/** The Constant PLUGIN_ID. */
	public static final String PLUGIN_ID = "org.opendarts.ui.export"; //$NON-NLS-1$

	// The shared instance
	/** The plugin. */
	private static OpenDartsUiExportPlugin plugin;

	/** The export provider. */
	private IExportProvider exportProvider;

	/**
	 * The constructor.
	 */
	public OpenDartsUiExportPlugin() {
		super();
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
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static OpenDartsUiExportPlugin getDefault() {
		return plugin;
	}

	/**
	 * Gets the export provider.
	 *
	 * @return the export provider
	 */
	public IExportProvider getExportProvider() {
		if (exportProvider == null) {
			BundleContext context = plugin.getBundle().getBundleContext();
			ServiceReference<IExportProvider> ref = context 
					.getServiceReference(IExportProvider.class);
			if (ref != null) {
				this.exportProvider = context.getService(ref);
			}
		}
		return this.exportProvider;
	}
}
