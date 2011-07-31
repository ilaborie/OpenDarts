package org.opendarts.prototype.ui.utils;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.swt.SWT;

/**
 * The Class ColumnDescriptor.
 */
public class ColumnDescriptor implements Cloneable {

	/** The label. */
	private String label;

	/** The default width. */
	private int defaultWidth;

	/** The style. */
	private int style;

	/** The label provider. */
	private ColumnLabelProvider labelProvider;

	/** The editing support. */
	private EditingSupport editingSupport;

	/** The resizable. */
	private boolean resizable;

	/** The column. */
	private ViewerColumn column;

	/**
	 * Instantiates a new column descriptor.
	 */
	public ColumnDescriptor(String label) {
		this(label, SWT.CENTER);

	}

	/**
	 * Instantiates a new column descriptor.
	 *
	 * @param label the label
	 * @param style the style
	 */
	public ColumnDescriptor(String label, int style) {
		super();
		this.label = label;
		this.style = style;
		this.defaultWidth = 80;
		this.labelProvider = new ColumnLabelProvider();
	}

	/**
	 * Set Label.
	 *
	 * @param label the label
	 * @return the column descriptor
	 */
	public ColumnDescriptor label(String label) {
		this.label = label;
		return this;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return this.copy();
	}

	/**
	 * Copy.
	 *
	 * @return the column descriptor
	 */
	private ColumnDescriptor copy() {
		ColumnDescriptor result = new ColumnDescriptor(this.label, this.style);
		result.width(this.defaultWidth).labelProvider(this.labelProvider)
				.editingSupport(this.editingSupport).reziable(this.resizable);
		return result;

	}

	/**
	 * Set Width.
	 *
	 * @param width the width
	 * @return the column descriptor
	 */
	public ColumnDescriptor width(int width) {
		this.defaultWidth = width;
		return this;
	}

	/**
	 * Set Style.
	 *
	 * @param style the style
	 * @return the column descriptor
	 */
	public ColumnDescriptor style(int style) {
		this.style = style;
		return this;
	}

	/**
	 * Set Label provider.
	 *
	 * @param labelProvider the label provider
	 * @return the column descriptor
	 */
	public ColumnDescriptor labelProvider(ColumnLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
		return this;
	}

	/**
	 * Editing support.
	 *
	 * @param editingSupport the editing support
	 * @return the column descriptor
	 */
	public ColumnDescriptor editingSupport(EditingSupport editingSupport) {
		this.editingSupport = editingSupport;
		return this;
	}

	/**
	 * Reziable.
	 *
	 * @param resizable the resizable
	 * @return the column descriptor
	 */
	public ColumnDescriptor reziable(boolean resizable) {
		this.resizable = resizable;
		return this;
	}

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * Gets the default width.
	 *
	 * @return the default width
	 */
	public int getDefaultWidth() {
		return this.defaultWidth;
	}

	/**
	 * Gets the style.
	 *
	 * @return the style
	 */
	public int getStyle() {
		return this.style;
	}

	/**
	 * Gets the label provider.
	 *
	 * @return the label provider
	 */
	public ColumnLabelProvider getLabelProvider() {
		return this.labelProvider;
	}

	/**
	 * Gets the editing support.
	 *
	 * @return the editing support
	 */
	public EditingSupport getEditingSupport() {
		return this.editingSupport;
	}

	/**
	 * Checks if is resizable.
	 *
	 * @return true, if is resizable
	 */
	public boolean isResizable() {
		return this.resizable;
	}

	/**
	 * Gets the column.
	 *
	 * @return the column
	 */
	public ViewerColumn getColumn() {
		return this.column;
	}

	/**
	 * Sets the column.
	 *
	 * @param column the new column
	 */
	public void setColumn(ViewerColumn column) {
		this.column = column;
	}

}
