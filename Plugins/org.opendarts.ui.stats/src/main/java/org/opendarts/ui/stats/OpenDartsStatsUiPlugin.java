package org.opendarts.ui.stats;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class OpenDartsStatsUiPlugin.
 */
public class OpenDartsStatsUiPlugin extends AbstractUIPlugin {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(OpenDartsStatsUiPlugin.class);
	
	/** The Constant PLUGIN_ID. */
	private static final String PLUGIN_ID = "org.opendarts.ui.stats";

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
	 * Gets the service.
	 *
	 * @param clazz the class
	 * @return the service
	 */
	public static <T> List<T> getAllService(Class<T> clazz) {
		List<T> result = new ArrayList<T>();
		if (plugin != null) {
			BundleContext context = plugin.getBundle().getBundleContext();
			try {
				Collection<ServiceReference<T>> refs;
				refs = context.getServiceReferences(clazz, null);
				for (ServiceReference<T> serviceRef : refs) {
					if (serviceRef != null) {
						result.add(context.getService(serviceRef));
					}
				}
			} catch (InvalidSyntaxException e) {
				LOG.error("Could not retrieve services: " + clazz, e);
			}
		}
		return result;
	}

	public static IPreferenceStore getPrefefrenceStore() {
		return null;
	}

}
