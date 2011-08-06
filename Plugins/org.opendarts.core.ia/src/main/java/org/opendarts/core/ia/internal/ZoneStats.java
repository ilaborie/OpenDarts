package org.opendarts.core.ia.internal;

import java.util.Properties;

/**
 * The Class ZoneStats.
 */
public class ZoneStats {

	/** The Constant UNLUCKY_SUFFIX. */
	private static final String UNLUCKY_SUFFIX = ".unlucky";

	/** The Constant DOUBLE_BULL_SUFFIX. */
	private static final String DOUBLE_BULL_SUFFIX = ".double.bull";

	/** The Constant SIMPLE_BULL_SUFFIX. */
	private static final String SIMPLE_BULL_SUFFIX = ".simple.bull";

	/** The Constant SMALL_SIMPLE_SUFFIX. */
	private static final String SMALL_SIMPLE_SUFFIX = ".small.simple";

	/** The Constant TRIPLE_SUFFIX. */
	private static final String TRIPLE_SUFFIX = ".triple";

	/** The Constant BIG_SIMPLE_SUFFIX. */
	private static final String BIG_SIMPLE_SUFFIX = ".big.simple";

	/** The Constant DOUBLE_SUFFIX. */
	private static final String DOUBLE_SUFFIX = ".double";

	/** The Constant OUT_SUFFIX. */
	private static final String OUT_SUFFIX = ".out";

	/** The player level. */
	private final int playerLevel;

	/** The unlucky. */
	private final int unlucky;

	/** The double bull. */
	private final int doubleBull;

	/** The simple bull. */
	private final int simpleBull;

	/** The small simple. */
	private final int smallSimple;

	/** The triple sector. */
	private final int tripleSector;

	/** The big simple. */
	private final int bigSimple;

	/** The double sector. */
	private final int doubleSector;

	/** The out. */
	private final int out;
	
	/** The total. */
	private final int total;

	/**
	 * Instantiates a new zone stats.
	 *
	 * @param playerLevel the player level
	 * @param prefix the prefix
	 * @param props the props
	 */
	public ZoneStats(int playerLevel, String prefix, Properties props) {
		super();
		this.playerLevel = playerLevel;

		int total = 0;
		this.unlucky = Integer.valueOf(props.getProperty(prefix
				+ UNLUCKY_SUFFIX));
		total += this.unlucky;
		
		this.doubleBull = Integer.valueOf(props.getProperty(prefix
				+ DOUBLE_BULL_SUFFIX));
		total+= this.doubleBull;
		
		this.simpleBull = Integer.valueOf(props.getProperty(prefix
				+ SIMPLE_BULL_SUFFIX));
		total+= this.simpleBull;
		
		this.smallSimple = Integer.valueOf(props.getProperty(prefix
				+ SMALL_SIMPLE_SUFFIX));
		total+= this.smallSimple;
		
		this.tripleSector = Integer.valueOf(props.getProperty(prefix
				+ TRIPLE_SUFFIX));
		total+= this.tripleSector;
		
		this.bigSimple = Integer.valueOf(props.getProperty(prefix
				+ BIG_SIMPLE_SUFFIX));
		total+= this.bigSimple;
		
		this.doubleSector = Integer.valueOf(props.getProperty(prefix
				+ DOUBLE_SUFFIX));
		total+= this.doubleSector;
		
		this.out = Integer.valueOf(props.getProperty(prefix + OUT_SUFFIX));
		total+= this.out;
		
		this.total = total;
	}

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public int getTotal() {
		return this.total;
	}

	/**
	 * Gets the player level.
	 *
	 * @return the player level
	 */
	public int getPlayerLevel() {
		return this.playerLevel;
	}

	/**
	 * Gets the unlucky.
	 *
	 * @return the unlucky
	 */
	public int getUnlucky() {
		return this.unlucky;
	}

	/**
	 * Gets the double bull.
	 *
	 * @return the double bull
	 */
	public int getDoubleBull() {
		return this.getUnlucky() + this.doubleBull;
	}

	/**
	 * Gets the simple bull.
	 *
	 * @return the simple bull
	 */
	public int getSimpleBull() {
		return this.getDoubleBull() + this.simpleBull;
	}

	/**
	 * Gets the small simple.
	 *
	 * @return the small simple
	 */
	public int getSmallSimple() {
		return this.getSimpleBull() + this.smallSimple;
	}

	/**
	 * Gets the triple sector.
	 *
	 * @return the triple sector
	 */
	public int getTripleSector() {
		return this.getSmallSimple() + this.tripleSector;
	}

	/**
	 * Gets the big simple.
	 *
	 * @return the big simple
	 */
	public int getBigSimple() {
		return this.getTripleSector() + this.bigSimple;
	}

	/**
	 * Gets the double sector.
	 *
	 * @return the double sector
	 */
	public int getDoubleSector() {
		return this.getBigSimple() + this.doubleSector;
	}

	/**
	 * Gets the out.
	 *
	 * @return the out
	 */
	public int getOut() {
		return this.getDoubleSector() + this.out;
	}
}
