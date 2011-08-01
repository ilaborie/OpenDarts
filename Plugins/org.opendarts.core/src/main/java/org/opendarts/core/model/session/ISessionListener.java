package org.opendarts.core.model.session;

/**
 * The listener interface for receiving Session events.
 * The class that is interested in processing a Session
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addListener<code> method. When
 * the Session event occurs, that object's appropriate
 * method is invoked.
 *
 * @see SessionEvent
 */
public interface ISessionListener {

	/**
	 * Notify game event.
	 *
	 * @param event the event
	 */
	void notifySessionEvent(SessionEvent event);

}
