/*
 * 
 */
package org.opendarts.ui.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.session.ISet;

/**
 * The Class GameEditorInput.
 */
public class SetEditorInput implements IEditorInput {

	/** The set. */
	private final ISet set;

	/**
	 * Instantiates a new game editor input.
	 *
	 * @param game the game
	 */
	public SetEditorInput(ISet set) {
		super();
		this.set = set;
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
			result = this.set.getCurrentGame();
		} else if (ISet.class.isAssignableFrom(adapter)) {
			result = this.set;
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#exists()
	 */
	@Override
	public boolean exists() {
		return !this.set.isFinished();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#getName()
	 */
	@Override
	public String getName() {
		return this.set.getName();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#getPersistable()
	 */
	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 */
	@Override
	public String getToolTipText() {
		return this.set.getDescription();
	}

	/**
	 * Gets the sets the.
	 *
	 * @return the sets the
	 */
	public ISet getSet() {
		return this.set;
	}

}
