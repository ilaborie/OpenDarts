package org.opendarts.prototype.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.window.Window;
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
import org.opendarts.prototype.ui.dialog.NewGameDialog;
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
		NewGameDialog dialog = new NewGameDialog(
				HandlerUtil.getActiveShell(event));
		if (dialog.open() == Window.OK) {
			IGameDefinition gameDef = dialog.getGameDefinition();

			// Set
			ISetService setService = ProtoPlugin.getService(ISetService.class);
			ISet set = setService.createNewSet(session, gameDef);

			// start the set
			set.initSet();

			// Open Editor
			this.openEditor(page, set);
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
	 */
	private void openEditor(IWorkbenchPage page, ISet set) {
		SetEditorInput input = new SetEditorInput(set);
		try {
			page.openEditor(input, SetX01Editor.ID);
		} catch (PartInitException e) {
			LOG.error("Could not open editor", e);
		}
	}

}
