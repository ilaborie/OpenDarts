package org.opendarts.ui.utils.listener;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISessionListener;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.model.session.SessionEvent;
import org.opendarts.ui.editor.SetEditorInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Session listener
 */
public class SessionListener implements ISessionListener {
	
	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(SessionListener.class);
	
	/** The page. */
	private final IWorkbenchPage page;
	
	/** The editor id. */
	private final String editorId;

	/**
	 * Instantiates a new session listener.
	 *
	 * @param page the page
	 * @param editorId the editor id
	 */
	public SessionListener(IWorkbenchPage page, String editorId) {
		super();
		this.page = page;
		this.editorId = editorId;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.model.session.ISessionListener#notifySessionEvent(org.opendarts.core.model.session.SessionEvent)
	 */
	@Override
	public void notifySessionEvent(SessionEvent event) {
		ISession session = event.getSession();
		switch (event.getType()) {
			case NEW_CURRENT_SET:
				ISet set = event.getSet();
				this.openEditor(set);
				break;
			case SESSION_CANCELED:
			case SESSION_FINISHED:
				session.removeListener(this);
				break;
			case SESSION_INITIALIZED:
			default:
				break;
		}
	}

	/**
	 * Open Game editor.
	 *
	 * @param set the set
	 */
	public void openEditor( ISet set) {
		SetEditorInput input = new SetEditorInput(set);
		try {
			this.page.openEditor(input, this.editorId);
//			this.setService.startSet(set);
		} catch (PartInitException e) {
			LOG.error("Could not open editor", e);
		}
	}

}
