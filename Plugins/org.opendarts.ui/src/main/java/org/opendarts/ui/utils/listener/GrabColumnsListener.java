package org.opendarts.ui.utils.listener;

import java.util.List;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.opendarts.ui.utils.ColumnDescriptor;

/**
 * Fill table with columns
 */
public class GrabColumnsListener extends ControlAdapter {

	/** The columns. */
	private final List<ColumnDescriptor> columns;

	/** The viewer. */
	private final ColumnViewer viewer;

	/**
	 * Instantiates a new grab columns listener.
	 *
	 * @param viewer the viewer
	 * @param columns the columns
	 */
	public GrabColumnsListener(ColumnViewer viewer,
			List<ColumnDescriptor> columns) {
		super();
		this.columns = columns;
		this.viewer = viewer;
	}

	/**
	 * Pack.
	 */
	protected void pack() {
		ViewerColumn column;
		for (ColumnDescriptor colDescr : this.columns) {
			column = colDescr.getColumn();
			if (column != null) {
				if (column instanceof TableViewerColumn) {
					TableViewerColumn tableColumn = (TableViewerColumn) column;
					tableColumn.getColumn().pack();
				} else if (column instanceof TreeViewerColumn) {
					TreeViewerColumn treeColumn = (TreeViewerColumn) column;
					treeColumn.getColumn().pack();
				}
			}
		}
	}

	/**
	 * Grab.
	 */
	protected void grab() {
		// grab
		if (this.viewer instanceof TableViewer) {
			this.grabTable((TableViewer) this.viewer);
		} else if (this.viewer instanceof TreeViewer) {
			this.grabTree((TreeViewer) this.viewer);
		}
	}

	/**
	 * Grab table.
	 *
	 * @param tableViewer the table viewer
	 */
	private void grabTable(TableViewer tableViewer) {
		Table table = tableViewer.getTable();
		Composite comp = table.getParent();

		Rectangle area = comp.getClientArea();
		Point size = table.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		ScrollBar vBar = table.getVerticalBar();
		int width = area.width - table.computeTrim(0, 0, 0, 0).width
				- vBar.getSize().x;
		if (size.y > (area.height + table.getHeaderHeight())) {
			Point vBarSize = vBar.getSize();
			width -= vBarSize.x;
		}
		Point oldSize = table.getSize();
		if (oldSize.x > area.width) {
			// table is getting smaller so make the columns
			// smaller first and then resize the table to
			// match the client area width
			this.setColumnsWidth(width);
			table.setSize(area.width, area.height);
		} else {
			// table is getting bigger so make the table
			// bigger first and then make the columns wider
			// to match the client area width
			table.setSize(area.width, area.height);
			this.setColumnsWidth(width);
		}

	}

	/**
	 * Sets the columns width.
	 *
	 * @param width the new columns width
	 */
	private void setColumnsWidth(int width) {
		int totalDefaultWidth = this.getTotalDefaultWidth();

		double ratio;
		int colWidth;
		double dWidth;
		ViewerColumn column;
		for (ColumnDescriptor colDescr : this.columns) {
			ratio = ((double) colDescr.getDefaultWidth())
					/ ((double) totalDefaultWidth);
			dWidth = width * ratio;
			colWidth = (int) Math.floor(dWidth);

			column = colDescr.getColumn();
			this.setWidth(column, colWidth);
		}
	}

	/**
	 * Grab tree.
	 *
	 * @param TreeViewer the tree viewer
	 */
	private void grabTree(TreeViewer TreeViewer) {
		Tree tree = TreeViewer.getTree();
		Composite comp = tree.getParent();

		Rectangle area = comp.getClientArea();
		Point size = tree.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		ScrollBar vBar = tree.getVerticalBar();
		int width = area.width - tree.computeTrim(0, 0, 0, 0).width
				- vBar.getSize().x;
		if (size.y > (area.height + tree.getHeaderHeight())) {
			Point vBarSize = vBar.getSize();
			width -= vBarSize.x;
		}
		Point oldSize = tree.getSize();
		if (oldSize.x > area.width) {
			this.setColumnsWidth(width);
			tree.setSize(area.width, oldSize.y);
		} else {
			tree.setSize(area.width, oldSize.y);
			this.setColumnsWidth(width);
		}

	}

	/**
	 * Sets the width.
	 *
	 * @param colDescr the column description
	 * @param width the width
	 */
	private void setWidth(ViewerColumn column, int width) {
		if (column != null) {
			if (column instanceof TableViewerColumn) {
				TableViewerColumn tableColumn = (TableViewerColumn) column;
				tableColumn.getColumn().setWidth(width);
			} else if (column instanceof TreeViewerColumn) {
				TreeViewerColumn treeColumn = (TreeViewerColumn) column;
				treeColumn.getColumn().setWidth(width);
			}
		}
	}

	/**
	 * Gets the total default width.
	 *
	 * @return the total default width
	 */
	private int getTotalDefaultWidth() {
		int result = 0;
		for (ColumnDescriptor colDescr : this.columns) {
			result += colDescr.getDefaultWidth();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.ControlListener#controlResized(org.eclipse.swt.events.ControlEvent)
	 */
	@Override
	public synchronized void controlResized(ControlEvent e) {
		this.grab();
	}

}
