package org.opendarts.core.service.dart;

import org.opendarts.core.model.dart.DartSector;
import org.opendarts.core.model.dart.DartZone;
import org.opendarts.core.model.dart.IDart;

/**
 * The Interface IDartService.
 */
public interface IDartService {

	/**
	 * Creates the dart.
	 *
	 * @param sector the sector
	 * @param zone the zone
	 * @return the i dart
	 */
	public IDart createDart(DartSector sector, DartZone zone);
}
