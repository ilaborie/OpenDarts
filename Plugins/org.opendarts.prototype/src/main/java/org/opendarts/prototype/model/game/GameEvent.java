package org.opendarts.prototype.model.game;

import java.text.MessageFormat;
import java.util.Calendar;

import org.opendarts.prototype.model.dart.IDartsThrow;
import org.opendarts.prototype.model.player.IPlayer;

/**
 * The Class SetEvent.
 */
public class GameEvent {

	/**
	 * The Enum GameEventType.
	 */
	public static enum GameEventType {

		/** The GAM e_ initialized. */
		GAME_INITIALIZED,

		/** The GAM e_ canceled. */
		GAME_CANCELED,

		/** The GAM e_ finished. */
		GAME_FINISHED,

		/** The GAM e_ entr y_ created. */
		GAME_ENTRY_CREATED,

		/** The GAM e_ entr y_ updated. */
		GAME_ENTRY_UPDATED,

		/** The NE w_ curren t_ player. */
		NEW_CURRENT_PLAYER;
	}

	/** The game. */
	private final IGame game;

	/** The player. */
	private IPlayer player;

	/** The darts throw. */
	private IDartsThrow dartsThrow;

	/** The entry. */
	private IGameEntry entry;

	/** The type. */
	private final GameEventType type;

	/** The time. */
	private final Calendar time;

	/**
	 * Instantiates a new game event.
	 *
	 * @param type the type
	 * @param game the game
	 */
	private GameEvent(GameEventType type, IGame game) {
		super();
		this.time = Calendar.getInstance();
		this.type = type;
		this.game = game;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return MessageFormat.format(
				"[SetEvent] {0} - {1} with '{'{2}, {3}, {4} '}' at {5}",
				this.game, this.type, this.entry, this.player, this.dartsThrow,
				this.time.getTime());
	}

	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	public IGame getGame() {
		return this.game;
	}

	/**
	 * Gets the player.
	 *
	 * @return the player
	 */
	public IPlayer getPlayer() {
		return this.player;
	}

	/**
	 * Gets the darts throw.
	 *
	 * @return the darts throw
	 */
	public IDartsThrow getDartsThrow() {
		return this.dartsThrow;
	}

	/**
	 * Gets the entry.
	 *
	 * @return the entry
	 */
	public IGameEntry getEntry() {
		return this.entry;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public GameEventType getType() {
		return this.type;
	}

	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public Calendar getTime() {
		return this.time;
	}

	/**
	 * The Class Factory.
	 */
	public static class Factory {
		/**
		 * Instantiates a new factory.
		 */
		private Factory() {
			super();
		}

		/**
		 * New game initialized event.
		 *
		 * @param game the game
		 * @return the game event
		 */
		public static GameEvent newGameInitializedEvent(IGame game) {
			GameEvent result = new GameEvent(GameEventType.GAME_INITIALIZED,
					game);
			return result;
		}

		/**
		 * New game finished event.
		 *
		 * @param game the game
		 * @param winner the winner
		 * @param entry the entry
		 * @param dartsThrow the darts throw
		 * @return the game event
		 */
		public static GameEvent newGameFinishedEvent(IGame game,
				IPlayer winner, IGameEntry entry, IDartsThrow dartsThrow) {
			GameEvent result = new GameEvent(GameEventType.GAME_FINISHED, game);
			result.player = winner;
			result.entry = entry;
			result.dartsThrow = dartsThrow;
			return result;
		}

		/**
		 * New game canceled event.
		 *
		 * @param game the game
		 * @return the game event
		 */
		public static GameEvent newGameCanceledEvent(IGame game) {
			GameEvent result = new GameEvent(GameEventType.GAME_CANCELED, game);
			return result;
		}

		/**
		 * New game entry created event.
		 *
		 * @param game the game
		 * @param entry the entry
		 * @return the game event
		 */
		public static GameEvent newGameEntryCreatedEvent(IGame game,
				IGameEntry entry) {
			GameEvent result = new GameEvent(GameEventType.GAME_ENTRY_CREATED,
					game);
			result.entry = entry;
			return result;
		}

		/**
		 * New game entry updated event.
		 *
		 * @param game the game
		 * @param player the player
		 * @param entry the entry
		 * @param dartsThrow the darts throw
		 * @return the game event
		 */
		public static GameEvent newGameEntryUpdatedEvent(IGame game,
				IPlayer player, IGameEntry entry, IDartsThrow dartsThrow) {
			GameEvent result = new GameEvent(GameEventType.GAME_ENTRY_UPDATED,
					game);
			result.player = player;
			result.entry = entry;
			result.dartsThrow = dartsThrow;
			return result;
		}

		/**
		 * New current player event.
		 *
		 * @param game the game
		 * @param player the player
		 * @param entry the entry
		 * @return the game event
		 */
		public static GameEvent newCurrentPlayerEvent(IGame game,
				IPlayer player, IGameEntry entry) {
			GameEvent result = new GameEvent(GameEventType.NEW_CURRENT_PLAYER,
					game);
			result.player = player;
			result.entry = entry;
			return result;
		}
	}

}
