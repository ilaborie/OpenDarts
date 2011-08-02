package org.opendarts.core.model.session;

import java.text.MessageFormat;
import java.util.Calendar;

import org.opendarts.core.model.player.IPlayer;

/**
 */
public class SessionEvent {

	/**
	 */
	public static enum SessionEventType {

		SESSION_INITIALIZED, SESSION_CANCELED, SESSION_FINISHED, NEW_CURRENT_SET;

	}

	private final ISession session;

	/** The player. */
	private IPlayer player;

	/** The Set. */
	private ISet set;

	/** The type. */
	private final SessionEventType type;

	/** The time. */
	private final Calendar time;

	/**
	 *
	 */
	private SessionEvent(SessionEventType type, ISession session) {
		super();
		this.time = Calendar.getInstance();
		this.type = type;
		this.session = session;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return MessageFormat.format(
				"[SessionEvent] {0} - {1} with '{'{2}, {3}'}' at {4}",
				this.session, this.type, this.set, this.player,
				this.time.getTime());
	}

	/**
	 * Gets the player.
	 *
	 * @return the player
	 */
	public IPlayer getPlayer() {
		return this.player;
	}

	public ISession getSession() {
		return this.session;
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
	public SessionEventType getType() {
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
		 *
		 * @param set the set
		 * @return the sets the event
		 */
		public static SessionEvent newSessionInitializedEvent(ISession session) {
			SessionEvent result = new SessionEvent(
					SessionEventType.SESSION_INITIALIZED, session);
			return result;
		}

		/**
		 *
		 * @param set the set
		 * @param winner the winner
		 * @param game the game
		 * @return the sets the event
		 */
		public static SessionEvent newSessionFinishEvent(ISession session,
				IPlayer winner, ISet set) {
			SessionEvent result = new SessionEvent(
					SessionEventType.SESSION_FINISHED, session);
			result.player = winner;
			result.set = set;
			return result;
		}

		/**
		 *
		 * @param set the set
		 * @return the sets the event
		 */
		public static SessionEvent newSessionCanceledEvent(ISession session) {
			SessionEvent result = new SessionEvent(
					SessionEventType.SESSION_CANCELED, session);
			return result;
		}

		/**
		 * New set game event.
		 *
		 * @param set the set
		 * @param game the game
		 * @return the sets the event
		 */
		public static SessionEvent newSessionSetEvent(ISession session, ISet set) {
			SessionEvent result = new SessionEvent(
					SessionEventType.NEW_CURRENT_SET, session);
			result.set = set;
			return result;
		}
	}

}
