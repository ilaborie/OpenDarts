package org.opendarts.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.opendarts.core.model.game.IGameDefinition;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.service.session.ISessionService;
import org.opendarts.core.service.session.ISetService;
import org.opendarts.ui.OpenDartsUiPlugin;
import org.opendarts.ui.dialog.NewSetDialog;
import org.opendarts.ui.editor.SetEditorInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class NewGameHandler.
 */
public class NewSetHandler extends AbstractHandler implements IHandler {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(NewSetHandler.class);

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		LOG.info("New Set...");
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();

		// The session
		ISessionService sessionService = OpenDartsUiPlugin
				.getService(ISessionService.class);
		ISession session = sessionService.getSession();

		// Dialog
		NewSetDialog dialog = new NewSetDialog(
				HandlerUtil.getActiveShell(event));
		if (dialog.open() == Window.OK) {
			IGameDefinition gameDef = dialog.getGameDefinition();
			String editorId = dialog.getEditorId();

			// Set
			ISetService setService = OpenDartsUiPlugin
					.getService(ISetService.class);
			ISet set = setService.createNewSet(session, gameDef);

			// Open Editor
			this.openEditor(page, set, editorId);
			setService.startSet(set);
		} else {
			LOG.info("Cancel NewGame creation");
		}
		return null;
	}

	/**
	 * Open Game editor.
	 *
	 * @param page the page
	 * @param game the game
	 * @return 
	 */
	private IEditorPart openEditor(IWorkbenchPage page, ISet set,
			String editorId) {
		IEditorPart openEditor = null;
		SetEditorInput input = new SetEditorInput(set);
		try {
			openEditor = page.openEditor(input, editorId);
		} catch (PartInitException e) {
			LOG.error("Could not open editor", e);
		}
		return openEditor;
	}

}
