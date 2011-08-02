package org.opendarts.ui.stats.content;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISessionListener;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.model.session.ISetListener;
import org.opendarts.core.model.session.SessionEvent;
import org.opendarts.core.model.session.SetEvent;
import org.opendarts.core.service.session.ISessionService;
import org.opendarts.ui.OpenDartsUiPlugin;

/**
 * The Class StatsTreeContentProvider.
 */
public class StatsTreeContentProvider implements ITreeContentProvider,
		ISetListener, ISessionListener {

	/** The viewer. */
	private TreeViewer viewer;

	/**
	 * Instantiates a new stats tree content provider.
	 * @param viewer 
	 */
	public StatsTreeContentProvider(TreeViewer viewer) {
		super();
		this.viewer = viewer;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		ISessionService sessionService = (ISessionService) inputElement;
		return new ISession[] { sessionService.getSession() };
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		List<Object> list = new ArrayList<Object>();
		if (parentElement instanceof ISession) {
			ISession session = (ISession) parentElement;
			session.addListener(this);
			// Add Set
			for (ISet set : session.getAllGame()) {
				set.addListener(this);
				list.add(set);
			}
		} else if (parentElement instanceof ISet) {
			ISet set = (ISet) parentElement;
			// Add game
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
			result = ((ISet) element).getParentSession();
		} else if (element instanceof IGame) {
			result = ((IGame) element).getParentSet();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	@Override
	public boolean hasChildren(Object element) {
		return this.getChildren(element).length > 0;
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
	
	/* (non-Javadoc)
	 * @see org.opendarts.core.model.session.ISessionListener#notifySessionEvent(org.opendarts.core.model.session.SessionEvent)
	 */
	@Override
	public void notifySessionEvent(SessionEvent event) {
		switch (event.getType()) {
			case SESSION_CANCELED:
			case SESSION_FINISHED:
			case SESSION_INITIALIZED:
				this.viewer.refresh(event.getSession());
				break;
			case NEW_CURRENT_SET:
				this.viewer.add(event.getSession(),event.getSet());
				break;
			default:
				break;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.opendarts.core.model.session.ISetListener#notifySetEvent(org.opendarts.core.model.session.SetEvent)
	 */
	@Override
	public void notifySetEvent(SetEvent event) {
		switch (event.getType()) {
			case SET_CANCELED:
			case SET_FINISHED:
			case SET_INITIALIZED:
				this.viewer.refresh(event.getSet());
				break;
			case NEW_CURRENT_GAME:
				this.viewer.add(event.getSet(), event.getGame());
				break;
			default:
				break;
		}
	}
}
