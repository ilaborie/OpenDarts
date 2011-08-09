package org.opendarts.core.player.test;

import org.opendarts.core.service.player.IPlayerService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The Class PlayerTestBundle.
 */
public class PlayerTestBundle implements BundleActivator {

	/** The player service. */
	private static IPlayerService playerService;

	
	public static IPlayerService getPlayerService() {
		return playerService;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		// Retrieve the player service
		ServiceReference<IPlayerService> reference = bundleContext.getServiceReference(IPlayerService.class);
		if (reference !=null) {
			playerService = bundleContext.getService(reference);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		playerService = null;
	}

}
