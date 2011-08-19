/*
 * 
 */
package org.opendarts.ui.utils.comp;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.ui.utils.ColumnDescriptor;
import org.opendarts.ui.utils.OpenDartsFormsToolkit;
import org.opendarts.ui.utils.listener.GrabColumnsListener;

/**
 * The Class SessionDetailComposite.
 */
public class SessionDetailComposite extends Composite {

	/** The session. */
	private ISession session;
	private TableViewer viewer;
	private final OpenDartsFormsToolkit toolkit;

	/**
	 * Instantiates a new session detail composite.
	 *
	 * @param parent the parent
	 */
	public SessionDetailComposite(Composite parent) {
		super(parent, SWT.NONE);
		this.toolkit = OpenDartsFormsToolkit.getToolkit();
		GridLayoutFactory.fillDefaults().applyTo(this);
		this.createContents();
	}

	/**
	 * Sets the input.
	 *
	 * @param session the new input
	 */
	public void setInput(ISession session) {
		this.session = session;
		if (session != null) {
			this.viewer.setInput(this.session.getAllGame());
		} else {
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
		colDescr = new ColumnDescriptor("Set");
		colDescr.width(30);
		colDescr.labelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof ISet) {
					ISet set = (ISet) element;
					int index = SessionDetailComposite.this.session
							.getAllGame().indexOf(set);
					return MessageFormat.format("#{0}", index + 1);
				}
				return super.getText(element);
			}
		});
		this.toolkit.createTableColumn(this.viewer, colDescr);
		result.add(colDescr);

		// Column players
		colDescr = new ColumnDescriptor("Players");
		colDescr.width(100);
		colDescr.labelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof ISet) {
					ISet set = (ISet) element;
					return SessionDetailComposite.this.getSetPlayers(set);
				}
				return super.getText(element);
			}
		});
		this.toolkit.createTableColumn(this.viewer, colDescr);
		result.add(colDescr);

		// Column Winner
		colDescr = new ColumnDescriptor("Winner");
		colDescr.width(60);
		colDescr.labelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof ISet) {
					ISet set = (ISet) element;
					return SessionDetailComposite.this.getSetWinner(set);
				}
				return super.getText(element);
			}
		});
		this.toolkit.createTableColumn(this.viewer, colDescr);
		result.add(colDescr);

		// Column nb darts
		colDescr = new ColumnDescriptor("Result");
		colDescr.width(45);
		colDescr.labelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof ISet) {
					ISet set = (ISet) element;
					return SessionDetailComposite.this.getSetResult(set);
				}
				return super.getText(element);
			}
		});
		this.toolkit.createTableColumn(this.viewer, colDescr);
		result.add(colDescr);

		return result;
	}

	/**
	 * Gets the game players.
	 *
	 * @param set the game
	 * @return the game players
	 */
	protected String getSetPlayers(ISet set) {
		StringBuilder sb = new StringBuilder();
		boolean isFist = true;
		for (IPlayer player : set.getGameDefinition().getPlayers()) {
			if (isFist) {
				isFist = false;
			} else {
				sb.append(", ");
			}
			sb.append(player.getName());
		}
		return sb.toString();

	}

	/**
	 * Gets the sets the winner.
	 *
	 * @param set the set
	 * @return the sets the winner
	 */
	protected String getSetWinner(ISet set) {
		String result;
		IPlayer winner = set.getWinner();
		if (winner != null) {
			result = winner.getName();
		} else {
			result = "-";
		}
		return result;
	}

	/**
	 * Gets the sets the result.
	 *
	 * @param set the game
	 * @return the sets the result
	 */
	protected String getSetResult(ISet set) {
		StringBuilder sb = new StringBuilder();
		boolean isFist = true;
		for (IPlayer player : set.getGameDefinition().getPlayers()) {
			if (isFist) {
				isFist = false;
			} else {
				sb.append(", ");
			}
			sb.append(set.getWinningGames(player));
		}
		return sb.toString();

	}

}
