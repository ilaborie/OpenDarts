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
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.model.IElementStats;
import org.opendarts.core.stats.model.IElementStats.IEntry;
import org.opendarts.core.stats.service.IStatsProvider;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.ui.stats.OpenDartsStatsUiPlugin;
import org.opendarts.ui.utils.ColumnDescriptor;
import org.opendarts.ui.utils.comp.SetDetailComposite;
import org.opendarts.ui.utils.listener.GrabColumnsListener;

/**
 * The Class SessionDetailsPage.
 */
public class SetDetailsPage extends DetailsPage<ISet> implements IDetailsPage {

	private ISet set;
	private final IStatsProvider statsProvider;
	private TableViewer viewer;
	private ControlListener listener;

	private final DateFormat dateFormat;
	private Label lblStarted;
	private Label lblEnded;
	private Label lblWinner;
	private SetDetailComposite setDetailComposite;

	/**
	 * Instantiates a new session details page.
	 */
	public SetDetailsPage() {
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
		section.setText("Set statistics");
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
		this.setDetailComposite = new SetDetailComposite(parent);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(this.setDetailComposite);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IPartSelectionListener#selectionChanged(org.eclipse.ui.forms.IFormPart, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		this.set = (ISet) ((IStructuredSelection) selection).getFirstElement();

		if (this.set != null) {
			// Section 
			this.getSection().setText(this.set.toString());

			// status
			Date date = this.set.getStart().getTime();
			this.lblStarted.setText(this.dateFormat.format(date));

			if (this.set.isFinished()) {
				date = this.set.getEnd().getTime();
				this.lblEnded.setText(this.dateFormat.format(date));

				this.lblWinner.setText(this.set.getWinner().toString());
			} else {
				this.lblEnded.setText("<In Progress>");
				this.lblWinner.setText("<No Winner>");
			}

			// Detail
			this.setDetailComposite.setInput(this.set);

			// Stats
			List<IStatsService> stats = this.statsProvider
					.getSetStats(this.set);
			IElementStats<ISet> setStats;
			List<ColumnDescriptor> columns = new ArrayList<ColumnDescriptor>();
			List<IEntry<ISet>> entries = new ArrayList<IEntry<ISet>>();
			for (IStatsService statsService : stats) {
				setStats = statsService.getSetStats(this.set);
				columns.addAll(this.getColumns(setStats));
				entries.addAll(setStats.getStatsEntries());
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

			this.setDetailComposite.setInput(null);

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
