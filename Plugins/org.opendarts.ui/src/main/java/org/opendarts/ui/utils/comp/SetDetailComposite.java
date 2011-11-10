/*
 * 
 */
package org.opendarts.ui.utils.comp;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.func.GameIndexFunction;
import org.opendarts.core.model.game.func.GamePlayersFunction;
import org.opendarts.core.model.game.func.GameWinnerFunction;
import org.opendarts.core.model.session.ISet;
import org.opendarts.ui.OpenDartsUiPlugin;
import org.opendarts.ui.label.FunctionalLabelProvider;
import org.opendarts.ui.service.IGameUiProvider;
import org.opendarts.ui.service.IGameUiService;
import org.opendarts.ui.utils.ColumnDescriptor;
import org.opendarts.ui.utils.OpenDartsFormsToolkit;
import org.opendarts.ui.utils.listener.GrabColumnsListener;

/**
 * The Class SessionDetailComposite.
 */
public class SetDetailComposite extends Composite {

	private ISet set;
	private TableViewer viewer;
	private final OpenDartsFormsToolkit toolkit;

	private IGameUiService gameUiService;

	private IGameUiProvider gameUiProvider;

	/**
	 * Instantiates a new session detail composite.
	 *
	 * @param parent the parent
	 */
	public SetDetailComposite(Composite parent) {
		super(parent, SWT.NONE);
		this.toolkit = OpenDartsFormsToolkit.getToolkit();
		GridLayoutFactory.fillDefaults().applyTo(this);
		this.createContents();
	}

	/**
	 * Gets the game ui provider.
	 *
	 * @return the game ui provider
	 */
	public IGameUiProvider getGameUiProvider() {
		if (this.gameUiProvider == null) {
			this.gameUiProvider = OpenDartsUiPlugin
					.getService(IGameUiProvider.class);
		}
		return this.gameUiProvider;
	}

	/**
	 * Gets the game ui service.
	 *
	 * @return the game ui service
	 */
	public IGameUiService getGameUiService() {
		if (this.gameUiService == null) {
			this.gameUiService = this.getGameUiProvider().getGameUiService(
					set.getGameDefinition());
		}
		return this.gameUiService;
	}

	/**
	 * Sets the input.
	 *
	 * @param session the new input
	 */
	public void setInput(ISet set) {
		this.set = set;
		if (set != null) {
			this.gameUiService = this.getGameUiProvider().getGameUiService(
					set.getGameDefinition());
			this.viewer.setInput(this.set.getAllGame());
		} else {
			this.gameUiService = null;
			this.viewer.setInput(new ArrayList<ISet>());
		}
	}

	/**
	 * Creates the contents.
	 */
	private void createContents() {
		Table table = new Table(this, SWT.V_SCROLL | SWT.BORDER
				| SWT.FULL_SELECTION);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		this.viewer = new TableViewer(table);
		this.viewer.setContentProvider(new ArrayContentProvider());

		List<ColumnDescriptor> columns = this.getColumns();
		this.viewer.getControl().addControlListener(
				new GrabColumnsListener(this.viewer, columns));
	}

	/**
	 * Gets the columns.
	 *
	 * @return the columns
	 */
	private List<ColumnDescriptor> getColumns() {
		List<ColumnDescriptor> result = new ArrayList<ColumnDescriptor>();

		ColumnDescriptor colDescr;

		// Column index
		colDescr = new ColumnDescriptor("Game");
		colDescr.width(30);
		colDescr.labelProvider(new FunctionalLabelProvider<IGame>(IGame.class,
				new GameIndexFunction()));
		this.toolkit.createTableColumn(this.viewer, colDescr);
		result.add(colDescr);

		// Column players
		colDescr = new ColumnDescriptor("Players");
		colDescr.width(100);
		colDescr.labelProvider(new FunctionalLabelProvider<IGame>(IGame.class,
				new GamePlayersFunction()));
		this.toolkit.createTableColumn(this.viewer, colDescr);
		result.add(colDescr);

		// Column Winner
		colDescr = new ColumnDescriptor("Winner");
		colDescr.width(60);
		colDescr.labelProvider(new FunctionalLabelProvider<IGame>(IGame.class,
				new GameWinnerFunction()));
		this.toolkit.createTableColumn(this.viewer, colDescr);
		result.add(colDescr);

		// Column nb darts
		colDescr = new ColumnDescriptor("Result");
		colDescr.width(45);
		colDescr.labelProvider(new FunctionalLabelProvider<IGame>(IGame.class,
				new GameResultFunction(this.getGameUiProvider())));
		this.toolkit.createTableColumn(this.viewer, colDescr);
		result.add(colDescr);

		return result;
	}

}
