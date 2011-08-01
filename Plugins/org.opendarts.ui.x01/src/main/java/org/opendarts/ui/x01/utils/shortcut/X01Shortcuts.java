package org.opendarts.ui.x01.utils.shortcut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.ui.utils.shortcut.IShortcut;

/**
 * The Class X01Shortcuts.
 */
public class X01Shortcuts {

	/** The instance. */
	private final static X01Shortcuts instance = new X01Shortcuts();

	/**
	 * Gets the shortcuts.
	 *
	 * @return the shortcuts
	 */
	public static X01Shortcuts getX01Shortcuts() {
		return instance;
	}

	/** The shortcuts. */
	private final List<IShortcut> shortcuts;

	/**
	 * Instantiates a new x01 shortcuts.
	 */
	private X01Shortcuts() {
		super();
		this.shortcuts = new ArrayList<IShortcut>();

		// Values
		this.shortcuts.add(new ValueShortcut(SWT.F1, SWT.NONE, 0));
		this.shortcuts.add(new ValueShortcut(SWT.F1, SWT.SHIFT, 43));

		this.shortcuts.add(new ValueShortcut(SWT.F2, SWT.NONE, 26));
		this.shortcuts.add(new ValueShortcut(SWT.F2, SWT.SHIFT, 55));

		this.shortcuts.add(new ValueShortcut(SWT.F3, SWT.NONE, 41));
		this.shortcuts.add(new ValueShortcut(SWT.F3, SWT.SHIFT, 83));

		this.shortcuts.add(new ValueShortcut(SWT.F4, SWT.NONE, 45));
		this.shortcuts.add(new ValueShortcut(SWT.F4, SWT.SHIFT, 95));

		this.shortcuts.add(new ValueShortcut(SWT.F5, SWT.NONE, 60));
		this.shortcuts.add(new ValueShortcut(SWT.F5, SWT.SHIFT, 121));

		this.shortcuts.add(new ValueShortcut(SWT.F6, SWT.NONE, 81));
		this.shortcuts.add(new ValueShortcut(SWT.F6, SWT.SHIFT, 125));

		this.shortcuts.add(new ValueShortcut(SWT.F7, SWT.NONE, 85));
		this.shortcuts.add(new ValueShortcut(SWT.F7, SWT.SHIFT, 140));

		this.shortcuts.add(new ValueShortcut(SWT.F8, SWT.NONE, 100));
		this.shortcuts.add(new ValueShortcut(SWT.F8, SWT.SHIFT, 180));

		// Left
		this.shortcuts.add(new LeftValueShortcut(SWT.F9, SWT.NONE));

		// Finish
		this.shortcuts.add(new FinishShortcut(SWT.F10, SWT.NONE, 1));
		this.shortcuts.add(new FinishShortcut(SWT.F11, SWT.NONE, 2));
		this.shortcuts.add(new FinishShortcut(SWT.F12, SWT.NONE, 3));
	}

	/**
	 * Handle key event.
	 *
	 * @param event the event
	 */
	public void handleKeyEvent(KeyEvent event, Shell parentShell,
			Text inputText, IGame game, IPlayer player, ControlDecoration dec) {
		int keyCode = event.keyCode;
		int stateMask = event.stateMask;
		for (IShortcut shortcut : this.shortcuts) {
			if (shortcut.apply(keyCode, stateMask)) {
				shortcut.execute(parentShell, inputText, game, player, dec);
				break;
			}
		}
	}

	/**
	 * Gets the shortcuts.
	 *
	 * @return the shortcuts
	 */
	public List<IShortcut> getShortcuts() {
		return Collections.unmodifiableList(this.shortcuts);
	}
}
