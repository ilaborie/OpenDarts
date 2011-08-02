package org.opendarts.core.x01;

import org.opendarts.core.stats.service.IStatsProvider;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.core.x01.model.GameX01;
import org.opendarts.core.x01.service.StatsX01Service;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The Class OpenDartsX01Bundle.
 */
public class OpenDartsX01Bundle implements BundleActivator {

	/** The stats provider. */
	private static IStatsProvider statsProvider;
	
	/** The stats x01 service. */
	private static StatsX01Service statsX01Service;
	
	/**
	 * The constructor
	 */
	public OpenDartsX01Bundle() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		ServiceReference<IStatsProvider> serviceRef = context.getServiceReference(IStatsProvider.class);
		if (serviceRef != null) {
			 statsProvider =  context.getService(serviceRef);
			 statsX01Service = new StatsX01Service();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		statsProvider = null;
		statsX01Service = null;
	}
	
	/**
	 * Gets the stats service.
	 *
	 * @param game the game
	 * @return the stats service
	 */
	public static IStatsService getStatsService(GameX01 game) {
		IStatsService result = statsX01Service;
		if (result!=null) {
			statsProvider.registerStatsService(game, result);
		}
		return result;
	}
}
