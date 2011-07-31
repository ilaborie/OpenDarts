package org.opendarts.prototype.internal.model.game.x01;

import java.util.List;

import org.opendarts.prototype.internal.model.game.GameDefinition;
import org.opendarts.prototype.internal.service.game.x01.GameX01Service;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.service.game.IGameService;

/**
 * The Class GameX01Definition.
 */
public class GameX01Definition extends GameDefinition {

	/** The start score. */
	private final int startScore;

	/** The game service. */
	private final IGameService gameService;

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
		this.gameService = new GameX01Service();
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

}
