package org.opendarts.ui.x01.defi.editor;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.opendarts.core.model.dart.InvalidDartThrowException;
import org.opendarts.core.model.dart.impl.SkipedDartsThrow;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.service.game.IGameService;
import org.opendarts.core.x01.defi.model.GameX01Defi;
import org.opendarts.ui.x01.utils.TextInputListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handle input text.
 */
public class TextDefiInputListener extends TextInputListener implements
		FocusListener, SelectionListener, KeyListener {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(TextDefiInputListener.class);

	/** The game. */
	private final GameX01Defi game;

	/** The player. */
	private final IPlayer player;

	/** The game service. */
	private final IGameService gameService;

	/** The input text. */
	private final Text inputText;

	/**
	 * Instantiates a new text input listener.
	 *
	 * @param parentShell the parent shell
	 * @param game the game
	 * @param player the player
	 * @param dec 
	 */
	public TextDefiInputListener(Shell parentShell, Text inputText,
			GameX01Defi game, IPlayer player, ControlDecoration dec) {
		super(parentShell, inputText, game, player, dec);
		this.game = game;
		this.player = player;
		this.inputText = inputText;
		this.gameService = game.getParentSet().getGameService();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.KeyListener#keyPressed(org.eclipse.swt.events.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if ("-".equals(inputText.getText())) {
			this.handleSkipScore();
		} else {
			super.keyPressed(e);
		}
	}

	/**
	 * Handle skip score.
	 */
	private void handleSkipScore() {
		try {
			this.gameService.addPlayerThrow(this.game, this.player,
					new SkipedDartsThrow());
		} catch (InvalidDartThrowException e) {
			LOG.warn("WTF !", e);
		}
	}
}
