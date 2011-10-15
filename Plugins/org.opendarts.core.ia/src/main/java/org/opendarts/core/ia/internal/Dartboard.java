package org.opendarts.core.ia.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.opendarts.core.ia.model.DartboardProperties;
import org.opendarts.core.ia.service.IDartboard;
import org.opendarts.core.model.dart.DartSector;
import org.opendarts.core.model.dart.DartZone;
import org.opendarts.core.model.dart.IDart;
import org.opendarts.core.model.player.IComputerPlayer;
import org.opendarts.core.service.dart.IDartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class Dartboard.
 */
public class Dartboard implements IDartboard {

	/** The logger. */
	private static final Logger LOG = LoggerFactory.getLogger(Dartboard.class);

	/** The dart service. */
	private final AtomicReference<IDartService> dartService;

	/** The board props. */
	private final Properties boardProps;

	/** The dartboard. */
	private DartboardProperties dartboard;

	private final Map<Integer, Double> lvlFactor;

	/**
	 * Instantiates a new dartboard.
	 */
	public Dartboard() {
		super();
		this.boardProps = new Properties();
		this.dartService = new AtomicReference<IDartService>();
		this.lvlFactor = new HashMap<Integer, Double>();
	}

	/**
	 * Startup.
	 */
	public void startup() {
		// load player stats
		URL resource;
		InputStream in = null;
		resource = this.getClass().getClassLoader()
				.getResource("DartBoard.properties");
		try {
			in = resource.openStream();
			this.boardProps.load(in);
		} catch (IOException e) {
			LOG.error("Fail to load computer level stats", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				LOG.error("Fail to load computer level stats", e);
			}
		}
		this.dartboard = new DartboardProperties(this.boardProps);

		// Level Factor
		Pattern p = Pattern.compile("level\\.(.*)");
		Matcher matcher;
		String lvl;
		for (Object prop : this.boardProps.keySet()) {
			matcher = p.matcher((String) prop);
			if (matcher.matches()) {
				lvl = matcher.group(1);
				this.lvlFactor.put(Integer.valueOf(lvl), Double
						.valueOf(this.boardProps.getProperty((String) prop)));
			}
		}
	}

	/**
	 * Shutdown.
	 */
	public void shutdown() {
		this.dartboard = null;
	}

	@Override
	public DartboardProperties getDartboard() {
		return this.dartboard;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.ia.service.IDartboard#getDart(double, double)
	 */
	@Override
	public IDart getDart(double x, double y) {
		// distance
		double dist = this.getDistance(x, y);

		IDartService ds = this.dartService.get();
		// Zone
		DartZone zone;
		if (dist > this.dartboard.getDoubleZone()) {
			return ds.createDart(DartSector.OUT_OF_TARGET, DartZone.NONE);
		} else if (dist > this.dartboard.getBigZone()) {
			zone = DartZone.DOUBLE;
		} else if (dist > this.dartboard.getTripleZone()) {
			zone = DartZone.SINGLE;
		} else if (dist > this.dartboard.getSmallSimpleZone()) {
			zone = DartZone.TRIPLE;
		} else if (dist > this.dartboard.getSimpleBullZone()) {
			zone = DartZone.SINGLE;
		} else if (dist > this.dartboard.getDoubleBullZone()) {
			return ds.createDart(DartSector.BULL, DartZone.SINGLE);
		} else {
			return ds.createDart(DartSector.BULL, DartZone.DOUBLE);
		}

		// Angle
		double deg = this.getAngle(x, y);

		// Sector
		DartSector sector = DartSector.SIX;
		double test = 0;
		// six start at -9¡
		deg -= 9;
		while (deg > test) {
			sector = sector.getPrevious();
			test += 18;
		}

		return ds.createDart(sector, zone);
	}

	/**
	 * Gets the player factor.
	 *
	 * @param player the player
	 * @return the player factor
	 */
	@Override
	public double getPlayerFactor(IComputerPlayer player) {
		return this.lvlFactor.get(player.getLevel());
	}

	/**
	 * Gets the distance.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the distance
	 */
	private double getDistance(double x, double y) {
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * Gets the angle.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the angle
	 */
	private double getAngle(double x, double y) {
		double atan = Math.atan2(y, x);
		double deg = atan * 180 / Math.PI;

		if (deg < 0) {
			deg += 360;
		}
		return deg;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.ia.service.IDartboard#getX(org.opendarts.core.model.dart.IDart)
	 */
	@Override
	public double getX(IDart dart) {
		double x;
		if (dart.getScore() == 50) {
			x = 0;
		} else if (dart.getScore() == 25) {
			x = (this.dartboard.getSimpleBullZone() + this.dartboard
					.getDoubleBullZone()) / 2;
		} else {
			double dist = this.getDistance(dart);
			double deg = this.getAngle(dart);
			double rad = deg * Math.PI / 180;
			x = dist * Math.cos(rad);
		}
		return x;
	}

	/**
	 * Gets the angle.
	 *
	 * @param dart the dart
	 * @return the angle
	 */
	private double getAngle(IDart dart) {
		double deg;
		switch (dart.getSector()) {
			case BULL:
			case OUT_OF_TARGET:
			case UNLUCKY_DART:
			case NONE:
				deg = 0;
				break;
			default:
				deg = 0;
				DartSector sector = DartSector.SIX;
				while (sector != dart.getSector()) {
					deg += 18;
					sector = sector.getPrevious();
				}

				break;
		}
		return deg;
	}

	/**
	 * Gets the distance.
	 *
	 * @param dart the dart
	 * @return the distance
	 */
	private double getDistance(IDart dart) {
		double dist;
		switch (dart.getZone()) {
			case TRIPLE:
				dist = (this.dartboard.getSmallSimpleZone() + this.dartboard
						.getTripleZone()) / 2;
				break;
			case SINGLE:
				dist = (this.dartboard.getTripleZone() + this.dartboard
						.getBigZone())/2;
				break;
			case DOUBLE:
				dist = (this.dartboard.getBigZone() + this.dartboard
						.getDoubleZone()) / 2;
				break;
			case NONE:
			default:
				dist = this.dartboard.getDoubleZone() + 20;
				break;
		}
		return dist;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.ia.service.IDartboard#getY(org.opendarts.core.model.dart.IDart)
	 */
	@Override
	public double getY(IDart dart) {
		double y;
		if (dart.getScore() == 50) {
			y = 0;
		} else if (dart.getScore() == 25) {
			y = (this.dartboard.getSimpleBullZone() + this.dartboard
					.getDoubleBullZone()) / 2;
		} else {
			double dist = this.getDistance(dart);
			double deg = this.getAngle(dart);
			double rad = deg * Math.PI / 180;
			y = dist * Math.sin(rad);
		}
		return y;
	}

	/**
	 * Sets the dart service.
	 *
	 * @param dartService the new dart service
	 */
	public void setDartService(IDartService dartService) {
		this.dartService.set(dartService);
	}

	/**
	 * Unset dart service.
	 *
	 * @param dartService the dart service
	 */
	public void unsetDartService(IDartService dartService) {
		this.dartService.compareAndSet(dartService, null);
	}

}
