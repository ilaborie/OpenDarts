package org.opendarts.prototype.ui.x01.dialog;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.Form;
import org.opendarts.prototype.ui.utils.OpenDartsFormsToolkit;
import org.opendarts.prototype.ui.utils.shortcut.IShortcut;
import org.opendarts.prototype.ui.x01.utils.shortcut.ValueShortcut;
import org.opendarts.prototype.ui.x01.utils.shortcut.X01Shortcuts;

/**
 * The Class ShortcutsTooltip.
 */
public class ShortcutsTooltip extends ToolTip {

	/**
	 * Instantiates a new shortcuts tooltip.
	 *
	 * @param control the control
	 */
	public ShortcutsTooltip(Control control) {
		super(control);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.ToolTip#createToolTipContentArea(org.eclipse.swt.widgets.Event, org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Composite createToolTipContentArea(Event event, Composite parent) {
		OpenDartsFormsToolkit toolkit = OpenDartsFormsToolkit.getToolkit();

		// create the base form
		Form form = toolkit.createForm(parent);
		form.setText("Shortcuts");

		toolkit.decorateFormHeading(form);
		Composite body = form.getBody();
		GridLayoutFactory.fillDefaults().margins(2, 2).numColumns(4)
				.applyTo(body);

		// shortcuts
		GridDataFactory keyData = GridDataFactory.fillDefaults()
				.grab(true, false).indent(5, SWT.DEFAULT)
				.align(SWT.END, SWT.CENTER);
		GridDataFactory lblData = GridDataFactory.fillDefaults();
		Label lbl;
		for (IShortcut shortcut : X01Shortcuts.getX01Shortcuts().getShortcuts()) {
			// Key
			lbl = toolkit.createLabel(body, shortcut.getKeyLabel());
			lbl.setFont(OpenDartsFormsToolkit
					.getFont(OpenDartsFormsToolkit.FONT_BOLD));
			keyData.copy().applyTo(lbl);

			// label
			lbl = toolkit.createLabel(body, shortcut.getLabel());
			if (shortcut instanceof ValueShortcut) {
				lblData.copy().applyTo(lbl);
			} else {
				lblData.copy().span(3, 1).applyTo(lbl);
			}
		}

		return parent;
	}
}
