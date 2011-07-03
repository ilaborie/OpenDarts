package org.opendarts.prototype.ui.x01.utils;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.opendarts.prototype.ProtoPlugin;
import org.opendarts.prototype.internal.model.dart.ThreeDartThrow;
import org.opendarts.prototype.internal.model.game.x01.GameX01;
import org.opendarts.prototype.internal.model.game.x01.WinningX01DartsThrow;
import org.opendarts.prototype.model.dart.InvalidDartThrowException;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.service.game.IGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handle input text.
 */
public class TextInputListener implements FocusListener, SelectionListener,
		KeyListener {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(TextInputListener.class);

	/** The game. */
	private final GameX01 game;

	/** The player. */
	private final IPlayer player;

	/** The game service. */
	private IGameService gameService;

	/** The dart throw util. */
	private final DartThrowUtil dartThrowUtil;

	/** The input text. */
	private final Text inputText;

	/** The decoration. */
	private final ControlDecoration decoration;

	/** The shortcut value. */
	private final Map<Integer, String> shortcutValue;

	/** The shortcut value. */
	private final Map<Integer, String> shiftShortcutValue;

	/**
	 * Instantiates a new text input listener.
	 *
	 * @param parentShell the parent shell
	 * @param game the game
	 * @param player the player
	 * @param dec 
	 */
	public TextInputListener(Shell parentShell, Text inputText, GameX01 game,
			IPlayer player, ControlDecoration dec) {
		super();
		this.game = game;
		this.player = player;
		this.inputText = inputText;
		this.decoration = dec;
		this.dartThrowUtil = new DartThrowUtil(parentShell, game, player);
		this.gameService = ProtoPlugin.getService(IGameService.class);
		this.shortcutValue = new HashMap<Integer, String>();
		this.shiftShortcutValue = new HashMap<Integer, String>();
		this.initShortCut();
	}

	/**
	 * Inits the short cut.
	 */
	private void initShortCut() {
		this.shortcutValue.put(SWT.F1, "0");
		this.shortcutValue.put(SWT.F2, "26");
		this.shortcutValue.put(SWT.F3, "41");
		this.shortcutValue.put(SWT.F4, "45");
		this.shortcutValue.put(SWT.F5, "60");
		this.shortcutValue.put(SWT.F6, "81");
		this.shortcutValue.put(SWT.F7, "85");
		this.shortcutValue.put(SWT.F8, "100");

		this.shiftShortcutValue.put(SWT.F1, "43");
		this.shiftShortcutValue.put(SWT.F2, "55");
		this.shiftShortcutValue.put(SWT.F3, "83");
		this.shiftShortcutValue.put(SWT.F4, "95");
		this.shiftShortcutValue.put(SWT.F5, "121");
		this.shiftShortcutValue.put(SWT.F6, "125");
		this.shiftShortcutValue.put(SWT.F7, "140");
		this.shiftShortcutValue.put(SWT.F8, "180");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.FocusListener#focusGained(org.eclipse.swt.events.FocusEvent)
	 */
	@Override
	public void focusGained(FocusEvent e) {
		// Nothing to do
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.FocusListener#focusLost(org.eclipse.swt.events.FocusEvent)
	 */
	@Override
	public void focusLost(FocusEvent e) {
		this.handleNewValue(this.inputText.getText());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// Nothing to do
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		this.handleNewValue(this.inputText.getText());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.KeyListener#keyPressed(org.eclipse.swt.events.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// Nothing to do
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.KeyListener#keyReleased(org.eclipse.swt.events.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Handle shortcuts (F1 ....)
		switch (e.keyCode) {
			case '\t':
			case '\r':
			case '\n':
			case ' ':
				this.handleNewValue(this.inputText.getText());
				break;
			case SWT.F1:
			case SWT.F2:
			case SWT.F3:
			case SWT.F4:
			case SWT.F5:
			case SWT.F6:
			case SWT.F7:
			case SWT.F8:
				if (e.stateMask == SWT.SHIFT) {
					this.handleNewValue(shiftShortcutValue.get(e.keyCode));
				} else {
					this.handleNewValue(shortcutValue.get(e.keyCode));
				}
				break;
			case SWT.F9:
				this.handleLeftValue(this.inputText.getText());
				break;
			case SWT.F10:
				this.handleWinning(1);
				break;
			case SWT.F11:
				this.handleWinning(2);
				break;
			case SWT.F12:
				this.handleWinning(3);
				break;
			default:
				break;
		}
	}

	/**
	 * Handle left value.
	 *
	 * @param leftValue the left value
	 */
	private void handleLeftValue(String leftValue) {
		this.decoration.hide();
		try {
			Integer score = Integer.parseInt(leftValue);
			Integer leftScore = this.game.getScore(player);
			String value = String.valueOf(leftScore - score);
			this.handleNewValue(value);
		} catch (NumberFormatException e) {
			LOG.warn("Invalid format: " + e);
			String msg = "Not a number!";
			this.applyError(msg);
		}
	}

	/**
	 * Handle winning.
	 *
	 * @param nbDart the nb dart
	 */
	private void handleWinning(int nbDart) {
		this.decoration.hide();
		try {
			Integer leftScore = this.game.getScore(player);
			WinningX01DartsThrow dartThrow = new WinningX01DartsThrow(
					leftScore, nbDart);
			this.gameService.addWinningPlayerThrow(this.game, this.player,
					dartThrow);
		} catch (InvalidDartThrowException e) {
			String msg = e.getMessage();
			this.applyError(msg);
		}
	}

	/**
	 * Handle new value.
	 *
	 * @param value the value
	 */
	private void handleNewValue(String value) {
		this.decoration.hide();
		if (!"".equals(value)) {
			Integer leftScore = this.game.getScore(player);
			ThreeDartThrow dartThrow;
			try {
				dartThrow = this.dartThrowUtil.getDartThrow((String) value,
						leftScore);
				if (dartThrow instanceof WinningX01DartsThrow) {
					this.gameService.addWinningPlayerThrow(this.game,
							this.player, dartThrow);
				} else {
					this.gameService.addPlayerThrow(this.game, this.player,
							dartThrow);
				}
			} catch (NumberFormatException e) {
				LOG.warn("Invalid format: " + e);
				String msg = "Not a number!";
				this.applyError(msg);
			} catch (InvalidDartThrowException e) {
				String msg = e.getMessage();
				this.applyError(msg);
			}
		} else {
			this.inputText.setFocus();
		}
	}

	/**
	 * Apply error.
	 *
	 * @param msg the message
	 */
	private void applyError(String msg) {
		// Decoration
		FieldDecoration errorFieldIndicator = FieldDecorationRegistry
				.getDefault().getFieldDecoration(
						FieldDecorationRegistry.DEC_ERROR);
		this.decoration.setImage(errorFieldIndicator.getImage());
		this.decoration.setDescriptionText(msg);
		this.decoration.setImage(errorFieldIndicator.getImage());
		this.decoration.show();

		// handler input text
		this.inputText.setFocus();
		this.inputText.selectAll();
	}
}
