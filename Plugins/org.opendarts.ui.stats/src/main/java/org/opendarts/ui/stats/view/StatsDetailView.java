/*
 * 
 */
package org.opendarts.ui.stats.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.menus.IMenuService;
import org.eclipse.ui.part.ViewPart;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.model.IElementStats;
import org.opendarts.core.stats.service.IStatsProvider;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.ui.OpenDartsUiPlugin;
import org.opendarts.ui.label.OpenDartsLabelProvider;
import org.opendarts.ui.stats.label.KeyLabelProvider;
import org.opendarts.ui.stats.label.PlayerStatsLabelProvider;
import org.opendarts.ui.utils.ColumnDescriptor;
import org.opendarts.ui.utils.OpenDartsFormsToolkit;

/**
 * The Class StatsDetailView.
 */
public class StatsDetailView extends ViewPart implements ISelectionListener {

	/** The Constant VIEW_ID. */
	public static final String VIEW_ID = "opendarts.view.stats.detail";

	/** The stats provider. */
	private final IStatsProvider statsProvider;

	/** The toolkit. */
	private final OpenDartsFormsToolkit toolkit;

	/** The main. */
	private Composite main;

	/** The body. */
	private Composite body;

	/** The sform. */
	private ScrolledForm sform;

	/** The label provider. */
	private final ColumnLabelProvider labelProvider;

	/** The last selected. */
	private Object lastSelected;

	/**
	 * Instantiates a new stats explorer view.
	 */
	public StatsDetailView() {
		super();
		this.statsProvider = OpenDartsUiPlugin.getService(IStatsProvider.class);
		this.toolkit = OpenDartsFormsToolkit.getToolkit();
		this.labelProvider = new OpenDartsLabelProvider();
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

		this.main = this.sform.getBody();
		GridDataFactory.fillDefaults().grab(true, true).applyTo(this.main);
		GridLayoutFactory.fillDefaults().applyTo(this.main);

		IWorkbenchPage page = this.getSite().getPage();
		page.addSelectionListener(this);

		this.createBody(page.getSelection());

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
	 *
	 * @param selection the selection
	 */
	private void createBody(ISelection selection) {
		if ((selection != null) && !selection.isEmpty()
				&& (selection instanceof IStructuredSelection)) {
			Object selected = ((IStructuredSelection) selection)
					.getFirstElement();
			this.lastSelected = selected;
			this.createBody(selected);

		}
	}

	/**
	 * Creates the body.
	 *
	 * @param selected the selected
	 */
	private void createBody(Object selected) {
		this.body = this.toolkit.createComposite(this.main, SWT.NONE);
		GridLayoutFactory.fillDefaults().margins(5, 5).applyTo(this.body);

		this.sform.setImage(this.labelProvider.getImage(selected));
		this.sform.setText(this.labelProvider.getText(selected));
		this.toolkit.decorateFormHeading(this.sform.getForm());

		List<IStatsService> stats = null;
		if (selected instanceof ISession) {
			ISession session = (ISession) selected;
			stats = this.statsProvider.getSessionStats(session);
			for (IStatsService statsService : stats) {
				this.createSessionStats(this.body, statsService, session,
						statsService.getName());
			}
		} else if (selected instanceof ISet) {
			ISet set = (ISet) selected;
			stats = this.statsProvider.getSetStats(set);
			for (IStatsService statsService : stats) {
				this.createSetStats(this.body, statsService, set,
						statsService.getName());
			}
		} else if (selected instanceof IGame) {
			IGame game = (IGame) selected;
			stats = this.statsProvider.getGameStats(game);
			for (IStatsService statsService : stats) {
				this.createGameStats(this.body, statsService, game,
						statsService.getName());
			}
		}
	}

	/**
	 * Creates the session stats.
	 *
	 * @param statsService the stats service
	 * @param sessionStats the session stats
	 */
	private void createSessionStats(Composite parent,
			IStatsService statsService, ISession session, String name) {
		IElementStats<ISession> sessionStats = statsService
				.getSessionStats(session);
		// Section
		Section section = this.toolkit.createSection(parent,
				ExpandableComposite.TITLE_BAR);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
		section.setText(name);

		// Section body
		Composite client = this.toolkit.createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().applyTo(client);

		// Fill client
		this.createStats(client, sessionStats);

		// Add all set stats
		List<ISet> allSet = sessionStats.getElement().getAllGame();
		Composite setComposite = this.toolkit.createComposite(client);
		GridLayoutFactory.fillDefaults().numColumns(allSet.size())
				.equalWidth(true).applyTo(setComposite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(setComposite);
		for (ISet set : allSet) {
			this.createSetStats(setComposite, statsService, set, set.getName());
		}

		// End section definition
		this.toolkit.paintBordersFor(client);
		section.setClient(client);
	}

	/**
	 * Creates the set stats.
	 *
	 * @param parent the parent
	 * @param statsService the stats service
	 * @param set the set
	 * @param name the name
	 */
	private void createSetStats(Composite parent, IStatsService statsService,
			ISet set, String name) {
		IElementStats<ISet> setStats = statsService.getSetStats(set);
		// Section
		Section section = this.toolkit.createSection(parent,
				ExpandableComposite.TITLE_BAR);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
		section.setText(name);

		// Section body
		Composite client = this.toolkit.createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().applyTo(client);

		// Fill client
		this.createStats(client, setStats);

		// Add all game stats
		List<IGame> allGame = setStats.getElement().getAllGame();
		Composite gameComposite = this.toolkit.createComposite(client);
		GridLayoutFactory.fillDefaults().numColumns(allGame.size())
				.equalWidth(true).applyTo(gameComposite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(gameComposite);
		for (IGame game : allGame) {
			this.createGameStats(gameComposite, statsService, game,
					game.getName());
		}

		// End section definition
		this.toolkit.paintBordersFor(client);
		section.setClient(client);
	}

	/**
	 * Creates the game stats.
	 *
	 * @param parent the parent
	 * @param statsService the stats service
	 * @param game the game
	 * @param name the name
	 */
	private void createGameStats(Composite parent, IStatsService statsService,
			IGame game, String name) {
		IElementStats<IGame> gameStats = statsService.getGameStats(game);
		// Section
		Section section = this.toolkit.createSection(parent,
				ExpandableComposite.TITLE_BAR);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
		section.setText(name);

		// Section body
		Composite client = this.toolkit.createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().applyTo(client);

		// Fill client
		this.createStats(client, gameStats);

		// End section definition
		this.toolkit.paintBordersFor(client);
		section.setClient(client);
	}

	/**
	 * Creates the stats.
	 *
	 * @param player the player
	 * @param stats the stats
	 */
	private void createStats(Composite parent, IElementStats<?> eltStats) {
		// Create client
		Table table = this.toolkit.createTable(parent, SWT.FULL_SELECTION);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(table);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		TableViewer viewer = new TableViewer(table);

		viewer.setContentProvider(new ArrayContentProvider());

		for (ColumnDescriptor column : this.getColumns(eltStats)) {
			this.toolkit.createTableColumn(viewer, column);
		}

		viewer.setInput(eltStats.getStatsEntries());

		for (TableColumn c : table.getColumns()) {
			c.pack();
		}
	}

	/**
	 * Gets the columns.
	 * @param eltStats 
	 *
	 * @return the columns
	 */
	private List<ColumnDescriptor> getColumns(IElementStats<?> eltStats) {
		List<ColumnDescriptor> columns = new ArrayList<ColumnDescriptor>();
		ColumnDescriptor column;

		// Key
		column = new ColumnDescriptor("Key", SWT.LEFT);
		columns.add(column);
		column.width(200).labelProvider(new KeyLabelProvider());

		// Player value
		for (IPlayer player : eltStats.getPlayers()) {
			column = new ColumnDescriptor(this.labelProvider.getText(player),
					SWT.LEFT);
			columns.add(column);
			column.width(200).labelProvider(
					new PlayerStatsLabelProvider(player));
		}
		return columns;
	}

	/**
	 * Refresh.
	 */
	public void refresh() {
		if ((this.body != null) && !this.body.isDisposed()) {
			this.body.dispose();
		}
		this.createBody(this.lastSelected);
		this.layoutBody();
	}

	/**
	 * Refresh.
	 *
	 * @param selection the selection
	 */
	private void refresh(ISelection selection) {
		if ((this.body != null) && !this.body.isDisposed()) {
			this.body.dispose();
		}
		this.createBody(selection);
		this.layoutBody();
	}

	/**
	 * Layout body.
	 */
	private void layoutBody() {
		if ((this.body != null) && !this.body.isDisposed()) {
			GridDataFactory.fillDefaults().grab(true, true).applyTo(this.body);
		}
		if (!this.main.isDisposed()) {
			this.main.layout(true, true);
			this.main.layout(true);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		this.refresh(selection);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		this.main.setFocus();
	}

}
