package org.opendarts.core.internal.service;

import org.opendarts.core.model.dart.DartSector;
import org.opendarts.core.model.dart.DartZone;
import org.opendarts.core.model.dart.IDart;
import org.opendarts.core.model.dart.impl.Dart;
import org.opendarts.core.service.dart.IDartService;

/**
 * The Class DartService.
 */
public class DartService implements IDartService{

	
	/**
	 * Instantiates a new dart service.
	 */
	public DartService() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.opendarts.core.service.dart.IDartService#createDart(org.opendarts.core.model.dart.DartSector, org.opendarts.core.model.dart.DartZone)
	 */
	@Override
	public IDart createDart(DartSector sector, DartZone zone) {
		return new Dart(sector,zone);
	}

}
