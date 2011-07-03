package org.opendarts.prototype.model.session;


/**
 * The listener interface for receiving Set events.
 * The class that is interested in processing a Set
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addListener<code> method. When
 * the Set event occurs, that object's appropriate
 * method is invoked.
 *
 * @see SetEvent
 */
public interface ISetListener {

	/**
	 * Notify game event.
	 *
	 * @param event the event
	 */
	void notifySetEvent(SetEvent event);

}
