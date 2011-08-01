package org.opendarts.core.model.session;

import java.text.MessageFormat;
import java.util.Calendar;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;

/**
 * The Class SetEvent.
 */
public class SetEvent {

	/**
	 * The Enum GameEventType.
	 */
	public static enum SetEventType {
		/** The SE t_ initialized. */
		SET_INITIALIZED,
		/** The SE t_ canceled. */
		SET_CANCELED,
		/** The SE t_ finished. */
		SET_FINISHED,
		/** The NE w_ curren t_ game. */
		NEW_CURRENT_GAME;
	}

	/** The game. */
	private IGame game;

	/** The player. */
	private IPlayer player;

	/** The Set. */
	private final ISet set;

	/** The type. */
	private final SetEventType type;

	/** The time. */
	private final Calendar time;

	/**
	 * Instantiates a new sets the event.
	 *
	 * @param type the type
	 * @param set the set
	 */
	private SetEvent(SetEventType type, ISet set) {
		super();
		this.time = Calendar.getInstance();
		this.type = type;
		this.set = set;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return MessageFormat.format(
				"[SetEvent] {0} - {1} with '{'{2}, {3}, {4} '}' at {5}",
				this.set, this.type, this.game, this.player,
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
	 * Gets the sets the.
	 *
	 * @return the sets the
	 */
	public ISet getSet() {
		return this.set;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public SetEventType getType() {
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
		 * New set initialized event.
		 *
		 * @param set the set
		 * @return the sets the event
		 */
		public static SetEvent newSetInitializedEvent(ISet set) {
			SetEvent result = new SetEvent(SetEventType.SET_INITIALIZED, set);
			return result;
		}

		/**
		 * New set finished event.
		 *
		 * @param set the set
		 * @param winner the winner
		 * @param game the game
		 * @return the sets the event
		 */
		public static SetEvent newSetFinishedEvent(ISet set, IPlayer winner,
				IGame game) {
			SetEvent result = new SetEvent(SetEventType.SET_FINISHED, set);
			result.player = winner;
			result.game = game;
			return result;
		}

		/**
		 * New set canceled event.
		 *
		 * @param set the set
		 * @return the sets the event
		 */
		public static SetEvent newSetCanceledEvent(ISet set) {
			SetEvent result = new SetEvent(SetEventType.SET_CANCELED, set);
			return result;
		}

		/**
		 * New set game event.
		 *
		 * @param set the set
		 * @param game the game
		 * @return the sets the event
		 */
		public static SetEvent newSetGameEvent(ISet set, IGame game) {
			SetEvent result = new SetEvent(SetEventType.NEW_CURRENT_GAME, set);
			result.game = game;
			return result;
		}
	}

}
