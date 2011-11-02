package org.opendarts.ui.export.dialog;

import java.io.File;
import java.util.List;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.opendarts.core.export.IExportOptions;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.service.session.ISessionService;
import org.opendarts.ui.OpenDartsUiPlugin;
import org.opendarts.ui.export.OpenDartsUiExportPlugin;
import org.opendarts.ui.export.composite.AbstractExportOptionComposite;
import org.opendarts.ui.export.label.ExportUiServiceLabelProvider;
import org.opendarts.ui.export.service.IExportUiService;
import org.opendarts.ui.label.OpenDartsLabelProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class NewSetDialog.
 */
@SuppressWarnings("rawtypes")
public class ExportDialog extends TitleAreaDialog implements
		ISelectionChangedListener {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(ExportDialog.class);

	/** The body. */
	private Composite body;

	/** The main. */
	private Composite main;

	/** The session viewer. */
	private TableViewer sessionViewer;

	/** The cb games available. */
	private ComboViewer cbExporterAvailable;

	/** The session. */
	private ISession session;

	/** The session service. */
	private ISessionService sessionService;

	/** The export service. */
	private IExportUiService exportService;

	/** The export options composite. */
	private AbstractExportOptionComposite exportOptionsComposite;

	/** The options. */
	private IExportOptions options;

	/** The file. */
	private File file;

	/** The txt file. */
	private Text txtFile;

	/** The btn file. */
	private Button btnFile;

	/**
	 * Instantiates a new new game dialog.
	 *
	 * @param parentShell the parent shell
	 */
	public ExportDialog(Shell parentShell) {
		super(parentShell);
		this.setHelpAvailable(false);

		this.sessionService = OpenDartsUiPlugin
				.getService(ISessionService.class);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	/**
	 * Configure shell.
	 *
	 * @param newShell the new shell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Export ...");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#isResizable()
	 */
	/**
	 * Checks if is resizable.
	 *
	 * @return true, if is resizable
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createContents(org.eclipse.swt.widgets.Composite)
	 */
	/**
	 * Creates the contents.
	 *
	 * @param parent the parent
	 * @return the control
	 */
	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);
		this.getShell().pack();
		return control;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	/**
	 * Creates the dialog area.
	 *
	 * @param parent the parent
	 * @return the control
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite root = (Composite) super.createDialogArea(parent);

		// Main composite
		this.main = new Composite(root, SWT.NONE);
		this.main.setBackground(Display.getDefault()
				.getSystemColor(SWT.COLOR_YELLOW)); // TODO remove after debug
		GridLayoutFactory.fillDefaults().margins(5, 5).numColumns(2)
				.applyTo(this.main);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(this.main);

		// Left composite
		Composite cmpBasic = new Composite(this.main, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(cmpBasic);
		GridLayoutFactory.fillDefaults().margins(2, 2).numColumns(3)
				.applyTo(cmpBasic);

		// Session Table
		this.sessionViewer = new TableViewer(cmpBasic, SWT.SINGLE | SWT.BORDER);
		Table table = this.sessionViewer.getTable();
		GridDataFactory.fillDefaults().span(3, 1).grab(true, true)
				.applyTo(table);
		this.sessionViewer.setLabelProvider(new OpenDartsLabelProvider());
		this.sessionViewer.setContentProvider(new ArrayContentProvider());
		this.sessionViewer.setInput(this.sessionService.getAllSessions());
		this.sessionViewer.addSelectionChangedListener(this);

		// Combo for Exporter
		Label lblGameAvailable = new Label(cmpBasic, SWT.WRAP);
		lblGameAvailable.setText("Available(s) Exporters: ");
		GridDataFactory.fillDefaults().applyTo(lblGameAvailable);

		this.cbExporterAvailable = new ComboViewer(cmpBasic);
		GridDataFactory.fillDefaults().span(2, 1).grab(true, false)
				.applyTo(this.cbExporterAvailable.getControl());
		this.cbExporterAvailable
				.setLabelProvider(new ExportUiServiceLabelProvider());

		this.cbExporterAvailable.setContentProvider(new ArrayContentProvider());
		this.cbExporterAvailable.addSelectionChangedListener(this);
		this.cbExporterAvailable.getControl().setEnabled(false);

		// File
		Label lblFile = new Label(cmpBasic, SWT.WRAP);
		lblFile.setText("Folder: ");
		GridDataFactory.fillDefaults().applyTo(lblFile);

		this.txtFile = new Text(cmpBasic, SWT.BORDER);
		GridDataFactory.swtDefaults().hint(200, SWT.DEFAULT).grab(true, false)
				.applyTo(this.txtFile);
		this.txtFile.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				ExportDialog.this.file = new File(ExportDialog.this.txtFile
						.getText());
				ExportDialog.this.validate();
			}
		});

		this.btnFile = new Button(cmpBasic, SWT.PUSH);
		this.btnFile.setText("...");
		this.btnFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(ExportDialog.this
						.getShell());
				String file = dialog.open();
				if (file != null) {
					txtFile.setText(file);
				}
			}
		});

		// Body
		this.body = new Composite(root, SWT.NONE);
		this.body.setBackground(Display.getDefault()
				.getSystemColor(SWT.COLOR_GREEN)); // TODO remove after debug
		GridDataFactory.fillDefaults().grab(true, true).applyTo(this.body);
		GridLayoutFactory.fillDefaults().applyTo(this.body);
		return root;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	/**
	 * Ok pressed.
	 */
	@Override
	protected void okPressed() {
		LOG.debug("ExportDialog#ok");
		// Export
		if (this.exportService != null && this.session != null) {
			this.options = null;
			if (this.exportOptionsComposite != null) {
				this.options = this.exportOptionsComposite.getExportOptions();
			}
			super.okPressed();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		ISelection selection = event.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sel = (IStructuredSelection) selection;
			if (this.sessionViewer.equals(event.getSource())) {
				// Update session
				this.session = (ISession) sel.getFirstElement();

				// Determine available exporter
				List<IExportUiService<?>> exporters = OpenDartsUiExportPlugin
						.getDefault().getExportProvider().getExporter(session);

				// Update Combo
				this.cbExporterAvailable.setInput(exporters);
				this.cbExporterAvailable.getControl().setEnabled(
						!exporters.isEmpty());
			} else if (this.cbExporterAvailable.equals(event.getSource())) {
				IExportUiService service = (IExportUiService) sel
						.getFirstElement();
				if ((this.exportService != null && !this.exportService
						.equals(service)) || (service != null)) {
					this.body.dispose();

					this.body = new Composite(this.main, SWT.NONE);
					GridDataFactory.fillDefaults().span(2, 1).grab(true, true)
							.applyTo(this.body);
					GridLayoutFactory.fillDefaults().applyTo(this.body);

					this.exportService = service;
					if (this.exportService != null) {
						this.exportOptionsComposite = this.exportService
								.createExportOptionsComposite(body);
						GridDataFactory.fillDefaults().grab(true, true)
								.applyTo(this.exportOptionsComposite);
						this.getShell().pack(true);
						this.exportOptionsComposite.setFocus();
					}
				}
			}
		}
		this.validate();
	}

	/**
	 * Validate.
	 */
	private void validate() {
		this.setErrorMessage(null);
		boolean ok = true;
		if (session == null) {
			this.setErrorMessage("Select a session");
			ok = false;
		}
		if (ok && this.exportService == null) {
			this.setErrorMessage("Select an Exporter");
			ok = false;
		}
		if (ok && this.file == null) {
			this.setErrorMessage("Select a destination folder");
			ok = false;
		}
		this.getButton(OK).setEnabled(ok);
	}

	/**
	 * Gets the options.
	 *
	 * @return the options
	 */
	public IExportOptions getOptions() {
		return this.options;
	}

	/**
	 * Gets the export service.
	 *
	 * @return the export service
	 */
	public IExportUiService getExportService() {
		return this.exportService;
	}

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	public ISession getSession() {
		return this.session;
	}

	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public File getFile() {
		return this.file;
	}

}
