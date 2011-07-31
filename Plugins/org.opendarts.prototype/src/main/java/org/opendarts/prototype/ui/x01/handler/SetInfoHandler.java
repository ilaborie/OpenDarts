package org.opendarts.prototype.ui.x01.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.handlers.HandlerUtil;
import org.opendarts.prototype.model.session.ISet;
import org.opendarts.prototype.ui.editor.SetEditorInput;
import org.opendarts.prototype.ui.x01.dialog.SetX01FinishSummaryDialog;

/**
 * The Class SetInfoHandler.
 */
public class SetInfoHandler extends AbstractHandler implements IHandler {

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorInput editorInput = HandlerUtil.getActiveEditorInput(event);
		if (editorInput != null) {
			if (editorInput instanceof SetEditorInput) {
				SetEditorInput setEditorInput = (SetEditorInput) editorInput;
				ISet set = setEditorInput.getSet();
				SetX01FinishSummaryDialog dialog = new SetX01FinishSummaryDialog(
						HandlerUtil.getActiveShell(event), set);
				dialog.open();
			}
		}
		return null;
	}
}
