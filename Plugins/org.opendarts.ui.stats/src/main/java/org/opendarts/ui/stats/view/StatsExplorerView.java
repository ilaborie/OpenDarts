package org.opendarts.ui.stats.view;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;
import org.opendarts.core.service.session.ISessionService;
import org.opendarts.ui.OpenDartsUiPlugin;
import org.opendarts.ui.label.OpenDartsLabelProvider;
import org.opendarts.ui.stats.content.StatsTreeContentProvider;

/**
 * The Class StatsExplorerView.
 */
public class StatsExplorerView extends ViewPart {
	
	/** The Constant VIEW_ID. */
	public static final String VIEW_ID ="opendarts.view.stats.explorer";

	/** The viewer. */
	private TreeViewer viewer;
	
	/** The session service. */
	private final ISessionService sessionService;

	/**
	 * Instantiates a new stats explorer view.
	 */
	public StatsExplorerView() {
		super();
		this.sessionService = OpenDartsUiPlugin.getService(ISessionService.class);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite main = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(main);
		
		// Tree
		Tree tree = new Tree(main, SWT.SINGLE| SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(tree);

		// Viewer
		this.viewer = new TreeViewer(tree);
		this.viewer.setContentProvider(new StatsTreeContentProvider(this.viewer));
		this.viewer.setLabelProvider(new OpenDartsLabelProvider());
		this.viewer.setUseHashlookup(true);
		
		this.getSite().setSelectionProvider(this.viewer);
		
		this.viewer.setInput(this.sessionService);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		this.viewer.getTree().setFocus();
	}

}

