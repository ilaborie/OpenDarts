package org.opendarts.ui.utils.shortcut;

import java.text.MessageFormat;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

/**
 * The Class AbstractShortcut.
 */
public abstract class AbstractShortcut implements IShortcut {

	/** The key code. */
	private final int keyCode;

	/** The state mask. */
	private final int stateMask;

	/**
	 * Instantiates a new value shortcut.
	 *
	 * @param keyCode the key code
	 * @param stateMask the state mask
	 */
	public AbstractShortcut(int keyCode, int stateMask) {
		super();
		this.keyCode = keyCode;
		this.stateMask = stateMask;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.ui.x01.utils.IShortcut#apply(int, int)
	 */
	@Override
	public boolean apply(int keyCode, int stateMask) {
		return (this.keyCode == keyCode) && (this.stateMask == stateMask);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return MessageFormat.format("{0}:{1}", this.getKeyLabel(),
				this.getLabel());
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.ui.x01.utils.IShortcut#getKeyLabel()
	 */
	@Override
	public String getKeyLabel() {
		String result;
		switch (this.keyCode) {
			case SWT.F1:
				result = "F1";
				break;
			case SWT.F2:
				result = "F2";
				break;
			case SWT.F3:
				result = "F3";
				break;
			case SWT.F4:
				result = "F4";
				break;
			case SWT.F5:
				result = "F5";
				break;
			case SWT.F6:
				result = "F6";
				break;
			case SWT.F7:
				result = "F7";
				break;
			case SWT.F8:
				result = "F8";
				break;
			case SWT.F9:
				result = "F9";
				break;
			case SWT.F10:
				result = "F10";
				break;
			case SWT.F11:
				result = "F11";
				break;
			case SWT.F12:
				result = "F12";
				break;
			default:
				char c = (char) (this.keyCode);
				result = String.valueOf(c);
				break;
		}

		switch (this.stateMask) {
			case SWT.SHIFT:
				result = "Shift+" + result;
				break;
			case SWT.CTRL:
				result = "Ctrl+" + result;
				break;
			case SWT.ALT:
				result = "Alt+" + result;
				break;
			default:
				break;
		}

		return result;
	}

	/**
	 * Apply error.
	 *
	 * @param msg the message
	 * @param inputText 
	 * @param dec 
	 */
	protected void applyError(String msg, ControlDecoration dec, Text inputText) {
		// Decoration
		if (dec != null) {
			FieldDecoration errorFieldIndicator = FieldDecorationRegistry
					.getDefault().getFieldDecoration(
							FieldDecorationRegistry.DEC_ERROR);
			dec.setImage(errorFieldIndicator.getImage());
			dec.setDescriptionText(msg);
			dec.setImage(errorFieldIndicator.getImage());
			dec.show();
		}
		// handler input text
		if ((inputText != null) && !inputText.isDisposed()) {
			inputText.setFocus();
			inputText.selectAll();
		}
	}

}
