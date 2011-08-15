package org.opendarts.ui.stats.internal;

import java.util.HashMap;
import java.util.Map;

import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.ui.stats.service.IStatsUiProvider;
import org.opendarts.ui.stats.service.IStatsUiService;

// TODO: Auto-generated Javadoc
/**
 * The Class StatsUiProvider.
 */
public class StatsUiProvider implements IStatsUiProvider{
	
	/** The ui services. */
	private final Map<IStatsService, IStatsUiService> uiServices;
	
	/**
	 * Instantiates a new stats ui provider.
	 */
	public StatsUiProvider() {
		super();
		this.uiServices = new HashMap<IStatsService, IStatsUiService>();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.stats.service.IStatsUiProvider#registerStatsUiService(org.opendarts.core.stats.service.IStatsService, org.opendarts.ui.stats.service.IStatsUiService)
	 */
	@Override
	public void registerStatsUiService(IStatsService statsService,
			IStatsUiService statsUiService) {
		this.uiServices.put(statsService, statsUiService);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.stats.service.IStatsUiProvider#getStatsUiService(org.opendarts.core.stats.service.IStatsService)
	 */
	@Override
	public IStatsUiService getStatsUiService(IStatsService statsService) {
		return this.uiServices.get(statsService);
	}
	
	/* (non-Javadoc)
	 * @see org.opendarts.ui.stats.service.IStatsUiProvider#unregisterStatsUiService(org.opendarts.core.stats.service.IStatsService)
	 */
	@Override
	public void unregisterStatsUiService(IStatsService statsService) {
		this.uiServices.remove(statsService);
	}

}
