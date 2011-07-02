package org.opendarts.prototype.ui.x01.utils;

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

	/**
	 * Instantiates a new text input listener.
	 *
	 * @param parentShell the parent shell
	 * @param game the game
	 * @param player the player
	 */
	public TextInputListener(Shell parentShell, Text inputText, GameX01 game,
			IPlayer player) {
		super();
		this.game = game;
		this.player = player;
		this.inputText = inputText;
		this.dartThrowUtil = new DartThrowUtil(parentShell, game, player);
		this.gameService = ProtoPlugin.getService(IGameService.class);
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
				e.doit = false;
				this.handleNewValue(this.inputText.getText());
				break;
			default:
				break;
		}
	}

	/**
	 * Handle new value.
	 *
	 * @param value the value
	 */
	private void handleNewValue(String value) {
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
			} catch (Exception e) {
				LOG.warn("Invalid format", e);
				this.inputText.setFocus();
			}
		} else {
			this.inputText.setFocus();
		}
	}

}
