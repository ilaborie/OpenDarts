package org.opendarts.core.ia.model;

import java.util.Properties;

/**
 * The Class GaussianStats.
 */
public class DartboardProperties {

	/** The un lucky bull. */
	private final int unLuckyBull;

	/** The un lucky others. */
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

	/**
	 * Instantiates a new zone gaussian stats.
	 *
	 * @param dartService the dart service
	 * @param props the props
	 * @param maxComputerLvl the max computer lvl
	 */
	public DartboardProperties(Properties props) {
		super();
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
	}

	/**
	 * Gets the un lucky bull.
	 *
	 * @return the un lucky bull
	 */
	public int getUnLuckyBull() {
		return this.unLuckyBull;
	}

	/**
	 * Gets the un lucky others.
	 *
	 * @return the un lucky others
	 */
	public int getUnLuckyOthers() {
		return this.unLuckyOthers;
	}

	/**
	 * Gets the double bull zone.
	 *
	 * @return the double bull zone
	 */
	public double getDoubleBullZone() {
		return this.doubleBullZone;
	}

	/**
	 * Gets the simple bull zone.
	 *
	 * @return the simple bull zone
	 */
	public double getSimpleBullZone() {
		return this.simpleBullZone;
	}

	/**
	 * Gets the small simple zone.
	 *
	 * @return the small simple zone
	 */
	public double getSmallSimpleZone() {
		return this.smallSimpleZone;
	}

	/**
	 * Gets the triple zone.
	 *
	 * @return the triple zone
	 */
	public double getTripleZone() {
		return this.tripleZone;
	}

	/**
	 * Gets the big zone.
	 *
	 * @return the big zone
	 */
	public double getBigZone() {
		return this.bigZone;
	}

	/**
	 * Gets the double zone.
	 *
	 * @return the double zone
	 */
	public double getDoubleZone() {
		return this.doubleZone;
	}

}
