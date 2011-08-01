package org.opendarts.ui.dialog;

import org.eclipse.jface.dialogs.IMessageProvider;

/**
 * The Class ValidationEntry.
 */
public class ValidationEntry implements IMessageProvider {

	/** The message. */
	private final String message;

	/** The type. */
	private final int type;

	/**
	 * Instantiates a new validation entry.
	 *
	 * @param type the type
	 * @param message the message
	 */
	public ValidationEntry(int type, String message) {
		super();
		this.type = type;
		this.message = message;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	@Override
	public String getMessage() {
		return this.message;
	}

	/**
	 * Gets the message type.
	 *
	 * @return the message type
	 */
	@Override
	public int getMessageType() {
		return this.type;
	}

}
