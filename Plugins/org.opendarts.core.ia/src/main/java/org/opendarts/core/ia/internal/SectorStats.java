package org.opendarts.core.ia.internal;

import java.util.Properties;

/**
 * The Class BullStats.
 */
public class SectorStats {

	/** The Constant PREVIOUS_PREVIOUS_SUFFIX. */
	private static final String PREVIOUS_PREVIOUS_SUFFIX = ".previous2";

	/** The Constant PREVIOUS_SUFFIX. */
	private static final String PREVIOUS_SUFFIX = ".previous";

	/** The Constant GOOD_SUFFIX. */
	private static final String GOOD_SUFFIX = ".good";

	/** The Constant NEXT_SUFFIX. */
	private static final String NEXT_SUFFIX = ".next";

	/** The Constant NEXT_NEXT_SUFFIX. */
	private static final String NEXT_NEXT_SUFFIX = ".next2";

	/** The player level. */
	private final int playerLevel;

	/** The previous previous. */
	private final int previousPrevious;

	/** The previous. */
	private final int previous;

	/** The good. */
	private final int good;

	/** The next. */
	private final int next;

	/** The next next. */
	private final int nextNext;

	/** The total. */
	private final int total;
	
	/**
	 * Instantiates a new sector stats.
	 *
	 * @param playerLevel the player level
	 * @param prefix the prefix
	 * @param props the props
	 */
	public SectorStats(int playerLevel, Properties props) {
		super();
		this.playerLevel = playerLevel;

		int total = 0;
		
		String prefix = "sector";
		this.previousPrevious = Integer.valueOf(props.getProperty(prefix
				+ PREVIOUS_PREVIOUS_SUFFIX));
		total+= this.previousPrevious;
		
		this.previous = Integer.valueOf(props.getProperty(prefix
				+ PREVIOUS_SUFFIX));
		total+= this.previous;
		
		this.good = Integer.valueOf(props.getProperty(prefix + GOOD_SUFFIX));
		total+= this.good;
		
		this.next = Integer.valueOf(props.getProperty(prefix + NEXT_SUFFIX));
		total+= this.next;
		
		this.nextNext = Integer.valueOf(props.getProperty(prefix
				+ NEXT_NEXT_SUFFIX));
		total+= this.nextNext;
		
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
	 * Gets the previous previous.
	 *
	 * @return the previous previous
	 */
	public int getPreviousPrevious() {
		return this.previousPrevious;
	}

	/**
	 * Gets the previous.
	 *
	 * @return the previous
	 */
	public int getPrevious() {
		return this.getPreviousPrevious() + this.previous;
	}

	/**
	 * Gets the good.
	 *
	 * @return the good
	 */
	public int getGood() {
		return this.getPrevious() + this.good;
	}

	/**
	 * Gets the next.
	 *
	 * @return the next
	 */
	public int getNext() {
		return this.getGood() + this.next;
	}

	/**
	 * Gets the next next.
	 *
	 * @return the next next
	 */
	public int getNextNext() {
		return this.getNext() + this.nextNext;
	}

}
