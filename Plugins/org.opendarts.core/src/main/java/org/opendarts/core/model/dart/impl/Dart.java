package org.opendarts.core.model.dart.impl;

import org.opendarts.core.model.dart.DartSector;
import org.opendarts.core.model.dart.DartZone;
import org.opendarts.core.model.dart.IDart;

/**
 * The Class Dart.
 */
public class Dart implements IDart {

	/** The sector. */
	private final DartSector sector;

	/** The zone. */
	private final DartZone zone;

	/** The score. */
	private final int score;

	/**
	 * Instantiates a new dart.
	 *
	 * @param sector the sector
	 * @param zone the zone
	 */
	public Dart(DartSector sector, DartZone zone) {
		super();
		this.sector = sector;
		this.zone = zone;
		this.score = this.computeScore();
	}

	/**
	 * Compute score.
	 *
	 * @return the score
	 */
	private int computeScore() {
		int baseScore = this.sector.getBaseScore();
		int multi = this.zone.getMultiplier();
		return baseScore * multi;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result;
		switch (this.sector) {
			case TWENTY:
				if (DartZone.DOUBLE.equals(this.zone)) {
					result = "Top";
					break;
				}
			default:
				result = this.zone.toString() + this.sector.toString();
				break;
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.dart.IDart#getSector()
	 */
	@Override
	public DartSector getSector() {
		return this.sector;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.dart.IDart#getZone()
	 */
	@Override
	public DartZone getZone() {
		return this.zone;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.dart.IDart#getScore()
	 */
	@Override
	public int getScore() {
		return this.score;
	}

}
