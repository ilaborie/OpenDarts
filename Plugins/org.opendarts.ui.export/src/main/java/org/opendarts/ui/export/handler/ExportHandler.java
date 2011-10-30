package org.opendarts.ui.export.handler;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.progress.UIJob;
import org.opendarts.core.export.IExportOptions;
import org.opendarts.core.export.IExportService;
import org.opendarts.core.model.session.ISession;
import org.opendarts.ui.export.dialog.ExportDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class NewGameHandler.
 */
public class ExportHandler extends AbstractHandler implements IHandler {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(ExportHandler.class);

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Dialog
		final Shell shell = HandlerUtil.getActiveShell(event);
		ExportDialog dialog = new ExportDialog(
				shell);
		if (dialog.open() == Window.OK) {
			final ISession session = dialog.getSession();
			final IExportService exportService = dialog.getExportService();
			final IExportOptions options = dialog.getOptions();
			final File file = dialog.getFile();
			
			// Export Job
			UIJob job = new UIJob("Export") {
				@Override
				@SuppressWarnings("unchecked")
				public IStatus runInUIThread(IProgressMonitor monitor) {
					// touch file
					file.mkdirs();
					
					exportService.export(session, file, options);
					return Status.OK_STATUS;
				}
			};
			job.setUser(true);
			
			// Run
			job.schedule();
			
		} else {
			LOG.info("Cancel Export");
		}
		return null;
	}

}
