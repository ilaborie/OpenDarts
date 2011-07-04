package org.opendarts.prototype.internal.model.game.x01;

import java.util.List;

import org.opendarts.prototype.internal.model.game.GameDefinition;
import org.opendarts.prototype.model.player.IPlayer;

public class GameX01Definition extends GameDefinition {

	/** The start score. */
	private final int startScore;

	/**
	 * Instantiates a new game x01 definition.
	 *
	 * @param startScore the start score
	 * @param players the players
	 * @param nbGameToWin the number of games to win
	 * @param playAllGames the play all games
	 */
	public GameX01Definition(int startScore, List<IPlayer> players,
			int nbGameToWin, boolean playAllGames) {
		super(players, nbGameToWin, playAllGames);
		this.startScore = startScore;
	}

	/**
	 * Gets the start score.
	 *
	 * @return the start score
	 */
	public int getStartScore() {
		return this.startScore;
	}

}
