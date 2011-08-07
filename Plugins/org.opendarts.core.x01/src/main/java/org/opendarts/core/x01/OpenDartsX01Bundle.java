package org.opendarts.core.x01;

import org.opendarts.core.ia.service.IComputerPlayerDartService;
import org.opendarts.core.service.dart.IDartService;
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

	/** The computer player dart service. */
	private static IComputerPlayerDartService computerPlayerDartService;

	private static IDartService dartService;

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
		ServiceReference<IStatsProvider> serviceRef = context
				.getServiceReference(IStatsProvider.class);
		// Stats
		if (serviceRef != null) {
			statsProvider = context.getService(serviceRef);
			statsX01Service = new StatsX01Service();
		}
		
		// Computer player
		 ServiceReference<IComputerPlayerDartService> ref = context.getServiceReference(IComputerPlayerDartService.class);
		 if (ref !=null) {
			 computerPlayerDartService = context.getService(ref);
		 }
		 
		 // Dart Service
		 ServiceReference<IDartService> dartServiceRef = context.getServiceReference(IDartService.class);
		 if (dartServiceRef !=null) {
			 dartService = context.getService(dartServiceRef);
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
		if (result != null) {
			statsProvider.registerStatsService(game, result);
		}
		return result;
	}

	/**
	 * Gets the computer player dart service.
	 *
	 * @return the computer player dart service
	 */
	public static IComputerPlayerDartService getComputerPlayerDartService() {
		return computerPlayerDartService;
	}

	public static IDartService getDartService() {
		return dartService;
	}
}
