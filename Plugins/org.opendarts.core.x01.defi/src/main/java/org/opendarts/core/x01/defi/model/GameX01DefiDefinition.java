package org.opendarts.core.x01.defi.model;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.opendarts.core.model.game.impl.GameDefinition;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.service.game.IGameService;
import org.opendarts.core.x01.defi.service.GameX01DefiService;

/**
 * The Class GameX01Definition.
 */
public class GameX01DefiDefinition extends GameDefinition {

	/** The start score. */
	private final int startScore;

	/** The game service. */
	private final IGameService gameService;

	/** The delay. */
	private final int delay;
	
	/** The time target. */
	private final long timeTarget;
	
	/** The time formatter. */
	public static final DateFormat TIME_FORMATTER;

	static {
		TIME_FORMATTER = new SimpleDateFormat("H:mm:ss");
		TIME_FORMATTER.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	/**
	 * Instantiates a new game x01 definition.
	 *
	 * @param startScore the start score
	 * @param players the players
	 */
	public GameX01DefiDefinition(int startScore, int delay, long timeTarget, List<IPlayer> players) {
		super(players, 1,false);
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
		return NumberFormat.getIntegerInstance().format(this.getStartScore());
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
