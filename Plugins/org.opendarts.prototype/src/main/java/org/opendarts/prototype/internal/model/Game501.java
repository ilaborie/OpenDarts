package org.opendarts.prototype.internal.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opendarts.prototype.model.IGame;
import org.opendarts.prototype.model.IGameDefinition;
import org.opendarts.prototype.model.IPlayer;

/**
 * The Class Game501.
 */
public class Game501 implements IGame {

	/** The start. */
	private final Calendar start;
	
	/** The end. */
	private Calendar end;
	
	/** The winner. */
	private IPlayer winner;

	/** The game def. */
	private final IGameDefinition gameDef;

	/** The players. */
	private final List<IPlayer> players;

	/** The set. */
	private final GameSet set;

	/** The score. */
	private final Map<IPlayer, Integer> score;

	/**
	 * Instantiates a new game container.
	 *
	 * @param set the set
	 * @param gameDef the game definition
	 * @param players the players
	 */
	public Game501(GameSet set, GameDefinition gameDef, IPlayer... players) {
		super();
		this.start = Calendar.getInstance();
		this.set = set;
		this.gameDef = gameDef;
		this.players = new ArrayList<IPlayer>(Arrays.asList(players));
		this.score = new HashMap<IPlayer, Integer>();
	}

	/**
	 * Initialize the game.
	 */
	public void initGame() {
		// Set score to 501;
		for (IPlayer player : this.players) {
			this.score.put(player, 501);
		}
		// TODO ...
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.IGame#getName()
	 */
	@Override
	public String getName() {
		return MessageFormat.format("501 - {0} vs {1}", this.players.get(0),
				this.players.get(1));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getName();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.IGame#getDescription()
	 */
	@Override
	public String getDescription() {
		return this.getName();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.IAbstractGame#getStart()
	 */
	@Override
	public Calendar getStart() {
		return this.start;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.IAbstractGame#isFinished()
	 */
	@Override
	public boolean isFinished() {
		return (this.end != null);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.IAbstractGame#getEnd()
	 */
	@Override
	public Calendar getEnd() {
		return this.end;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.IAbstractGame#getWinner()
	 */
	@Override
	public IPlayer getWinner() {
		return this.winner;
	}


	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.IGame#getPlayers()
	 */
	@Override
	public List<IPlayer> getPlayers() {
		return Collections.unmodifiableList(this.players);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.IGame#getGameDefinition()
	 */
	@Override
	public IGameDefinition getGameDefinition() {
		return this.gameDef;
	}

	/**
	 * Gets the game def.
	 *
	 * @return the game def
	 */
	public IGameDefinition getGameDef() {
		return this.gameDef;
	}

	/**
	 * Gets the sets the.
	 *
	 * @return the sets the
	 */
	public GameSet getSet() {
		return this.set;
	}

	/**
	 * Sets the end.
	 *
	 * @param end the new end
	 */
	public void setEnd(Calendar end) {
		this.end = end;
	}

	/**
	 * Sets the winner.
	 *
	 * @param winner the new winner
	 */
	public void setWinner(IPlayer winner) {
		this.winner = winner;
	}

	/**
	 * Gets the score.
	 *
	 * @param player the player
	 * @return the score
	 */
	public Integer getScore(IPlayer player) {
		return this.score.get(player);
	}

}
