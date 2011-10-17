package org.opendarts.ui.stats.view;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IDetailsPageProvider;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.opendarts.core.service.session.ISessionService;
import org.opendarts.ui.OpenDartsUiPlugin;
import org.opendarts.ui.stats.content.ChartsTreeContentProvider;
import org.opendarts.ui.stats.label.ChartLabelProvider;
import org.opendarts.ui.utils.ISharedImages;
import org.opendarts.ui.utils.OpenDartsFormsToolkit;

/**
 * The Class StatsExplorer.
 */
public class StatsExplorer extends MasterDetailsBlock {

	/** The page. */
	private TreeViewer viewer;

	/** The session service. */
	private final ISessionService sessionService;

	/** The toolkit. */
	private OpenDartsFormsToolkit toolkit;

	/**
	 * Instantiates a new stats explorer.
	 *
	 */
	public StatsExplorer() {
		super();
		//		this.page = page;
		this.sessionService = OpenDartsUiPlugin
				.getService(ISessionService.class);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.MasterDetailsBlock#createMasterPart(org.eclipse.ui.forms.IManagedForm, org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createMasterPart(final IManagedForm managedForm,
			Composite parent) {
		final ScrolledForm form = managedForm.getForm();
		form.setText("Statistics");
		this.toolkit = (OpenDartsFormsToolkit) managedForm.getToolkit();

		this.toolkit.decorateFormHeading(form.getForm());

		// Section
		Section section = this.toolkit.createSection(parent,
				Section.DESCRIPTION);
		section.setText("Session, set and legs");
		section.marginWidth = 10;

		Composite client = this.toolkit.createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().applyTo(client);

		// Tree
		Tree tree = new Tree(client, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(tree);

		// Viewer
		this.viewer = new TreeViewer(tree);
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
		this.viewer.setContentProvider(new ChartsTreeContentProvider(viewer));
		this.viewer.setLabelProvider(new ChartLabelProvider());

		this.viewer.setInput(this.sessionService);
		this.viewer.expandToLevel(2);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.MasterDetailsBlock#registerPages(org.eclipse.ui.forms.DetailsPart)
	 */
	@Override
	protected void registerPages(DetailsPart detailsPart) {
		IDetailsPageProvider detailsPageProvider = new ElementDetailProvider();
		detailsPart.setPageProvider(detailsPageProvider);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.MasterDetailsBlock#createToolBarActions(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void createToolBarActions(IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		// Horizontal
		Action haction = new Action("hor", IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				StatsExplorer.this.sashForm.setOrientation(SWT.HORIZONTAL);
				form.reflow(true);
			}
		};
		haction.setChecked(true);
		haction.setToolTipText("Horizontal orientation");
		haction.setImageDescriptor(OpenDartsUiPlugin
				.getImageDescriptor(ISharedImages.IMG_SEP_H));

		// Vertical
		Action vaction = new Action("ver", IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				StatsExplorer.this.sashForm.setOrientation(SWT.VERTICAL);
				form.reflow(true);
			}
		};
		vaction.setChecked(false);
		vaction.setToolTipText("Vertical orientation");
		vaction.setImageDescriptor(OpenDartsUiPlugin
				.getImageDescriptor(ISharedImages.IMG_SEP_V));
		form.getToolBarManager().add(haction);
		form.getToolBarManager().add(vaction);
	}

}
