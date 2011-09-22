package org.opendarts.ui.x01.defi;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.opendarts.core.x01.defi.OpenDartsX01DefiBundle;
import org.opendarts.ui.service.IGameUiService;
import org.opendarts.ui.stats.service.IStatsUiProvider;
import org.opendarts.ui.x01.defi.service.GameX01DefiUiService;
import org.opendarts.ui.x01.defi.service.StatsX01DefiUiService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenDartsUiX01DefiPlugin extends AbstractUIPlugin {

	// The plug-in ID
	/** The Constant PLUGIN_ID. */
	public static final String PLUGIN_ID = OpenDartsUiX01DefiPlugin.class.getPackage()
			.getName();

	// The shared instance
	/** The plugin. */
	private static OpenDartsUiX01DefiPlugin plugin;

	private static IGameUiService gameUiService;

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(OpenDartsUiX01DefiPlugin.class);

	private static StatsX01DefiUiService statsUiService;

	/**
	 * The constructor.
	 */
	public OpenDartsUiX01DefiPlugin() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		gameUiService = new GameX01DefiUiService();

		// Register the StatsUiService
		statsUiService = new StatsX01DefiUiService();
		IStatsUiProvider uiProvider = getService(IStatsUiProvider.class);
		if (uiProvider != null) {
			uiProvider.registerStatsUiService(
					OpenDartsX01DefiBundle.getStatsX01Service(), statsUiService);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		gameUiService = null;
		statsUiService = null;
		IStatsUiProvider uiProvider = getService(IStatsUiProvider.class);
		if (uiProvider != null) {
			uiProvider.unregisterStatsUiService(OpenDartsX01DefiBundle
					.getStatsX01Service());
		}
		super.stop(context);
	}

	/**
	 * Gets the x01 preferences.
	 *
	 * @return the x01 preferences
	 */
	public static IPreferenceStore getX01Preferences() {
		return plugin.getPreferenceStore();
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static OpenDartsUiX01DefiPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		ImageDescriptor imageDescriptor = plugin.getImageRegistry()
				.getDescriptor(path);
		if (imageDescriptor == null) {
			imageDescriptor = imageDescriptorFromPlugin(PLUGIN_ID, path);
			if (imageDescriptor == null) {
				LOG.error("Missing image: {}#{}", PLUGIN_ID, path);
			} else {
				plugin.getImageRegistry().put(path, imageDescriptor);
			}
		}
		return imageDescriptor;
	}

	/**
	 * Gets the image.
	 *
	 * @param path the path
	 * @return the image
	 */
	public static Image getImage(String path) {
		Image image = plugin.getImageRegistry().get(path);
		if (image == null) {
			ImageDescriptor imageDescriptor = imageDescriptorFromPlugin(
					PLUGIN_ID, path);
			if (imageDescriptor == null) {
				LOG.error("Missing image: {}#{}", PLUGIN_ID, path);
			} else {
				plugin.getImageRegistry().put(path, imageDescriptor);
				image = imageDescriptor.createImage();
			}
		}
		return image;
	}

	/**
	 * Gets the service.
	 *
	 * @param <T> the generic type
	 * @param clazz the class
	 * @return the service
	 */
	public static <T> T getService(Class<T> clazz) {
		T result = null;
		if (plugin != null) {
			BundleContext context = plugin.getBundle().getBundleContext();
			ServiceReference<T> serviceRef = context.getServiceReference(clazz);
			if (serviceRef != null) {
				result = context.getService(serviceRef);
			}
		}
		return result;
	}

	/**
	 * Gets the game x01 ui service.
	 *
	 * @return the game x01 ui service
	 */
	public static IGameUiService getGameX01UiService() {
		return gameUiService;
	}

	/**
	 * Gets the stats ui service.
	 *
	 * @return the stats ui service
	 */
	public static StatsX01DefiUiService getStatsUiService() {
		return statsUiService;
	}
}
