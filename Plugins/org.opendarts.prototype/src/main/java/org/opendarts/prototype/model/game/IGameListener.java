package org.opendarts.prototype.model.game;

/**
 * The listener interface for receiving Game events.
 * The class that is interested in processing a Game
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addListener<code> method. When
 * the Game event occurs, that object's appropriate
 * method is invoked.
 *
 * @see GameEvent
 */
public interface IGameListener {

	/**
	 * Notify game event.
	 *
	 * @param event the event
	 */
	void notifyGameEvent(GameEvent event);

}
