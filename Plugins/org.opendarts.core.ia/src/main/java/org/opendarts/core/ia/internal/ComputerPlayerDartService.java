package org.opendarts.core.ia.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import org.opendarts.core.ia.service.IComputerPlayerDartService;
import org.opendarts.core.model.dart.DartSector;
import org.opendarts.core.model.dart.DartZone;
import org.opendarts.core.model.dart.IDart;
import org.opendarts.core.model.player.IComputerPlayer;
import org.opendarts.core.service.dart.IDartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ComputerPlayerDartService.
 */
public class ComputerPlayerDartService implements IComputerPlayerDartService {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(ComputerPlayerDartService.class);

	/** The rand. */
	private final Random rand;

	/** The dart service. */
	private final AtomicReference<IDartService> dartService = new AtomicReference<IDartService>();

	/** The level bull stats. */
	private final Map<Integer, BullStats> levelDoubleBullStats;
	private final Map<Integer, BullStats> levelSimpleBullStats;

	/** The level zone stats. */
	private final Map<Integer, ZoneStats> levelSimpleZoneStats;
	private final Map<Integer, ZoneStats> levelDoubleZoneStats;
	private final Map<Integer, ZoneStats> levelTripleZoneStats;

	/** The level sector stats. */
	private final Map<Integer, SectorStats> levelSectorStats;

	/**
	 * Instantiates a new computer player throw service.
	 */
	public ComputerPlayerDartService() {
		super();
		this.rand = new Random();
		this.levelSimpleBullStats = new HashMap<Integer, BullStats>();
		this.levelDoubleBullStats = new HashMap<Integer, BullStats>();

		this.levelSimpleZoneStats = new HashMap<Integer, ZoneStats>();
		this.levelDoubleZoneStats = new HashMap<Integer, ZoneStats>();
		this.levelTripleZoneStats = new HashMap<Integer, ZoneStats>();

		this.levelSectorStats = new HashMap<Integer, SectorStats>();
	}
	
	/**
	 * Startup.
	 */
	public void startup() {
		// load player stats
		URL resource;
		InputStream in = null;
		Properties props = new Properties();
		for (int level = 0; level < 13; level++) {
			resource = this.getClass().getClassLoader()
					.getResource(MessageFormat.format("level_{0}.properties", level));
			try {
				in = resource.openStream();
				props.clear();
				props.load(in);
				this.levelSimpleBullStats.put(level, new BullStats(level,
						"simplebull", props));
				this.levelDoubleBullStats.put(level, new BullStats(level,
						"doublebull", props));

				this.levelSimpleZoneStats.put(level, new ZoneStats(level,
						"simple", props));
				this.levelDoubleZoneStats.put(level, new ZoneStats(level,
						"double", props));
				this.levelTripleZoneStats.put(level, new ZoneStats(level,
						"triple", props));

				this.levelSectorStats.put(level, new SectorStats(level, props));
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
		}
	}
	
	/**
	 * Shutdown.
	 */
	public void shutdown() {
		// Nothing to do
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.ia.service.IComputerPlayerDartService#getComputerDart(org.opendarts.core.model.player.IComputerPlayer, org.opendarts.core.model.dart.IDart)
	 */
	@Override
	public IDart getComputerDart(IComputerPlayer player, IDart wished) {
		IDart result = null;
		if (DartSector.BULL.equals(wished.getSector())) {
			BullStats bullStats;
			switch (wished.getZone()) {
				case DOUBLE:
					bullStats = this.levelDoubleBullStats.get(player.getLevel());					
					break;
				default:
					bullStats = this.levelSimpleBullStats.get(player.getLevel());
					break;
			}
			result = this.getRandomBull(bullStats);
		} else {
			ZoneStats zoneStats;
			switch (wished.getZone()) {
				case TRIPLE:
					zoneStats = this.levelTripleZoneStats.get(player.getLevel());
					break;
				case DOUBLE:
					zoneStats = this.levelDoubleZoneStats.get(player.getLevel());
					break;
					default:
						zoneStats = this.levelSimpleZoneStats.get(player.getLevel());
						break;
			}
			SectorStats sectorStats = this.levelSectorStats.get(player
					.getLevel());
			result = this.getRandomDart(wished, zoneStats, sectorStats);
		}
		return result;
	}

	/**
	 * Gets the random bull.
	 *
	 * @param bullStats the bull stats
	 * @return the random bull
	 */
	private IDart getRandomBull(BullStats bullStats) {
		DartZone zone = null;
		DartSector sector = null;

		// Bull
		int val = rand.nextInt(bullStats.getTotal());
		if (val < bullStats.getUnlucky()) {
			zone = DartZone.NONE;
			sector = DartSector.UNLUCKY_DART;
		} else if (val < bullStats.getDoubleBull()) {
			zone = DartZone.DOUBLE;
			sector = DartSector.BULL;
		} else if (val < bullStats.getSimpleBull()) {
			zone = DartZone.SINGLE;
			sector = DartSector.BULL;
		} else {
			zone = DartZone.SINGLE;
			sector = DartSector.getSingle(this.rand.nextInt(20) + 1);
		}

		return this.dartService.get().createDart(sector, zone);
	}

	/**
	 * Gets the random zone.
	 *
	 * @param wished the wished
	 * @param zoneStats the zone stats
	 * @param sectorStats the sector stats
	 * @return the random zone
	 */
	private IDart getRandomDart(IDart wished, ZoneStats zoneStats,
			SectorStats sectorStats) {
		DartZone zone = null;
		DartSector sector = null;

		// Zone
		int total = zoneStats.getTotal();
		int val = rand.nextInt(total);
		if (val < zoneStats.getUnlucky()) {
			zone = DartZone.NONE;
			sector = DartSector.UNLUCKY_DART;
		} else if (val < zoneStats.getDoubleBull()) {
			zone = DartZone.DOUBLE;
			sector = DartSector.BULL;
		} else if (val < zoneStats.getSimpleBull()) {
			zone = DartZone.SINGLE;
			sector = DartSector.BULL;
		} else if (val < zoneStats.getSmallSimple()) {
			zone = DartZone.SINGLE;
		} else if (val < zoneStats.getTripleSector()) {
			zone = DartZone.TRIPLE;
		} else if (val < zoneStats.getBigSimple()) {
			zone = DartZone.SINGLE;
		} else if (val < zoneStats.getDoubleSector()) {
			zone = DartZone.DOUBLE;
		} else if (val < zoneStats.getOut()) {
			zone = DartZone.NONE;
			sector = DartSector.OUT_OF_TARGET;
		}

		if (sector == null) {
			// Sector
			DartSector wishedSector = wished.getSector();
			total = sectorStats.getTotal();
			val = rand.nextInt(total);
			if (val < sectorStats.getPreviousPrevious()) {
				sector = wishedSector.getPrevious().getPrevious();
			} else if (val < sectorStats.getPrevious()) {
				sector = wishedSector.getPrevious();
			} else if (val < sectorStats.getGood()) {
				sector = wishedSector;
			} else if (val < sectorStats.getNext()) {
				sector = wishedSector.getNext();
			} else if (val < sectorStats.getNextNext()) {
				sector = wishedSector.getNext().getNext();
			}
		}

		return this.dartService.get().createDart(sector, zone);
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
