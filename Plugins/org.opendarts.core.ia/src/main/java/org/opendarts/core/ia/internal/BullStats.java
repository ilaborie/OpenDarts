package org.opendarts.core.ia.internal;

import java.util.Properties;

/**
 * The Class BullStats.
 */
public class BullStats {
	
	/** The Constant UNLUCKY_SUFFIX. */
	private static final String UNLUCKY_SUFFIX = ".unlucky";
	
	/** The Constant DOUBLE_BULL_SUFFIX. */
	private static final String DOUBLE_BULL_SUFFIX = ".double";
	
	/** The Constant SIMPLE_BULL_SUFFIX. */
	private static final String SIMPLE_BULL_SUFFIX = ".simple";
	
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
	
	/** The out. */
	private final int out;
	
	/** The total. */
	private final int total;
	
	/**
	 * Instantiates a new bull stats.
	 *
	 * @param playerLevel the player level
	 * @param prefix the prefix
	 * @param props the props
	 */
	public BullStats(int playerLevel, String prefix, Properties props) {
		super();
		this.playerLevel = playerLevel;

		int total = 0;
		
		this.unlucky = Integer.valueOf(props.getProperty(prefix + UNLUCKY_SUFFIX));
		total+= this.unlucky;
		
		this.doubleBull = Integer.valueOf(props.getProperty(prefix + DOUBLE_BULL_SUFFIX));
		total+= this.doubleBull;
		
		this.simpleBull = Integer.valueOf(props.getProperty(prefix + SIMPLE_BULL_SUFFIX));
		total+= this.simpleBull;
		
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
		return this.getDoubleBull()+this.simpleBull;
	}

	/**
	 * Gets the out.
	 *
	 * @return the out
	 */
	public int getOut() {
		return this.getSimpleBull()+this.out;
	}
	
	

}
