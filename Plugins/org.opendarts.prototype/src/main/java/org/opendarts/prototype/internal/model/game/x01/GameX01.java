package org.opendarts.prototype.internal.model.game.x01;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opendarts.prototype.internal.model.game.AbstractGame;
import org.opendarts.prototype.internal.model.game.GameDefinition;
import org.opendarts.prototype.internal.model.session.GameSet;
import org.opendarts.prototype.model.game.GameEvent;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.game.IGameEntry;
import org.opendarts.prototype.model.player.IPlayer;

/**
 * The Class Game X01.
 */
public class GameX01 extends AbstractGame implements IGame {

	/** The current player. */
	private IPlayer currentPlayer;

	/** The score. */
	private final Map<IPlayer, Integer> score;

	/** The entries. */
	private final List<GameX01Entry> entries;

	/** The score to do. */
	private final int scoreToDo;

	/**
	 * Instantiates a new game container.
	 *
	 * @param set the set
	 * @param gameDef the game definition
	 * @param players the players
	 */
	public GameX01(GameSet set, GameDefinition gameDef, IPlayer... players) {
		super(set,gameDef,players);
		this.score = new HashMap<IPlayer, Integer>();
		this.entries = new ArrayList<GameX01Entry>();
		this.scoreToDo = 501;
	}

	/**
	 * Initialize the game.
	 */
	public void initGame() {
		this.setStart(Calendar.getInstance());

		// Clear entries
		this.entries.clear();

		// Set score to 501
		for (IPlayer player : this.getPlayers()) {
			this.score.put(player, this.scoreToDo);
		}
		this.fireGameEvent(GameEvent.Factory.newGameInitializedEvent(this));

		// Create first entry
		GameX01Entry entry = this.newGameEntry();

		// Set first player
		this.currentPlayer = this.getFirstPlayer();
		this.fireGameEvent(GameEvent.Factory.newCurrentPlayerEvent(this,
				this.currentPlayer, entry));
	}

	/**
	 * New game entry.
	 * @return 
	 */
	private GameX01Entry newGameEntry() {
		GameX01Entry entry = new GameX01Entry(this, this.entries.size() + 1);
		this.entries.add(entry);
		this.fireGameEvent(GameEvent.Factory.newGameEntryCreatedEvent(this,
				entry));
		return entry;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.IGame#getName()
	 */
	@Override
	public String getName() {
		return MessageFormat.format("501 - {0} vs {1}", this.getFirstPlayer(),
				this.getSecondPlayer());
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

	/**
	 * Gets the first player.
	 *
	 * @return the first player
	 */
	public IPlayer getFirstPlayer() {
		return this.getPlayers().get(0);
	}

	/**
	 * Gets the second player.
	 *
	 * @return the second player
	 */
	public IPlayer getSecondPlayer() {
		return this.getPlayers().get(1);
	}


	/**
	 * Gets the current player.
	 *
	 * @return the current player
	 */
	public IPlayer getCurrentPlayer() {
		return this.currentPlayer;
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

	/**
	 * Gets the score to do.
	 *
	 * @return the score to do
	 */
	public int getScoreToDo() {
		return this.scoreToDo;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.game.IGame#getGameEntries()
	 */
	@Override
	public List<? extends IGameEntry> getGameEntries() {
		return Collections.unmodifiableList(this.entries);
	}

}
