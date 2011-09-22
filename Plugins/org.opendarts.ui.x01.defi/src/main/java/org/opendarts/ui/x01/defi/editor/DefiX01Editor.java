/*
 * 
 */
package org.opendarts.ui.x01.defi.editor;

import java.util.List;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.session.SetEvent;
import org.opendarts.core.x01.defi.model.GameX01Defi;
import org.opendarts.ui.x01.dialog.SetX01InfoDialog;
import org.opendarts.ui.x01.editor.SetX01Editor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefiX01Editor extends SetX01Editor {
	
	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(DefiX01Editor.class);
	
	
	/** The ID. */
	public static final String ID = "opendarts.editor.defi.x01";
	
	/** The page. */
	private GameX01DefiPage page;

	/**
	 * Instantiates a new defi x01 editor.
	 */
	public DefiX01Editor() {
		super();
	}
	
	public void notifySetEvent(SetEvent event) {
		if (event.getSet().equals(this.getSet())) {
			LOG.trace("New Set Event: {}", event);
			switch (event.getType()) {
				case SET_INITIALIZED:
					this.handleSetInitialize();
					break;
				case NEW_CURRENT_GAME:
					this.handleGameActive((GameX01Defi) event.getGame());
					break;
				case SET_FINISHED:
					this.handleSetFinished();
					break;
				default:
					break;
			}
		}
	}

	/**
	* Start.
	*/
	protected void handleSetInitialize() {
		IGame game = this.getSet().getCurrentGame();
		if ((game != null) && !game.isFinished()) {
			this.handleGameActive((GameX01Defi) game);
		}
		this.setDirty(true);
	}

	/**
	 * Handle new game.
	 *
	 * @param game the game
	 */
	protected void handleGameActive(GameX01Defi game) {
		LOG.info("Starting new game: {}", game);
		// get new page
		if ((this.page == null)) {
			this.page = new GameX01DefiPage(this, (GameX01Defi) game, 0);
			try {
				this.addPage(page);
				this.setActivePage(0);
				this.setPartName(this.getSet().toString());
			} catch (PartInitException e) {
				LOG.error("Could not add page", e);
			}
		}
		// auto start Game
		this.getGameService().startGame(game);
	}
	

	/**
	 * Handle game reinitialized.
	 *
	 * @param gameX01Page the game x01 page
	 * @param game the game
	 */
	protected void handleGameReinitialized(GameX01DefiPage gameX01Page, GameX01Defi game) {
		this.removePage(0);
		// get the page
		page = new GameX01DefiPage(this, game, 0);
		try {
			this.addPage(page);
			this.setActivePage(0);
			this.setPartName(this.getSet().toString());
		} catch (PartInitException e) {
			LOG.error("Could not add page", e);
		}
	}

	/**
	 * Handle set finished.
	 */
	protected void handleSetFinished() {
		this.setDirty(false) ;
		// End Game dialog
		SetX01InfoDialog dialog = new SetX01InfoDialog(this.getSite()
				.getShell(), this.getSet());
		dialog.open();
		this.firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormEditor#addPages()
	 */
	@Override
	protected void addPages() {
		try {
			List<IGame> games = this.getSet().getAllGame();
			GameX01Defi game = (GameX01Defi) games.get(0);
			this.page = new GameX01DefiPage(this, game, 0);
				this.addPage(page);
		} catch (PartInitException e) {
			LOG.error("Could not create editor", e);
		}
	}

}
