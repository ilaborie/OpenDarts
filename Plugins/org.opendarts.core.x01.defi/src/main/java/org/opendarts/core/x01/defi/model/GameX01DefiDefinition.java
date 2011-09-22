package org.opendarts.core.x01.defi.model;

import java.util.List;

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

	/**
	 * Instantiates a new game x01 definition.
	 *
	 * @param startScore the start score
	 * @param players the players
	 * @param nbGameToWin the number of games to win
	 * @param playAllGames the play all games
	 */
	public GameX01DefiDefinition(int startScore, List<IPlayer> players) {
		super(players, 1,false);
		this.startScore = startScore;
		this.gameService = new GameX01DefiService();
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
