/*
 * 
 */
package org.opendarts.ui.stats.view;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.menus.IMenuService;
import org.eclipse.ui.part.ViewPart;
import org.opendarts.ui.utils.OpenDartsFormsToolkit;

/**
 * The Class StatsDetailView.
 */
public class StatsView extends ViewPart {

	/** The Constant VIEW_ID. */
	public static final String VIEW_ID = "opendarts.view.stats";

	/** The toolkit. */
	private final OpenDartsFormsToolkit toolkit;

	/** The sform. */
	private ScrolledForm sform;

	/** The m form. */
	private IManagedForm mForm;

	/** The main. */
	private Composite main;

	/** The md. */
	private StatsExplorer md;

	/**
	 * Instantiates a new stats explorer view.
	 */
	public StatsView() {
		super();
		this.toolkit = OpenDartsFormsToolkit.getToolkit();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		this.sform = this.toolkit.createScrolledForm(parent);
		this.sform.setAlwaysShowScrollBars(true);
		this.sform.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayoutFactory.fillDefaults().applyTo(this.sform.getForm());

		this.mForm = new ManagedForm(this.toolkit, this.sform);

		this.main = this.sform.getBody();
		GridDataFactory.fillDefaults().grab(true, true).applyTo(this.main);
		GridLayoutFactory.fillDefaults().applyTo(this.main);

		this.createBody();

		// Toolbar
		ToolBarManager manager = (ToolBarManager) this.sform
				.getToolBarManager();
		IMenuService menuService = (IMenuService) this.getSite().getService(
				IMenuService.class);
		menuService.populateContributionManager(manager,
				"toolbar:openwis.stats.detail");
		manager.update(true);
	}

	/**
	 * Creates the body.
	 */
	private void createBody() {
		this.md = new StatsExplorer();
		this.md.createContent(this.mForm, this.main);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		this.main.setFocus();
	}

}
