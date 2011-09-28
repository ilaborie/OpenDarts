package org.opendarts.core.x01.defi.model;

import java.util.List;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.impl.GameSet;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.core.x01.defi.OpenDartsX01DefiBundle;
import org.opendarts.core.x01.model.GameX01;

/**
 * The Class Game X01.
 */
public class GameX01Defi extends GameX01 implements IGame {

	/** The score. */
	private int score;

	private IStatsService statsService;

	/**
	 * Instantiates a new game container.
	 *
	 * @param set the set
	 * @param gameDef the game definition
	 * @param players the players
	 * @param startScore 
	 */
	public GameX01Defi(GameSet set, List<IPlayer> players, int startScore) {
		super(set, players, startScore);
		this.score = startScore;
		// Stats
		this.statsService = OpenDartsX01DefiBundle.getStatsService(this);
	}

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public long getDuration() {
		return System.currentTimeMillis() - this.getStart().getTimeInMillis();
	}

	/**
	 * Gets the forecast.
	 *
	 * @return the forecast
	 */
	public long getForecast() {
		long result;
		if (this.score == 0) {
			result = 0;
		} else {

			int startScore = ((GameX01DefiDefinition) this.getParentSet()
					.getGameDefinition()).getStartScore();
			double delta = ((double) this.getDuration())
					/ ((double) this.score);
			result = (long) (delta * startScore);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.x01.model.GameX01#getNbDartToFinish()
	 */
	@Override
	public Integer getNbDartToFinish() {
		return super.getNbDartToFinish();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.x01.model.GameX01#getStatsService()
	 */
	@Override
	public IStatsService getStatsService() {
		return this.statsService;
	}

	/**
	 * Gets the score.
	 *
	 * @param player the player
	 * @return the score
	 */
	public Integer getScore(IPlayer player) {
		return this.score;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.x01.model.GameX01#setScore(org.opendarts.core.model.player.IPlayer, int)
	 */
	@Override
	protected void setScore(IPlayer player, int score) {
		this.score = score;
	}

	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public int getScore() {
		return this.score;
	}

}
