package org.opendarts.core.x01.service;

import java.util.Properties;

import org.opendarts.core.model.dart.DartSector;
import org.opendarts.core.model.dart.DartZone;
import org.opendarts.core.model.dart.IDart;
import org.opendarts.core.service.dart.IDartService;
import org.opendarts.core.x01.OpenDartsX01Bundle;

/**
 * The Class BestDart.
 */
public class BestDart {
	
	/** The properties. */
	private final Properties props;
	
	/** The index. */
	private final int index;
	
	/** The dart service. */
	private final IDartService dartService;


	/**
	 * Instantiates a new best dart.
	 *
	 * @param index the index
	 * @param props the props
	 */
	public BestDart(int index,Properties props) {
		super();
		this.index = index;
		this.props = props;
		this.dartService = OpenDartsX01Bundle.getDartService();
	}
	
	/**
	 * Gets the index.
	 *
	 * @return the index
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * Gets the best dart.
	 *
	 * @param score the score
	 * @return the best dart
	 */
	public IDart getBestDart(int score) {
		DartSector sector;
		// Sector
		String sSector = props.getProperty("sector."+score);
		if (sSector == null) {
			sSector= props.getProperty("sector.default");
		}
		sector = DartSector.valueOf(sSector);
		
		DartZone zone;
		// Zone
		String sZone = props.getProperty("zone."+score);
		if (sZone== null) {
			sZone= props.getProperty("zone.default");
		}
		zone = DartZone.valueOf(sZone);
		
		return this.dartService.createDart(sector, zone);
	}
}
