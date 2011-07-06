package org.opendarts.prototype.internal.model.game.x01;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opendarts.prototype.internal.model.dart.ThreeDartThrow;
import org.opendarts.prototype.internal.model.dart.x01.BrokenX01DartsThrow;
import org.opendarts.prototype.internal.model.dart.x01.WinningX01DartsThrow;
import org.opendarts.prototype.internal.model.game.AbstractGame;
import org.opendarts.prototype.internal.model.session.GameSet;
import org.opendarts.prototype.model.dart.InvalidDartThrowException;
import org.opendarts.prototype.model.game.GameEvent;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.game.IGameEntry;
import org.opendarts.prototype.model.player.IPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class Game X01.
 */
public class GameX01 extends AbstractGame implements IGame {

	/** The logger. */
	private static final Logger LOG = LoggerFactory.getLogger(GameX01.class);

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
	 * @param startScore 
	 */
	public GameX01(GameSet set, List<IPlayer> players, int startScore) {
		super(set, players);
		this.score = new HashMap<IPlayer, Integer>();
		this.entries = new ArrayList<GameX01Entry>();
		this.scoreToDo = startScore;
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
		this.newGameEntry();

		// Set first player
		this.setCurrentPlayer(this.getFirstPlayer());
	}

	/**
	 * New game entry.
	 * @return 
	 */
	private GameX01Entry newGameEntry() {
		GameX01Entry entry = new GameX01Entry(this, this.entries.size() + 1);
		this.entries.add(entry);
		this.setCurrentEntry(entry);
		return entry;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.IGame#getName()
	 */
	@Override
	public String getName() {
		String result;
		if (this.getPlayers().size() == 2) {
			result = MessageFormat.format("{2} - {0} vs {1}",
					this.getFirstPlayer(), this.getSecondPlayer(),
					this.scoreToDo);
		} else {
			result = MessageFormat.format("{1} - {0} ", this.getPlayers(),
					this.scoreToDo);
		}
		return result;
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
	 * Gets the second player.
	 *
	 * @return the second player
	 */
	public IPlayer getSecondPlayer() {
		return this.getPlayers().get(1);
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

	/**
	 * Adds the player throw.
	 *
	 * @param player the player
	 * @param dartThrow the dart throw
	 */
	public void addPlayerThrow(IPlayer player, ThreeDartThrow dartThrow) {
		ThreeDartThrow dThrow = dartThrow;
		if (dartThrow != null) {
			// update player score
			int score = this.getScore(player);
			score -= dartThrow.getScore();
			if (score < 2) {
				// broken
				try {
					dThrow = new BrokenX01DartsThrow(dartThrow);
				} catch (InvalidDartThrowException e) {
					LOG.error("WTF should not happen", e);
				}
			} else {
				this.score.put(player, score);
			}

			// Add darts
			GameX01Entry entry = (GameX01Entry) this.getCurrentEntry();
			entry.addPlayerThrow(player, dThrow);

			// Notify update
			this.fireGameEvent(GameEvent.Factory.newGameEntryUpdatedEvent(this,
					player, entry, dartThrow));

			// Choose next player
			List<IPlayer> players = this.getPlayers();
			int idx = players.indexOf(player);
			idx++;
			if (idx >= players.size()) {
				idx = 0;
				this.newGameEntry();
			}
			this.setCurrentPlayer(players.get(idx));
		}
	}

	/**
	* Update player throw.
	*
	* @param entry the entry
	* @param player the player
	* @param newThrow the new dart throw
	*/
	public void updatePlayerThrow(GameX01Entry entry, IPlayer player,
			ThreeDartThrow newThrow) {
		ThreeDartThrow oldThrow = entry.getPlayerThrow().get(player);
		if (oldThrow != null) {
			if (newThrow != null) {
				int score = this.getScore(player);
				score += oldThrow.getScore();
				score -= newThrow.getScore();
				entry.getPlayerThrow().put(player, newThrow);
				this.score.put(player, score);
			}
			this.fireGameEvent(GameEvent.Factory.newGameEntryUpdatedEvent(this,
					player, entry, newThrow));
		} else {
			this.addPlayerThrow(player, newThrow);
		}
	}

	/**
	 * Adds the winning player throw.
	 *
	 * @param player the player
	 * @param dartThrow the dart throw
	 */
	public void addWinningPlayerThrow(IPlayer player,
			WinningX01DartsThrow dartThrow) {
		// update player score
		this.score.put(player, 0);

		// Add darts
		GameX01Entry entry = (GameX01Entry) this.getCurrentEntry();
		entry.addPlayerThrow(player, dartThrow);
		entry.setNbPlayedDart(((entry.getRound() - 1) * 3)
				+ dartThrow.getNbDartToFinish());

		// Notify
		this.fireGameEvent(GameEvent.Factory.newGameEntryUpdatedEvent(this,
				player, entry, dartThrow));

		// Handle winning
		this.end(player);
		this.fireGameEvent(GameEvent.Factory.newGameFinishedEvent(this,
				this.getWinner(), entry, dartThrow));
		this.getParentSet().handleFinishedGame(this);
	}

	/**
	 * Gets the winning message.
	 *
	 * @return the winning message
	 */
	public String getWinningMessage() {
		String result = "";
		if (this.getWinner() != null) {
			GameX01Entry entry = (GameX01Entry) this.getCurrentEntry();
			result = MessageFormat.format("{0} win with {1} darts",
					this.getWinner(), entry.getNbPlayedDart());
		} else {
			result = "Draw game";
		}
		return result;
	}
}
