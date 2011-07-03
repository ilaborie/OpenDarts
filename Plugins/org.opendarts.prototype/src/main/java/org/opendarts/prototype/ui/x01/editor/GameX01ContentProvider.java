package org.opendarts.prototype.ui.x01.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.opendarts.prototype.internal.model.game.x01.DummyX01Entry;
import org.opendarts.prototype.internal.model.game.x01.GameX01;
import org.opendarts.prototype.model.game.IGameEntry;

/**
 * The Class GameX01ContentProvider.
 */
public class GameX01ContentProvider implements IStructuredContentProvider {

	/**
	 * Instantiates a new game x01 content provider.
	 */
	public GameX01ContentProvider() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof GameX01) {
			GameX01 game = (GameX01) inputElement;
			List<IGameEntry> entries = new ArrayList<IGameEntry>();
			entries.add(new DummyX01Entry(game));
			entries.addAll(game.getGameEntries());
			return entries.toArray();
		}
		return new Object[0];
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {
		// Nothing to dispose

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// Nothing to do
	}

}
