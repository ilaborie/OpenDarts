package org.opendarts.prototype.ui.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.opendarts.prototype.model.IGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class Game501Editor.
 */
public class Game501Editor extends FormEditor implements IEditorPart {

	/** The ID. */
	public static String ID = "opendarts.editor.501";

	/** The logger. */
	private static final Logger LOG = LoggerFactory.getLogger(Game501Editor.class);

	/** The game input. */
	private GameEditorInput gameInput;

	/**
	 * Instantiates a new game editor.
	 */
	public Game501Editor() {
		super();
	}

	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	public IGame getGame() {
		return this.gameInput.getGame();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#setInput(org.eclipse.ui.IEditorInput)
	 */
	@Override
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		this.gameInput = (GameEditorInput) input;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormEditor#addPages()
	 */
	@Override
	protected void addPages() {
		try {
			this.addPage(new Game501Page(this));
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
