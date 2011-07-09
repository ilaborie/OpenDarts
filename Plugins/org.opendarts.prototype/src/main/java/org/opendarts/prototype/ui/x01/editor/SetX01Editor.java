package org.opendarts.prototype.ui.x01.editor;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.opendarts.prototype.ProtoPlugin;
import org.opendarts.prototype.internal.model.game.x01.GameX01;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.session.ISet;
import org.opendarts.prototype.model.session.ISetListener;
import org.opendarts.prototype.model.session.SetEvent;
import org.opendarts.prototype.service.game.IGameService;
import org.opendarts.prototype.ui.editor.ISetEditor;
import org.opendarts.prototype.ui.editor.SetEditorInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class SetX01Editor.
 */
public class SetX01Editor extends FormEditor implements ISetEditor,
		ISetListener {

	/** The ID. */
	public static String ID = "opendarts.editor.x01";

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(SetX01Editor.class);

	/** The game input. */
	private SetEditorInput setInput;

	/** all game pages. */
	private final Map<GameX01, GameX01Page> pages;

	/** The game service. */
	private final IGameService gameService;

	/**
	 * Instantiates a new game editor.
	 */
	public SetX01Editor() {
		super();
		this.pages = new HashMap<GameX01, GameX01Page>();
		this.gameService = ProtoPlugin.getService(IGameService.class);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.ui.editor.IGameEditor#getGameInput()
	 */
	@Override
	public SetEditorInput getSetInput() {
		return this.setInput;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#setInput(org.eclipse.ui.IEditorInput)
	 */
	@Override
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		this.setInput = (SetEditorInput) input;
		ISet set = this.getSet();

		// Register listener
		set.addListener(this);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.ui.editor.ISetEditor#getSet()
	 */
	@Override
	public ISet getSet() {
		return this.setInput.getSet();
	}

	/**
	 * Notify set event.
	 *
	 * @param event the event
	 */
	@Override
	public void notifySetEvent(SetEvent event) {
		if (event.getSet().equals(this.getSet())) {
			LOG.trace("New Set Event: {}", event);
			switch (event.getType()) {
				case SET_INITIALIZED:
					this.handleSetInitialize();
					break;
				case NEW_CURRENT_GAME:
					this.handleGameActive((GameX01) event.getGame());
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
	private void handleSetInitialize() {
		IGame game = this.getSet().getCurrentGame();
		if (game != null && !game.isFinished()) {
			this.handleGameActive((GameX01) game);
		}
	}

	/**
	 * Handle new game.
	 *
	 * @param game the game
	 */
	private void handleGameActive(GameX01 game) {
		// get new page
		GameX01Page page;
		page = this.pages.get(game);
		if (page == null) {
			int nb = this.pages.size() + 1;
			page = new GameX01Page(this, game, nb);
			this.pages.put(game, page);
			try {
				this.addPage(page);
				this.setActivePage(String.valueOf(nb));
			} catch (PartInitException e) {
				LOG.error("Could not add page", e);
			}
		}
		// auto start Game
		this.gameService.startGame(game);
	}

	/**
	 * Handle set finished.
	 */
	private void handleSetFinished() {
		// End Game dialog
		String title = MessageFormat.format("{0} finished", this.getSet());
		String message = this.getSet().getWinningMessage();
		Shell shell = this.getSite().getShell();
		MessageDialog.open(MessageDialog.INFORMATION, shell, title, message,
				SWT.SHEET);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormEditor#addPages()
	 */
	@Override
	protected void addPages() {
		try {
			List<IGame> games = this.getSet().getAllGame();
			GameX01 game;
			GameX01Page page;
			for (int i = 0; i < games.size(); i++) {
				game = (GameX01) games.get(i);
				page = new GameX01Page(this, game, i + 1);
				this.pages.put(game, page);
				this.addPage(page);
			}
		} catch (PartInitException e) {
			LOG.error("Could not create editor", e);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		// Save is disable
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		// Save is disable
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

}
