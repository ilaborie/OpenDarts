package org.opendarts.ui.export.x01.defi.model;

import java.util.Date;

/**
 * The Class GameDetail.
 */
public class GameDetail {

	/** The duration. */
	private final double duration;

	/** The game. */
	private final GameDefi game;

	/** The score. */
	private final int score;

	/** The nb throws. */
	private final double nbThrows;

	/** The nb darts. */
	private final int nbDarts;

	/**
	 * Instantiates a new game detail.
	 *
	 * @param game the game
	 * @param duration the duration
	 * @param score the score
	 * @param nbThrows the nb throws
	 * @param nbDarts the nb darts
	 */
	public GameDetail(GameDefi game, double duration, int score,
			double nbThrows, int nbDarts) {
		super();
		this.game = game;
		this.duration = duration;
		this.score = score;
		this.nbThrows = nbThrows;
		this.nbDarts = nbDarts;
	}

	/**
	 * Gets the total time.
	 *
	 * @return the total time
	 */
	public String getTotalTime() {
		Date date = new Date(Double.valueOf(duration).longValue());
		return this.game.getGameDefinition().getTimeFormatter().format(date);
	}

	/**
	 * Gets the points by second.
	 *
	 * @return the points by second
	 */
	public String getPointsBySecond() {
		double pointBySec = ((double) score * 1000) / (duration);
		return this.game.getFormatters().getDecimalFormat().format(pointBySec);
	}

	/**
	 * Gets the seconds by throw.
	 *
	 * @return the seconds by throw
	 */
	public String getSecondsByThrow() {
		double secByThrow = ((double) duration) / (1000 * nbThrows);
		return this.game.getFormatters().getDecimalFormat().format(secByThrow);

	}

	/**
	 * Gets the average score.
	 *
	 * @return the average score
	 */
	public String getAverageScore() {
		double avg = ((double) score * 3) / nbDarts;
		return this.game.getFormatters().getDecimalFormat().format(avg);
	}

	/**
	 * Gets the nb throws.
	 *
	 * @return the nb throws
	 */
	public String getNbThrows() {
		return this.game.getFormatters().getNumberFormat().format(Math.ceil(nbThrows));
	}

	/**
	 * Gets the nb darts.
	 *
	 * @return the nb darts
	 */
	public String getNbDarts() {
		return this.game.getFormatters().getNumberFormat().format(Math.ceil(nbDarts));
	}

}
