package org.opendarts.core.internal.service;

import org.opendarts.core.model.dart.DartSector;
import org.opendarts.core.model.dart.DartZone;
import org.opendarts.core.model.dart.IDart;
import org.opendarts.core.model.dart.impl.Dart;
import org.opendarts.core.service.dart.IDartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class DartService.
 */
public class DartService implements IDartService {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(DartService.class);

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
		return new Dart(sector, zone);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.service.dart.IDartService#getDart(java.lang.String)
	 */
	@Override
	public IDart getDart(String sDart) {
		DartSector sector = null;
		DartZone zone = null;

		if (sDart != null) {
			String dart = sDart.trim().toUpperCase();

			if ("25".equals(dart)) {
				sector = DartSector.BULL;
				zone = DartZone.SINGLE;
			} else if ("50".equals(dart)) {
				sector = DartSector.BULL;
				zone = DartZone.DOUBLE;
			} else if (dart.length() > 0) {
				try {
					Integer val;
					switch (dart.charAt(0)) {
						case 'T':
							zone = DartZone.TRIPLE;
							val = Integer.valueOf(dart.substring(1));
							sector = DartSector.getSingle(val);
							if (DartSector.BULL.equals(sector)) {
								sector = DartSector.NONE;
							}
							break;
						case 'D':
							zone = DartZone.DOUBLE;
							val = Integer.valueOf(dart.substring(1));
							sector = DartSector.getSingle(val);
							break;
						default:
							zone = DartZone.SINGLE;
							val = Integer.valueOf(dart);
							sector = DartSector.getSingle(val);
							break;
					}
					if (DartSector.NONE.equals(sector)) {
						zone = DartZone.NONE;
					}

				} catch (NumberFormatException e) {
					LOG.warn("Invalid dart format: {}", sDart);
					sector = DartSector.NONE;
					zone = DartZone.NONE;
				}
			}
		}
		// check warn
		if ((sector == null) || (zone == null)) {
			LOG.warn("Invalid dart format: {}", sDart);
			sector = DartSector.NONE;
			zone = DartZone.NONE;
		}
		return this.createDart(sector, zone);
	}
}
