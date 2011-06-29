package org.opendarts.prototype.internal.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.opendarts.prototype.model.IGame;
import org.opendarts.prototype.model.IGameDefinition;
import org.opendarts.prototype.model.IPlayer;

/**
 * The Class Game.
 */
public class Game implements IGame {

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

	/**
	 * Instantiates a new game container.
	 *
	 * @param set the set
	 * @param gameDef the game def
	 * @param players the players
	 */
	public Game(GameSet set, GameDefinition gameDef, IPlayer... players) {
		super();
		this.start = Calendar.getInstance();
		this.set = set;
		this.gameDef = gameDef;
		this.players = new ArrayList<IPlayer>(Arrays.asList(players));
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

}
