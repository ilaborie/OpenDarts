package org.opendarts.ui.stats.content;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.service.session.ISessionService;
import org.opendarts.ui.OpenDartsUiPlugin;

/**
 * The Class StatsTreeContentProvider.
 */
public class StatsTreeContentProvider implements ITreeContentProvider{

	/**
	 * Instantiates a new stats tree content provider.
	 */
	public StatsTreeContentProvider() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		ISessionService sessionService = (ISessionService) inputElement;
		return new ISession[] {sessionService.getSession()};
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		List<Object> list = new ArrayList<Object>() ;
		if (parentElement instanceof ISession) {
			ISession session = (ISession) parentElement;
			// Add Set
			list.addAll(session.getAllGame());
		} else if (parentElement instanceof ISet) {
			ISet set = (ISet) parentElement;
			list.addAll(set.getAllGame());
		}
		
		return list.toArray();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	@Override
	public Object getParent(Object element) {
		Object result = null;
		if (element instanceof ISession) {
			result = OpenDartsUiPlugin.getService(ISessionService.class);
		} else if (element instanceof ISet) {
			result = ((ISet)element).getParentSession();
		} else if (element instanceof IGame) {
			result = ((IGame)element).getParentSet();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	@Override
	public boolean hasChildren(Object element) {
		return this.getChildren(element).length>0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// Nothing to do
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {
		// Nothing to dispose
	}

}
