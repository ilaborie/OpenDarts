package org.opendarts.ui.stats.view;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.widgets.Section;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.stats.model.IElementStats;
import org.opendarts.core.stats.model.IElementStats.IEntry;
import org.opendarts.core.stats.service.IStatsProvider;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.ui.stats.OpenDartsStatsUiPlugin;
import org.opendarts.ui.utils.ColumnDescriptor;
import org.opendarts.ui.utils.comp.SessionDetailComposite;
import org.opendarts.ui.utils.listener.GrabColumnsListener;

/**
 * The Class SessionDetailsPage.
 */
public class SessionDetailsPage extends DetailsPage<ISession> implements
		IDetailsPage {

	/** The session. */
	private ISession session;
	private final IStatsProvider statsProvider;
	private TableViewer viewer;
	private ControlListener listener;

	private final DateFormat dateFormat;
	private Label lblStarted;
	private Label lblEnded;
	private Label lblWinner;
	private SessionDetailComposite sessionDetailComposite;

	/**
	 * Instantiates a new session details page.
	 */
	public SessionDetailsPage() {
		super();
		this.statsProvider = OpenDartsStatsUiPlugin
				.getService(IStatsProvider.class);
		this.dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
				DateFormat.MEDIUM);
	}

	/**
	 * Creates the internal status.
	 *
	 * @param client the client
	 */
	@Override
	protected void createInternalStatus(Composite client) {
		Label lbl;
		// Started
		lbl = this.getToolkit().createLabel(client, "Started:");
		GridDataFactory.fillDefaults().applyTo(lbl);

		this.lblStarted = this.getToolkit().createLabel(client, "");
		GridDataFactory.fillDefaults().grab(true, false)
				.applyTo(this.lblStarted);

		// Ended
		lbl = this.getToolkit().createLabel(client, "Ended:");
		GridDataFactory.fillDefaults().applyTo(lbl);

		this.lblEnded = this.getToolkit().createLabel(client, "");
		GridDataFactory.fillDefaults().grab(true, false).applyTo(this.lblEnded);

		// Winner
		lbl = this.getToolkit().createLabel(client, "Winner:");
		GridDataFactory.fillDefaults().applyTo(lbl);

		this.lblWinner = this.getToolkit().createLabel(client, "");
		GridDataFactory.fillDefaults().grab(true, false)
				.applyTo(this.lblWinner);
	}

	/**
	 * Creates the stats.
	 *
	 * @param player the player
	 * @param stats the stats
	 */
	@Override
	protected void createStats(Composite parent) {
		Section section = this.getToolkit().createSection(parent,
				Section.DESCRIPTION);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
		section.setText("Session statistics");
		section.marginWidth = 10;

		this.getToolkit().createCompositeSeparator(section);

		Composite client = this.getToolkit().createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().applyTo(client);

		// Create client
		Table table = this.getToolkit().createTable(client, SWT.FULL_SELECTION);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(table);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		this.viewer = new TableViewer(table);
		ColumnViewerToolTipSupport.enableFor(this.viewer);

		this.viewer.setContentProvider(new ArrayContentProvider());

		this.getToolkit().paintBordersFor(client);
		section.setClient(client);
	}

	/**
	 * Creates the internal detail.
	 *
	 * @param parent the client
	 */
	@Override
	protected void createInternalDetail(Composite parent) {
		this.sessionDetailComposite = new SessionDetailComposite(parent);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(this.sessionDetailComposite);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IPartSelectionListener#selectionChanged(org.eclipse.ui.forms.IFormPart, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		this.session = (ISession) ((IStructuredSelection) selection)
				.getFirstElement();

		if (this.session != null) {
			// Section 
			this.getSection().setText(this.session.toString());

			// status
			Date date = this.session.getStart().getTime();
			this.lblStarted.setText(this.dateFormat.format(date));

			if (this.session.isFinished()) {
				date = this.session.getEnd().getTime();
				this.lblEnded.setText(this.dateFormat.format(date));

				this.lblWinner.setText(this.session.getWinner().toString());
			} else {
				this.lblEnded.setText("<In Progress>");
				this.lblWinner.setText("<No Winner>");
			}

			// Detail
			this.sessionDetailComposite.setInput(this.session);

			// Stats
			List<IStatsService> stats = this.statsProvider
					.getSessionStats(this.session);
			IElementStats<ISession> sessionStats;
			List<ColumnDescriptor> columns = new ArrayList<ColumnDescriptor>();
			List<IEntry<ISession>> entries = new ArrayList<IEntry<ISession>>();
			for (IStatsService statsService : stats) {
				sessionStats = statsService.getSessionStats(this.session);
				columns.addAll(this.getColumns(sessionStats));
				entries.addAll(sessionStats.getStatsEntries());
			}

			// Columns
			for (TableColumn c : this.viewer.getTable().getColumns()) {
				c.dispose();
			}
			for (ColumnDescriptor column : columns) {
				this.getToolkit().createTableColumn(this.viewer, column);
			}

			// listener
			if (this.listener != null) {
				this.viewer.getControl().removeControlListener(this.listener);
			}
			this.listener = new GrabColumnsListener(this.viewer, columns);
			this.viewer.getControl().addControlListener(this.listener);

			// table data
			this.viewer.setInput(entries);
		} else {
			// Clear
			this.lblStarted.setText("");
			this.lblEnded.setText("");
			this.lblWinner.setText("");

			this.sessionDetailComposite.setInput(null);

			for (TableColumn c : this.viewer.getTable().getColumns()) {
				c.dispose();
			}
			if (this.listener != null) {
				this.viewer.getControl().removeControlListener(this.listener);
			}
		}
		Composite client = (Composite) this.getSection().getClient();
		client.layout(true);
	}
}
