package org.opendarts.core.x01.defi.model;

import java.util.Calendar;
import java.util.List;

import org.opendarts.core.model.dart.impl.ThreeDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.impl.GameSet;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.core.x01.defi.OpenDartsX01DefiBundle;
import org.opendarts.core.x01.model.GameX01;
import org.opendarts.core.x01.model.GameX01Entry;
import org.opendarts.core.x01.model.WinningX01DartsThrow;

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
		long duration;
		Calendar cal = this.getEnd();
		if (cal == null) {
			duration = System.currentTimeMillis()
					- this.getStart().getTimeInMillis();
		} else {
			duration = cal.getTimeInMillis()
					- this.getStart().getTimeInMillis();
		}
		return duration;
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
		int nbDartsToFinish = 0;
		if (this.isFinished()) {
			GameX01Entry entry = (GameX01Entry) this.getCurrentEntry();
			nbDartsToFinish = ((entry.getRound() - 1) * 3)* this.getPlayers().size();
			for (ThreeDartsThrow dartThrow : entry.getPlayerThrow().values()) {
				if (dartThrow instanceof WinningX01DartsThrow) {
					nbDartsToFinish += ((WinningX01DartsThrow) dartThrow)
							.getNbDartToFinish();
				} else {
					nbDartsToFinish += 3;
				}
			}
		}
		return nbDartsToFinish;
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
