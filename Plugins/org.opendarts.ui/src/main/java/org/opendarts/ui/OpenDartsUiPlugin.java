package org.opendarts.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.opendarts.core.service.session.ISessionService;
import org.opendarts.ui.utils.listener.SessionListener;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class OpenDartsX01Bundle.
 */
public class OpenDartsUiPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = OpenDartsUiPlugin.class.getPackage()
			.getName();

	// The shared instance
	private static OpenDartsUiPlugin plugin;

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(OpenDartsUiPlugin.class);

	/**
	 * The constructor
	 */
	public OpenDartsUiPlugin() {
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
		ISessionService sessionService = getService(ISessionService.class);
		sessionService.addListener(new SessionListener());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static OpenDartsUiPlugin getDefault() {
		return plugin;
	}

	/**
	 * Gets the preference store.
	 *
	 * @return the preference store
	 */
	public static IPreferenceStore getOpenDartsPreference() {
		return plugin.getPreferenceStore();
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
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
}
