package org.opendarts.ui.x01.dialog;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.FormDialog;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.service.IStatsProvider;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.core.x01.service.StatsX01Service;
import org.opendarts.ui.utils.ColumnDescriptor;
import org.opendarts.ui.utils.OpenDartsFormsToolkit;
import org.opendarts.ui.utils.comp.SetDetailComposite;
import org.opendarts.ui.utils.listener.GrabColumnsListener;
import org.opendarts.ui.x01.X01UiPlugin;
import org.opendarts.ui.x01.label.SetStatsLabelProvider;

/**
 * The Class GameX01FinishDialog.
 */
public class SetX01InfoDialog extends FormDialog implements ControlListener {

	/** The toolkit. */
	private final OpenDartsFormsToolkit toolkit;

	/** The set. */
	private final ISet set;

	/** The body. */
	private Composite body;

	/** The stats services. */
	private final List<IStatsService> statsServices;

	/**
	 * Instantiates a new game x501 finish dialog.
	 *
	 * @param parentShell the parent shell
	 * @param set the set
	 */
	public SetX01InfoDialog(Shell parentShell, ISet set) {
		super(parentShell);
		this.set = set;
		// Stats
		this.statsServices = X01UiPlugin.getService(IStatsProvider.class)
				.getSetStats(set);
		this.toolkit = OpenDartsFormsToolkit.getToolkit();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#getShellStyle()
	 */
	@Override
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.SHEET;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(this.set.toString());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#isResizable()
	 */
	@Override
	protected boolean isResizable() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.FormDialog#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void createFormContent(IManagedForm mform) {
		String title;
		IPlayer winner = this.set.getWinner();
		if (winner != null) {
			title = MessageFormat.format("Set for {0}", winner);
		} else {
			title = "No winner";
		}
		ScrolledForm form = mform.getForm();
		form.setText(title);
		this.toolkit.decorateFormHeading(form.getForm());

		this.body = form.getBody();
		GridLayoutFactory.fillDefaults().margins(5, 5).applyTo(this.body);

		// Table
		Composite legsComposite = this.createLegsComposite(this.body);
		GridDataFactory.fillDefaults().applyTo(legsComposite);

		// Stats
		Composite statsComposite = this.createStatsComposite(this.body);
		GridDataFactory.fillDefaults().applyTo(statsComposite);
	}

	/**
	 * Creates the stats composite.
	 *
	 * @param parent the parent
	 * @return the composite
	 */
	private Composite createLegsComposite(Composite parent) {
		Composite main = this.toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().applyTo(main);

		// Section
		Section section = this.toolkit.createSection(main,
				ExpandableComposite.TITLE_BAR | ExpandableComposite.EXPANDED);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
		section.setText("Legs");

		// Section body
		Composite client = this.toolkit.createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().applyTo(client);

		SetDetailComposite setDetailComp = new SetDetailComposite(client);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(setDetailComp);

		setDetailComp.setInput(this.set);

		// End section
		this.toolkit.paintBordersFor(client);
		section.setClient(client);
		return main;
	}

	/**
	 * Creates the stats composite.
	 *
	 * @param parent the parent
	 * @return the composite
	 */
	private Composite createStatsComposite(Composite parent) {
		Composite main = this.toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().applyTo(main);

		// Section
		Section section = this.toolkit.createSection(main,
				ExpandableComposite.TITLE_BAR | ExpandableComposite.EXPANDED);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
		section.setText("Statistiques");

		// Section body
		Composite client = this.toolkit.createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().applyTo(client);

		Table table = this.toolkit.createTable(client, SWT.V_SCROLL
				| SWT.BORDER | SWT.FULL_SELECTION);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableViewer viewer = new TableViewer(table);
		viewer.setContentProvider(new ArrayContentProvider());

		List<ColumnDescriptor> columns = this.getStatsColumns(viewer);
		viewer.getControl().addControlListener(
				new GrabColumnsListener(viewer, columns));

		viewer.setInput(this.set.getGameDefinition().getPlayers());

		// End section
		this.toolkit.paintBordersFor(client);
		section.setClient(client);
		return main;
	}

	/**
	 * Gets the stats columns.
	 *
	 * @param viewer the viewer
	 * @return the stats columns
	 */
	private List<ColumnDescriptor> getStatsColumns(TableViewer viewer) {
		List<ColumnDescriptor> result = new ArrayList<ColumnDescriptor>();

		ColumnDescriptor colDescr;
		// Column player
		colDescr = new ColumnDescriptor("Player");
		colDescr.width(60);
		colDescr.labelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof IPlayer) {
					IPlayer player = (IPlayer) element;
					return player.getName();
				}
				return super.getText(element);
			}
		});
		this.toolkit.createTableColumn(viewer, colDescr);
		result.add(colDescr);

		// All Stats
		List<String> stats = Arrays.asList(StatsX01Service.SET_AVG_DART,
				StatsX01Service.SET_AVG_3_DARTS, StatsX01Service.SET_60_PLUS,
				StatsX01Service.SET_TONS, StatsX01Service.SET_TONS_PLUS,
				StatsX01Service.SET_180s, StatsX01Service.SET_BEST_LEG,
				StatsX01Service.SET_BEST_OUT, StatsX01Service.SET_COUNT_DARTS,
				StatsX01Service.SET_TOTAL_SCORE);

		Map<String, String> labels = new HashMap<String, String>();
		labels.put(StatsX01Service.SET_AVG_DART, "Avg. Darts");
		labels.put(StatsX01Service.SET_AVG_3_DARTS, "Avg. 3 Darts");
		labels.put(StatsX01Service.SET_60_PLUS, "60+");
		labels.put(StatsX01Service.SET_TONS, "Tons");
		labels.put(StatsX01Service.SET_TONS_PLUS, "Tons+");
		labels.put(StatsX01Service.SET_180s, "180");
		labels.put(StatsX01Service.SET_BEST_LEG, "Best Leg");
		labels.put(StatsX01Service.SET_BEST_OUT, "Best Out");
		labels.put(StatsX01Service.SET_COUNT_DARTS, "Nb Darts");
		labels.put(StatsX01Service.SET_TOTAL_SCORE, "Total Score");

		for (String statKey : stats) {
			colDescr = new ColumnDescriptor(labels.get(statKey));
			colDescr.width(60);
			colDescr.labelProvider(new SetStatsLabelProvider(
					this.statsServices, this.set, statKey));
			this.toolkit.createTableColumn(viewer, colDescr);
			result.add(colDescr);
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.ControlListener#controlMoved(org.eclipse.swt.events.ControlEvent)
	 */
	@Override
	public void controlMoved(ControlEvent e) {
		// Nothing to do
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.ControlListener#controlResized(org.eclipse.swt.events.ControlEvent)
	 */
	@Override
	public void controlResized(ControlEvent e) {
		this.body.layout(true);
	}

}