/*
 * 
 */
package org.opendarts.core.x01.model;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.opendarts.core.model.dart.impl.ThreeDartsThrow;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;

/**
 * The Class GameX01Entry.
 */
public class GameX01Entry implements IGameEntry {

	/** The game x01. */
	private final GameX01 gameX01;

	/** The player throw. */
	private final Map<IPlayer, ThreeDartsThrow> playerThrow;
	private final Map<IPlayer, Integer> playerScoreLeft;

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
		this.playerThrow = new HashMap<IPlayer, ThreeDartsThrow>();
		this.playerScoreLeft = new HashMap<IPlayer, Integer>();
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
	protected boolean addPlayerThrow(IPlayer player, ThreeDartsThrow newThrow) {
		this.playerThrow.put(player, newThrow);
		// check if could be win
		Integer startingScore = this.gameX01.getScore(player);
		return ((startingScore != null) && (startingScore == newThrow
				.getScore()));
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
	public Map<IPlayer, ThreeDartsThrow> getPlayerThrow() {
		return this.playerThrow;
	}
	
	/**
	 * Gets the player score left.
	 *
	 * @return the player score left
	 */
	public Map<IPlayer, Integer> getPlayerScoreLeft() {
		return this.playerScoreLeft;
	}

	/**
	 * Gets the round.
	 *
	 * @return the round
	 */
	@Override
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
