package org.opendarts.prototype.ui.editor.x01;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.opendarts.prototype.model.game.GameEvent;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.game.IGameListener;
import org.opendarts.prototype.ui.editor.GameEditorInput;
import org.opendarts.prototype.ui.editor.IGameEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class GameX01Editor.
 */
public class GameX01Editor extends FormEditor implements IGameEditor,
		IGameListener {

	/** The ID. */
	public static String ID = "opendarts.editor.x01";

	/** The logger. */
	private static final Logger LOG = LoggerFactory.getLogger(GameX01Editor.class);

	/** The game input. */
	private GameEditorInput gameInput;

	/** The page. */
	private GameX01Page page;

	/**
	 * Instantiates a new game editor.
	 */
	public GameX01Editor() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.ui.editor.IGameEditor#getGame()
	 */
	public IGame getGame() {
		return this.gameInput.getGame();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.ui.editor.IGameEditor#getGameInput()
	 */
	@Override
	public GameEditorInput getGameInput() {
		return this.gameInput;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#setInput(org.eclipse.ui.IEditorInput)
	 */
	@Override
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		if (this.gameInput != null) {
			this.getGame().removeListener(this);
		}
		this.gameInput = (GameEditorInput) input;
		this.getGame().addListener(this);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.game.IGameListener#notifyGameEvent(org.opendarts.prototype.model.game.GameEvent)
	 */
	@Override
	public void notifyGameEvent(GameEvent event) {
		if (event.getGame().equals(this.getGame())) {
			LOG.trace("New Game Event: {}", event);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormEditor#addPages()
	 */
	@Override
	protected void addPages() {
		try {
			this.page = new GameX01Page(this);
			this.addPage(this.page);
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

