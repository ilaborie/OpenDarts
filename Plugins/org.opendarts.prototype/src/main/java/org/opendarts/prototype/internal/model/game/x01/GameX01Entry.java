/*
 * 
 */
package org.opendarts.prototype.internal.model.game.x01;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.opendarts.prototype.internal.model.dart.ThreeDartThrow;
import org.opendarts.prototype.model.game.IGameEntry;
import org.opendarts.prototype.model.player.IPlayer;

/**
 * The Class GameX01Entry.
 */
public class GameX01Entry implements IGameEntry {

	/** The game x01. */
	private final GameX01 gameX01;

	/** The player throw. */
	private final Map<IPlayer, ThreeDartThrow> playerThrow;

	/** The round. */
	private final int round;

	/** The nb played dart. */
	private int nbPlayedDart;

	/**
	 * Instantiates a new game x01 entry.
	 *
	 * @param game the game
	 * @param round the round
	 */
	public GameX01Entry(GameX01 game, int round) {
		super();
		this.gameX01 = game;
		this.round = round;
		this.playerThrow = new HashMap<IPlayer, ThreeDartThrow>();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return MessageFormat.format("[{0}] {1}", this.round, this.playerThrow);
	}

	/**
	 * Adds the player throw.
	 *
	 * @param player the player
	 * @param newThrow the new throw
	 * @return true, if the game could be finished
	 */
	public boolean addPlayerThrow(IPlayer player, ThreeDartThrow newThrow) {
		this.playerThrow.put(player, newThrow);
		// check if could be win
		Integer startingScore = this.gameX01.getScore(player);
		return (startingScore != null && (startingScore == newThrow.getScore()));
	}

	/**
	 * Gets the nb played dart.
	 *
	 * @return the nb played dart
	 */
	public int getNbPlayedDart() {
		return this.nbPlayedDart;
	}

	/**
	 * Sets the nb played dart.
	 *
	 * @param nbPlayedDart the new nb played dart
	 */
	public void setNbPlayedDart(int nbPlayedDart) {
		this.nbPlayedDart = nbPlayedDart;
	}

	/**
	 * Gets the player throw.
	 *
	 * @return the player throw
	 */
	public Map<IPlayer, ThreeDartThrow> getPlayerThrow() {
		return this.playerThrow;
	}

	/**
	 * Gets the round.
	 *
	 * @return the round
	 */
	public int getRound() {
		return this.round;
	}

	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	public GameX01 getGame() {
		return this.gameX01;
	}

}
