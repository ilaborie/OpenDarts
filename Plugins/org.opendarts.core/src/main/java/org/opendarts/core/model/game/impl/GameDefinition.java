package org.opendarts.core.model.game.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opendarts.core.model.game.IGameDefinition;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISet;

/**
 * The Class GameDefinition.
 */
public abstract class GameDefinition implements IGameDefinition {

	/** The players. */
	private final List<IPlayer> initialPlayers;

	/** The players. */
	private final List<IPlayer> players;

	/** The players. */
	private List<IPlayer> lastPlayers;

	/** The play all games. */
	private final boolean playAllGames;

	/** The nb game to win. */
	private final int nbGameToWin;

	/**
	 * Instantiates a new game definition.
	 *
	 * @param players the players
	 * @param nbGameToWin the nb game to win
	 * @param playAllGames the play all games
	 */
	public GameDefinition(List<IPlayer> players, int nbGameToWin,
			boolean playAllGames) {
		super();
		this.initialPlayers = new ArrayList<IPlayer>(players);
		this.players = new ArrayList<IPlayer>(players);
		this.lastPlayers = new ArrayList<IPlayer>(players);
		this.nbGameToWin = nbGameToWin;
		this.playAllGames = playAllGames;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result;
		if (this.playAllGames) {
			result = "Best of " + this.nbGameToWin;
		} else {
			result = "First at " + this.nbGameToWin;
		}
		return result;
	}

	/**
	 * Instantiates a new game definition.
	 *
	 * @param players the players
	 * @param nbGameToWin the nb game to win
	 */
	public GameDefinition(List<IPlayer> players, int nbGameToWin) {
		this(players, nbGameToWin, false);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.game.IGameDefinition#getPlayers()
	 */
	@Override
	public List<IPlayer> getPlayers() {
		return this.players;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.game.IGameDefinition#getNextPlayers(org.opendarts.prototype.model.session.ISet)
	 */
	@Override
	public List<IPlayer> getNextPlayers(ISet set) {
		this.lastPlayers = new ArrayList<IPlayer>(this.lastPlayers);
		Collections.rotate(this.players, -1);
		return this.players;
	}

	/**
	 * Gets the initial players.
	 *
	 * @return the initial players
	 */
	@Override
	public List<IPlayer> getInitialPlayers() {
		return Collections.unmodifiableList(this.initialPlayers);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.game.IGameDefinition#isPlayerWin(org.opendarts.prototype.model.session.ISet, org.opendarts.prototype.model.player.IPlayer)
	 */
	@Override
	public boolean isPlayerWin(ISet set, IPlayer player) {
		int winningGames = set.getWinningGames(player);
		return (this.nbGameToWin <= winningGames);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.game.IGameDefinition#isSetFinished(org.opendarts.prototype.model.session.ISet)
	 */
	@Override
	public boolean isSetFinished(ISet set) {
		boolean result = false;
		if (this.playAllGames) {
			int nbGamesToPlay = (this.nbGameToWin * 2) - 1;
			result = (nbGamesToPlay == set.getAllGame().size());
		} else {
			result = (set.getWinner() != null);
		}
		return result;
	}

	/**
	 * Gets the nb game to win.
	 *
	 * @return the nb game to win
	 */
	@Override
	public int getNbGameToWin() {
		return this.nbGameToWin;
	}

	/**
	 * Checks if is play all games.
	 *
	 * @return true, if is play all games
	 */
	public boolean isPlayAllGames() {
		return this.playAllGames;
	}

}
