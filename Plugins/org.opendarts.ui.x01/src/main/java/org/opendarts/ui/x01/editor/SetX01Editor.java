package org.opendarts.ui.x01.editor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.model.session.ISetListener;
import org.opendarts.core.model.session.SetEvent;
import org.opendarts.core.service.game.IGameService;
import org.opendarts.core.service.session.ISetService;
import org.opendarts.core.x01.model.GameX01;
import org.opendarts.ui.editor.ISetEditor;
import org.opendarts.ui.editor.SetEditorInput;
import org.opendarts.ui.x01.X01UiPlugin;
import org.opendarts.ui.x01.dialog.SetX01InfoDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class SetX01Editor.
 */
public class SetX01Editor extends FormEditor implements ISetEditor,
		ISetListener, ISaveablePart2 {

	/** The ID. */
	public static final String ID = "opendarts.editor.x01";

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(SetX01Editor.class);

	/** The game input. */
	private SetEditorInput setInput;

	/** all game pages. */
	private final Map<GameX01, GameX01Page> pages;

	/** The game service. */
	private IGameService gameService;

	/** The dirty. */
	private boolean dirty;

	/** The set service. */
	private final ISetService setService;

	/**
	 * Instantiates a new game editor.
	 */
	public SetX01Editor() {
		super();
		this.pages = new HashMap<GameX01, GameX01Page>();
		this.setService = X01UiPlugin.getService(ISetService.class);
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
		this.setPartName(set.toString());
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
	 * Close.
	 *
	 * @param save the save
	 */
	@Override
	public void close(boolean save) {
		this.getSet().removeListener(this);
		super.close(save);
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
		this.gameService = this.getSet().getGameService();
		IGame game = this.getSet().getCurrentGame();
		if ((game != null) && !game.isFinished()) {
			this.handleGameActive((GameX01) game);
		}
		this.dirty = true;
	}

	/**
	 * Handle new game.
	 *
	 * @param game the game
	 */
	private void handleGameActive(GameX01 game) {
		LOG.info("Starting new game: {}", game);
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
				this.setPartName(this.getSet().toString());
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
		this.dirty = false;
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
		ISet set = this.getSet();
		if ((set != null) && !set.isFinished()) {
			this.setService.cancelSet(set);
		}
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

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormEditor#isDirty()
	 */
	@Override
	public boolean isDirty() {
		return this.dirty;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.ISaveablePart2#promptToSaveOnClose()
	 */
	@Override
	public int promptToSaveOnClose() {
		return ISaveablePart2.YES;
	}
}
