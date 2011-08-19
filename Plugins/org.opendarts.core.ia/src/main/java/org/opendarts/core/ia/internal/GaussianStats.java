package org.opendarts.core.ia.internal;

import java.util.Properties;
import java.util.Random;

import org.opendarts.core.model.dart.DartSector;
import org.opendarts.core.model.dart.DartZone;
import org.opendarts.core.model.dart.IDart;
import org.opendarts.core.model.player.IComputerPlayer;
import org.opendarts.core.service.dart.IDartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class GaussianStats.
 */
public class GaussianStats {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(GaussianStats.class);

	private final int unLuckyBull;

	private final int unLuckyOthers;

	/** The double bull zone. */
	private final double doubleBullZone;

	/** The simple bull zone. */
	private final double simpleBullZone;

	/** The small simple zone. */
	private final double smallSimpleZone;

	/** The triple zone. */
	private final double tripleZone;

	/** The big zone. */
	private final double bigZone;

	/** The double zone. */
	private final double doubleZone;

	/** The triple next. */
	private final double tripleNext;

	/** The triple next next. */
	private final double tripleNextNext;

	/** The triple next next next. */
	private final double tripleNextNextNext;

	/** The double next. */
	private final double doubleNext;

	/** The double next next. */
	private final double doubleNextNext;

	/** The double next next next. */
	private final double doubleNextNextNext;

	/** The simple next. */
	private final double simpleNext;

	/** The simple next next. */
	private final double simpleNextNext;

	/** The simple next next next. */
	private final double simpleNextNextNext;

	/** The dart service. */
	private final IDartService dartService;

	private final double[] lvlFactor;

	/** The rand. */
	private final Random rand;

	/**
	 * Instantiates a new zone gaussian stats.
	 *
	 * @param dartService the dart service
	 */
	public GaussianStats(IDartService dartService, Properties props,
			int maxComputerLvl) {
		super();
		this.dartService = dartService;
		this.rand = new Random();

		// Unlucky
		this.unLuckyBull = Integer.valueOf(props.getProperty("unLuckyBull"));
		this.unLuckyOthers = Integer
				.valueOf(props.getProperty("unLuckyOthers"));

		// Zone
		this.doubleBullZone = Double.valueOf(props
				.getProperty("doubleBullZone"));
		this.simpleBullZone = this.doubleBullZone
				+ Double.valueOf(props.getProperty("simpleBullZone"));
		this.smallSimpleZone = this.simpleBullZone
				+ Double.valueOf(props.getProperty("smallSimpleZone"));
		this.tripleZone = this.smallSimpleZone
				+ Double.valueOf(props.getProperty("tripleZone"));
		this.bigZone = this.tripleZone
				+ Double.valueOf(props.getProperty("bigZone"));
		this.doubleZone = this.bigZone
				+ Double.valueOf(props.getProperty("doubleZone"));

		// Sector
		this.tripleNext = Double.valueOf(props.getProperty("tripleNext"));
		this.tripleNextNext = Double.valueOf(props
				.getProperty("tripleNextNext"));
		this.tripleNextNextNext = Double.valueOf(props
				.getProperty("tripleNextNextNext"));

		this.simpleNext = Double.valueOf(props.getProperty("simpleNext"));
		this.simpleNextNext = Double.valueOf(props
				.getProperty("simpleNextNext"));
		this.simpleNextNextNext = Double.valueOf(props
				.getProperty("simpleNextNextNext"));

		this.doubleNext = Double.valueOf(props.getProperty("doubleNext"));
		this.doubleNextNext = Double.valueOf(props
				.getProperty("doubleNextNext"));
		this.doubleNextNextNext = Double.valueOf(props
				.getProperty("doubleNextNextNext"));

		// Level Factor
		this.lvlFactor = new double[maxComputerLvl];
		for (int lvl = 0; lvl < this.lvlFactor.length; lvl++) {
			this.lvlFactor[lvl] = Double.valueOf(props.getProperty("level."
					+ lvl));
		}
	}

	/**
	 * Gets the dart.
	 *
	 * @param player the player
	 * @param wished the wished
	 * @return the dart
	 */
	public IDart getDart(IComputerPlayer player, IDart wished) {
		LOG.trace("Wished: {}", wished);

		DartSector sector = null;
		DartZone zone = null;

		// Zone
		if (DartSector.BULL.equals(wished.getSector())) {
			IDart dart = this.getBull(wished.getZone(), player);
			zone = dart.getZone();
			sector = dart.getSector();
		} else {
			// Unlucky
			if (this.rand.nextInt(this.unLuckyOthers) == 0) {
				return this.dartService.createDart(DartSector.UNLUCKY_DART,
						DartZone.NONE);
			}

			double zoneDone = this.getZoneDone(wished.getZone(), player);
			if (zoneDone == 0) {
				return this.dartService.createDart(DartSector.OUT_OF_TARGET,
						DartZone.NONE);
			}
			IDart dart = this.getZone(zoneDone);
			zone = dart.getZone();
			sector = dart.getSector();
		}

		// Sector
		if (sector == null) {
			int shift = this.getShift(wished.getZone(), player);
			sector = this.getSector(shift, wished.getSector());
		}

		// Result
		IDart result = this.dartService.createDart(sector, zone);
		LOG.trace("Done: {}", result);
		return result;
	}

	/**
	 * Gets the sector.
	 *
	 * @param shift the shift
	 * @param sector the sector
	 * @return the sector
	 */
	private DartSector getSector(int shift, DartSector sector) {
		DartSector result = null;
		if (shift > 0) {
			result = this.getSector(shift - 1, sector.getNext());
		} else if (shift < 0) {
			result = this.getSector(shift + 1, sector.getPrevious());
		} else {
			result = sector;
		}
		return result;
	}

	/**
	 * Gets the shift.
	 *
	 * @param zone the zone
	 * @param player the player
	 * @return the shift
	 */
	private int getShift(DartZone zone, IComputerPlayer player) {
		int shift = 0;

		double factor = this.getPlayerFactor(player);
		double result = this.nextGaussian(factor, 0d);

		switch (zone) {
			case TRIPLE:
				if (result > this.tripleNextNextNext) {
					result = 3;
				} else if (result > this.tripleNextNext) {
					result = 2;
				} else if (result > this.tripleNext) {
					result = 1;
				}
				break;
			case SINGLE:
				if (result > this.simpleNextNextNext) {
					result = 3;
				} else if (result > this.simpleNextNext) {
					result = 2;
				} else if (result > this.simpleNext) {
					result = 1;
				}
				break;
			case DOUBLE:
				if (result > this.doubleNextNextNext) {
					result = 3;
				} else if (result > this.doubleNextNext) {
					result = 2;
				} else if (result > this.doubleNext) {
					result = 1;
				}
				break;
			case NONE:
			default:
				break;
		}

		if (result < 0) {
			shift *= -1;
		}
		return shift;
	}

	/**
	 * Gets the bull.
	 *
	 * @param zone the zone
	 * @param player the player
	 * @return the bull
	 */
	private IDart getBull(DartZone zone, IComputerPlayer player) {
		// Unlucky
		if (this.rand.nextInt(this.unLuckyBull) == 0) {
			return this.dartService.createDart(DartSector.UNLUCKY_DART,
					DartZone.NONE);
		}

		IDart dart;
		double zoneWished;
		if (DartZone.DOUBLE.equals(zone)) {
			zoneWished = 0d;
		} else {
			zoneWished = (this.doubleBullZone + this.simpleBullZone) / 2d;
		}

		double factor = this.getPlayerFactor(player);
		double result = this.nextGaussian(factor, 0d);
		result += zoneWished;
		result = Math.abs(result);

		if (result > this.simpleBullZone) {
			DartSector sector = DartSector.getSingle(this.rand.nextInt(20) + 1);
			dart = this.dartService.createDart(sector, this.getZone(result)
					.getZone());
		} else if (result > this.doubleBullZone) {
			dart = this.dartService
					.createDart(DartSector.BULL, DartZone.SINGLE);

		} else {
			dart = this.dartService
					.createDart(DartSector.BULL, DartZone.DOUBLE);
		}
		return dart;
	}

	/**
	 * Gets the zone done.
	 *
	 * @param zone the zone
	 * @param player the player
	 * @return the zone done
	 */
	private double getZoneDone(DartZone zone, IComputerPlayer player) {
		double zoneWished = this.getZone(zone);
		if (zoneWished == 0d) {
			return 0d;
		}
		double factor = this.getPlayerFactor(player);

		double result = this.nextGaussian(factor, zoneWished);
		return result;
	}

	/**
	 * Nex gaussian.
	 *
	 * @param factor the factor
	 * @param offset the offset
	 * @return the double
	 */
	private double nextGaussian(double factor, double offset) {
		double result = factor * this.rand.nextGaussian();
		result += offset;
		return Math.abs(result);
	}

	/**
	 * Gets the player factor.
	 *
	 * @param player the player
	 * @return the player factor
	 */
	private double getPlayerFactor(IComputerPlayer player) {
		return this.lvlFactor[player.getLevel()];
	}

	/**
	 * Gets the zone.
	 *
	 * @param zone the zone
	 * @return the zone
	 */
	private double getZone(DartZone zone) {
		double result = 0d;
		switch (zone) {
			case SINGLE:
				result = (this.tripleZone + this.bigZone) / 2d;
				break;
			case TRIPLE:
				result = (this.tripleZone + this.smallSimpleZone) / 2d;
				break;
			case DOUBLE:
				result = (this.doubleZone + this.bigZone) / 2d;
				break;
			case NONE:
			default:
				break;
		}
		return result;
	}

	/**
	 * Gets the d.
	 *
	 * @param d the d
	 * @return the d
	 */
	private IDart getZone(double d) {
		DartSector sector = null;
		DartZone zone = null;

		// Zone
		if (d > this.doubleZone) {
			zone = DartZone.NONE;
			sector = DartSector.OUT_OF_TARGET;
		} else if (d > this.bigZone) {
			zone = DartZone.DOUBLE;
		} else if (d > this.tripleZone) {
			zone = DartZone.SINGLE;
		} else if (d > this.smallSimpleZone) {
			zone = DartZone.TRIPLE;
		} else if (d > this.simpleBullZone) {
			zone = DartZone.SINGLE;
		} else if (d > this.doubleBullZone) {
			zone = DartZone.SINGLE;
			sector = DartSector.BULL;
		} else {
			zone = DartZone.DOUBLE;
			sector = DartSector.BULL;
		}

		return this.dartService.createDart(sector, zone);
	}

}
