package org.opendarts.prototype.ui.handler;

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
import org.opendarts.prototype.ProtoPlugin;
import org.opendarts.prototype.model.game.IGameDefinition;
import org.opendarts.prototype.model.session.ISession;
import org.opendarts.prototype.model.session.ISet;
import org.opendarts.prototype.service.session.ISessionService;
import org.opendarts.prototype.service.session.ISetService;
import org.opendarts.prototype.ui.dialog.NewSetDialog;
import org.opendarts.prototype.ui.editor.SetEditorInput;
import org.opendarts.prototype.ui.x01.editor.SetX01Editor;
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
		ISessionService sessionService = ProtoPlugin
				.getService(ISessionService.class);
		ISession session = sessionService.getSession();

		// Dialog
		NewSetDialog dialog = new NewSetDialog(
				HandlerUtil.getActiveShell(event));
		if (dialog.open() == Window.OK) {
			IGameDefinition gameDef = dialog.getGameDefinition();

			// Set
			ISetService setService = ProtoPlugin.getService(ISetService.class);
			ISet set = setService.createNewSet(session, gameDef);

			// Open Editor
			this.openEditor(page, set);
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
	private IEditorPart openEditor(IWorkbenchPage page, ISet set) {
		IEditorPart openEditor = null;
		SetEditorInput input = new SetEditorInput(set);
		try {
			openEditor = page.openEditor(input, SetX01Editor.ID);
		} catch (PartInitException e) {
			LOG.error("Could not open editor", e);
		}
		return openEditor;
	}

}