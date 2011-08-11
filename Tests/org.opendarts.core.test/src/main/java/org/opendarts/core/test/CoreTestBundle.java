package org.opendarts.core.test;

import org.opendarts.core.service.dart.IDartService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The Class CoreTestBundle.
 */
public class CoreTestBundle implements BundleActivator {

	/** The dart service. */
	private static IDartService dartService;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		ServiceReference<IDartService> reference = bundleContext
				.getServiceReference(IDartService.class);
		if (dartService == null) {
			dartService = bundleContext.getService(reference);
		}
	}
	
	/**
	 * Gets the dart service.
	 *
	 * @return the dart service
	 */
	public static IDartService getDartService() {
		return dartService;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
	}

}
