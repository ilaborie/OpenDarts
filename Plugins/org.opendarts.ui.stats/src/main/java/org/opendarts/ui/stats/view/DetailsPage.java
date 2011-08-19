package org.opendarts.ui.stats.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.IElementStats;
import org.opendarts.ui.label.OpenDartsLabelProvider;
import org.opendarts.ui.stats.label.KeyLabelProvider;
import org.opendarts.ui.stats.label.PlayerStatsLabelProvider;
import org.opendarts.ui.utils.ColumnDescriptor;
import org.opendarts.ui.utils.OpenDartsFormsToolkit;

/**
 * The Class SessionDetailsPage.
 */
public abstract class DetailsPage<E> implements IDetailsPage {

	private OpenDartsFormsToolkit toolkit;
	private Section section;
	private TableViewer viewer;
	private final OpenDartsLabelProvider labelProvider;

	/**
	 * Instantiates a new session details page.
	 */
	public DetailsPage() {
		super();
		this.labelProvider = new OpenDartsLabelProvider();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#initialize(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	public void initialize(IManagedForm form) {
		this.toolkit = (OpenDartsFormsToolkit) form.getToolkit();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createContents(Composite parent) {
		GridLayoutFactory.fillDefaults().margins(5, 5).applyTo(parent);
		// Section
		this.section = this.toolkit.createSection(parent,
				ExpandableComposite.TITLE_BAR);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(this.section);
		this.section.setText("Session");

		// Section body
		Composite client = this.toolkit.createComposite(this.section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().applyTo(client);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(client);

		// Status
		this.createStatus(client);

		// Stats
		this.createStats(client);

		// Detail
		this.createDetail(client);

		// End section definition
		this.toolkit.paintBordersFor(client);
		this.section.setClient(client);
	}

	/**
	 * Gets the section.
	 *
	 * @return the section
	 */
	protected Section getSection() {
		return this.section;
	}

	/**
	 * Gets the toolkit.
	 *
	 * @return the toolkit
	 */
	public OpenDartsFormsToolkit getToolkit() {
		return this.toolkit;
	}

	/**
	 * Creates the status.
	 *
	 * @param client the client
	 */
	protected void createStatus(Composite parent) {
		Section section = this.toolkit.createSection(parent,
				Section.DESCRIPTION);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(section);
		section.setText("Status");
		section.marginWidth = 10;

		this.toolkit.createCompositeSeparator(section);

		Composite client = this.toolkit.createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(client);

		this.createInternalStatus(client);

		this.toolkit.paintBordersFor(client);
		section.setClient(client);
	}

	/**
	 * Creates the internal status.
	 *
	 * @param client the client
	 */
	protected abstract void createInternalStatus(Composite client);

	/**
	 * Creates the stats.
	 *
	 * @param player the player
	 * @param stats the stats
	 */
	protected void createStats(Composite parent) {
		Section section = this.toolkit.createSection(parent,
				Section.DESCRIPTION);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
		section.setText("Session statistics");
		section.marginWidth = 10;

		this.toolkit.createCompositeSeparator(section);

		Composite client = this.toolkit.createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().applyTo(client);

		// Create client
		Table table = this.toolkit.createTable(client, SWT.V_SCROLL
				| SWT.BORDER | SWT.FULL_SELECTION);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(table);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		this.viewer = new TableViewer(table);
		ColumnViewerToolTipSupport.enableFor(this.viewer);

		this.viewer.setContentProvider(new ArrayContentProvider());

		this.toolkit.paintBordersFor(client);
		section.setClient(client);
	}

	/**
	 * Gets the columns.
	 * @param eltStats 
	 *
	 * @return the columns
	 */
	protected List<ColumnDescriptor> getColumns(IElementStats<E> eltStats) {
		List<ColumnDescriptor> columns = new ArrayList<ColumnDescriptor>();
		ColumnDescriptor column;

		// Key
		column = new ColumnDescriptor("Stat", SWT.RIGHT);
		columns.add(column);
		column.width(200).reziable(true).labelProvider(new KeyLabelProvider());

		// Player value
		for (IPlayer player : eltStats.getPlayers()) {
			column = new ColumnDescriptor(this.labelProvider.getText(player),
					SWT.CENTER);
			columns.add(column);
			column.width(200).reziable(true)
					.labelProvider(new PlayerStatsLabelProvider(player));
		}
		return columns;
	}

	/**
	 * Creates the status.
	 *
	 * @param client the client
	 */
	protected void createDetail(Composite parent) {
		Section section = this.toolkit.createSection(parent,
				Section.DESCRIPTION);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
		section.setText("Detail");
		section.marginWidth = 10;

		this.toolkit.createCompositeSeparator(section);

		Composite client = this.toolkit.createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().applyTo(client);

		this.createInternalDetail(client);

		this.toolkit.paintBordersFor(client);
		section.setClient(client);
	}

	/**
	 * Creates the internal detail.
	 *
	 * @param parent the client
	 */
	protected abstract void createInternalDetail(Composite parent);

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#setFocus()
	 */
	@Override
	public void setFocus() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#setFormInput(java.lang.Object)
	 */
	@Override
	public boolean setFormInput(Object input) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#refresh()
	 */
	@Override
	public void refresh() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#isStale()
	 */
	@Override
	public boolean isStale() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#isDirty()
	 */
	@Override
	public boolean isDirty() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#commit(boolean)
	 */
	@Override
	public void commit(boolean onSave) {
		// Nothing to do
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#dispose()
	 */
	@Override
	public void dispose() {
		// Nothing to dispose
	}

}
