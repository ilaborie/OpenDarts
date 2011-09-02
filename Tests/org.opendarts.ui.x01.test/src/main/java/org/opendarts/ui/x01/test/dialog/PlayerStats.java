package org.opendarts.ui.x01.test.dialog;

public class PlayerStats {

	/** The min. */
	private int min = Integer.MAX_VALUE;

	/** The max. */
	private int max;

	/** The count. */
	private int count;

	/** The distribution. */
	private final double[] distribution;

	/** The played. */
	private int played;

	/** The nb games. */
	private final int nbGames;

	/**
	 * Instantiates a new player stats.
	 *
	 * @param nbGames the nb games
	 */
	public PlayerStats(int nbGames) {
		super();
		this.nbGames = nbGames;
		this.distribution = new double[this.nbGames];
	}
	
	/**
	 * Update.
	 *
	 * @param current the current
	 */
	public void update(int current) {
		this.min = Math.min(this.min, current);
		this.max = Math.max(this.max, current);
		this.count += current;
		this.distribution[this.played] = (double) current;
		this.played++;
	}

	public int getMin() {
		return this.min;
	}

	public double getAverage() {
		return ((double) this.count) / ((double) this.nbGames);
	}

	public int getMax() {
		return this.max;
	}

	public double[] getDistribution() {
		return this.distribution;
	}

	public int getPlayed() {
		return this.played;
	}

}
