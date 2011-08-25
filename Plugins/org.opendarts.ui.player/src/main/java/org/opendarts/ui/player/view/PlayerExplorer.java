package org.opendarts.ui.player.view;

import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.service.player.IPlayerService;
import org.opendarts.ui.player.OpenDartsPlayerUiPlugin;
import org.opendarts.ui.player.label.PlayerLabelProvider;
import org.opendarts.ui.utils.ColumnDescriptor;
import org.opendarts.ui.utils.OpenDartsFormsToolkit;

/**
 * The Class StatsExplorer.
 */
public class PlayerExplorer extends MasterDetailsBlock {

	/** The page. */
	private TableViewer viewer;

	private final IPlayerService playerService;

	/** The toolkit. */
	private OpenDartsFormsToolkit toolkit;

	/**
	 * Instantiates a new stats explorer.
	 *
	 */
	public PlayerExplorer() {
		super();
		this.playerService = OpenDartsPlayerUiPlugin
				.getService(IPlayerService.class);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.MasterDetailsBlock#createMasterPart(org.eclipse.ui.forms.IManagedForm, org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createMasterPart(final IManagedForm managedForm,
			Composite parent) {
		final ScrolledForm form = managedForm.getForm();
		form.setText("players");
		this.toolkit = (OpenDartsFormsToolkit) managedForm.getToolkit();

		this.toolkit.decorateFormHeading(form.getForm());

		// Section
		Section section = this.toolkit.createSection(parent,
				Section.DESCRIPTION);
		section.setText("");
		section.marginWidth = 10;

		Composite client = this.toolkit.createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().applyTo(client);

		// Tree
		Table table = new Table(client, SWT.SINGLE | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(table);

		// Viewer
		this.viewer = new TableViewer(table);

		this.viewer.setUseHashlookup(true);
		this.toolkit.paintBordersFor(client);
		section.setClient(client);

		// End viewer
		final SectionPart spart = new SectionPart(section);
		managedForm.addPart(spart);
		this.viewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						managedForm.fireSelectionChanged(spart,
								event.getSelection());
					}
				});
		this.viewer.setContentProvider(new ArrayContentProvider());

		ColumnDescriptor column = new ColumnDescriptor("Player");
		column.width(100).labelProvider(new PlayerLabelProvider());
		this.toolkit.createTableColumn(this.viewer, column);

		List<IPlayer> players = this.playerService.getAllPlayers();
		this.viewer.setInput(players);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.MasterDetailsBlock#registerPages(org.eclipse.ui.forms.DetailsPart)
	 */
	@Override
	protected void registerPages(DetailsPart detailsPart) {
		detailsPart.setPageProvider(new PlayerDetailProvider());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.MasterDetailsBlock#createToolBarActions(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void createToolBarActions(IManagedForm managedForm) {
		// No action yet
	}

}
