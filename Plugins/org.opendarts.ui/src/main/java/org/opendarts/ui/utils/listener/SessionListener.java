package org.opendarts.ui.utils.listener;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.opendarts.core.model.game.IGameDefinition;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISessionListener;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.model.session.SessionEvent;
import org.opendarts.ui.OpenDartsUiPlugin;
import org.opendarts.ui.editor.SetEditorInput;
import org.opendarts.ui.service.IGameUiProvider;
import org.opendarts.ui.service.IGameUiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Session listener
 */
public class SessionListener implements ISessionListener {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(SessionListener.class);

	/** The game ui provider. */
	private final IGameUiProvider gameUiProvider;

	/**
	 * Instantiates a new session listener.
	 *
	 * @param page the page
	 * @param editorId the editor id
	 */
	public SessionListener() {
		super();
		this.gameUiProvider = OpenDartsUiPlugin
				.getService(IGameUiProvider.class);
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

	/* (non-Javadoc)
	 * @see org.opendarts.core.model.session.ISessionListener#sessionCreated(org.opendarts.core.model.session.ISession)
	 */
	@Override
	public void sessionCreated(ISession session) {
		session.addListener(this);
	}

	/**
	 * Open Game editor.
	 *
	 * @param set the set
	 */
	public void openEditor(ISet set) {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window != null) {
			try {
				window.getWorkbench().showPerspective(
						"opendarts.perspective.main", window);
			} catch (WorkbenchException e1) {
				LOG.error("Could not switch to main perspective", e1);
			}

			IWorkbenchPage page = window.getActivePage();
			SetEditorInput input = new SetEditorInput(set);
			try {
				IGameDefinition definition = set.getGameDefinition();
				IGameUiService gameUiService = this.gameUiProvider
						.getGameUiService(definition);
				String editorId = gameUiService.getGameEditor(definition);
				page.openEditor(input, editorId);
			} catch (PartInitException e) {
				LOG.error("Could not open editor", e);
			}
		}
	}

}
