package org.opendarts.core.x01.defi.model;

import java.text.DateFormat;
import java.util.List;

import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.service.game.IGameService;
import org.opendarts.core.utils.FormaterUtils;
import org.opendarts.core.x01.defi.service.GameX01DefiService;
import org.opendarts.core.x01.model.GameX01Definition;

/**
 * The Class GameX01Definition.
 */
public class GameX01DefiDefinition extends GameX01Definition {

	/** The start score. */
	private final int startScore;

	/** The game service. */
	private final IGameService gameService;

	/** The delay. */
	private final int delay;

	/** The time target. */
	private final long timeTarget;

	/** The time formatter. */
	public static final DateFormat TIME_FORMATTER = FormaterUtils
			.getFormatters().getTimeFormat();

	/**
	 * Instantiates a new game x01 definition.
	 *
	 * @param startScore the start score
	 * @param players the players
	 */
	public GameX01DefiDefinition(int startScore, int delay, long timeTarget,
			List<IPlayer> players) {
		super(startScore, players, 1, false);
		this.startScore = startScore;
		this.delay = delay;
		this.gameService = new GameX01DefiService();
		this.timeTarget = timeTarget;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.model.game.impl.GameDefinition#toString()
	 */
	@Override
	public String toString() {
		return FormaterUtils.getFormatters().getNumberFormat()
				.format(this.getStartScore());
	}

	/**
	 * Gets the start score.
	 *
	 * @return the start score
	 */
	public int getStartScore() {
		return this.startScore;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.game.IGameDefinition#getGameService()
	 */
	@Override
	public IGameService getGameService() {
		return this.gameService;
	}

	/**
	 * Gets the time target.
	 *
	 * @return the time target
	 */
	public long getTimeTarget() {
		return this.timeTarget;
	}

	/**
	 * Gets the time formatter.
	 *
	 * @return the time formatter
	 */
	public DateFormat getTimeFormatter() {
		return TIME_FORMATTER;
	}

	/**
	 * Gets the delay.
	 *
	 * @return the delay
	 */
	public int getDelay() {
		return this.delay;
	}

}
