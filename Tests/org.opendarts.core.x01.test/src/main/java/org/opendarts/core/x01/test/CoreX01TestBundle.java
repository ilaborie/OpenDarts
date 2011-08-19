package org.opendarts.core.x01.test;

import org.opendarts.core.service.player.IPlayerService;
import org.opendarts.core.service.session.ISessionService;
import org.opendarts.core.service.session.ISetService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The Class CoreX01TestBundle.
 */
public class CoreX01TestBundle implements BundleActivator {

	/** The player service. */
	private static IPlayerService playerService;

	/** The session service. */
	private static ISessionService sessionService;

	/** The set service. */
	private static ISetService setService;

	/**
	 * Gets the player service.
	 *
	 * @return the player service
	 */
	public static IPlayerService getPlayerService() {
		return playerService;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		// Retrieve the player service
		ServiceReference<IPlayerService> reference = bundleContext
				.getServiceReference(IPlayerService.class);
		if (reference != null) {
			playerService = bundleContext.getService(reference);
		}

		// Retrieve the session service
		ServiceReference<ISessionService> ref = bundleContext
				.getServiceReference(ISessionService.class);
		if (ref != null) {
			sessionService = bundleContext.getService(ref);
		}

		// Retrieve the set service
		ServiceReference<ISetService> refe = bundleContext
				.getServiceReference(ISetService.class);
		if (refe != null) {
			setService = bundleContext.getService(refe);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		playerService = null;
		sessionService = null;
		setService = null;
	}

	/**
	 * Gets the session service.
	 *
	 * @return the session service
	 */
	public static ISessionService getSessionService() {
		return sessionService;
	}

	/**
	 * Gets the sets the service.
	 *
	 * @return the sets the service
	 */
	public static ISetService getSetService() {
		return setService;
	}

}
