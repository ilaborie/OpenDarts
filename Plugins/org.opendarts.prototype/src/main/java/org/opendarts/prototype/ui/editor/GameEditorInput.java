package org.opendarts.prototype.ui.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.opendarts.prototype.model.game.IGame;

/**
 * The Class GameEditorInput.
 */
public class GameEditorInput implements IEditorInput {

	/** The game. */
	private final IGame game;

	/**
	 * Instantiates a new game editor input.
	 *
	 * @param game the game
	 */
	public GameEditorInput(IGame game) {
		super();
		this.game = game;
	}

	/**
	 * Gets the adapter.
	 *
	 * @param adapter the adapter
	 * @return the adapter
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		Object result = null;
		if (IGame.class.isAssignableFrom(adapter)) {
			result = this.game;
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#exists()
	 */
	@Override
	public boolean exists() {
		return !this.game.isFinished();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#getName()
	 */
	@Override
	public String getName() {
		return this.game.getName();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#getPersistable()
	 */
	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 */
	@Override
	public String getToolTipText() {
		return this.game.getDescription();
	}

	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	public IGame getGame() {
		return this.game;
	}

}

