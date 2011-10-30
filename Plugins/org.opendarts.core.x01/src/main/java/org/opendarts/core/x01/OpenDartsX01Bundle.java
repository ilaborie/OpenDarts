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
	private IStatsProvider statsProvider;

	/** The stats x01 service. */
	private StatsX01Service statsX01Service;

	/** The computer player dart service. */
	private IComputerPlayerDartService computerPlayerDartService;

	/** The dart service. */
	private IDartService dartService;

	/** The context. */
	private BundleContext context;

	/** The bundle. */
	private static OpenDartsX01Bundle bundle;

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
		bundle = this;
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		this.statsProvider = null;
		this.statsX01Service = null;
		this.computerPlayerDartService = null;
		this.dartService = null;
		this.context = null;
		bundle = null;
	}

	/**
	 * Gets the bundle.
	 *
	 * @return the bundle
	 */
	public static OpenDartsX01Bundle getBundle() {
		return bundle;
	}

	/**
	 * Gets the stats x01 service.
	 *
	 * @return the stats x01 service
	 */
	public StatsX01Service getStatsX01Service() {
		if (this.statsX01Service == null) {
			ServiceReference<IStatsProvider> serviceRef = this.context
					.getServiceReference(IStatsProvider.class);
			// Stats
			if (serviceRef != null) {
				statsProvider = this.context.getService(serviceRef);
				statsX01Service = new StatsX01Service();
			}
		}
		return statsX01Service;
	}

	/**
	 * Gets the stats service.
	 *
	 * @param game the game
	 * @return the stats service
	 */
	public IStatsService getStatsService(GameX01 game) {
		IStatsService result = this.getStatsX01Service();
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
	public IComputerPlayerDartService getComputerPlayerDartService() {
		if (this.computerPlayerDartService == null) {
			// Computer player
			ServiceReference<IComputerPlayerDartService> ref = this.context
					.getServiceReference(IComputerPlayerDartService.class);
			if (ref != null) {
				computerPlayerDartService = this.context.getService(ref);
			}
		}
		return computerPlayerDartService;
	}

	/**
	 * Gets the dart service.
	 *
	 * @return the dart service
	 */
	public IDartService getDartService() {
		if (dartService == null) {
			// Dart Service
			ServiceReference<IDartService> dartServiceRef = this.context
					.getServiceReference(IDartService.class);
			if (dartServiceRef != null) {
				dartService = this.context.getService(dartServiceRef);
			}
		}
		return dartService;
	}
}
