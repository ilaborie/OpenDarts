package org.opendarts.ui.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.opendarts.ui.OpenDartsUiPlugin;

/**
 * The Class ListViewerEditor.
 *
 * @param <T> the generic type
 */
public abstract class ListViewerEditor<T> extends FieldEditor implements
		ISelectionChangedListener {

	/**
	 * The list widget; <code>null</code> if none
	 * (before creation or after disposal).
	 */
	private TableViewer tableViewer;

	/**
	 * The button box containing the Add, Remove, Up, and Down buttons;
	 * <code>null</code> if none (before creation or after disposal).
	 */
	private Composite buttonBox;

	/**
	 * The Add button.
	 */
	private Button addButton;

	/**
	 * The Remove button.
	 */
	private Button removeButton;

	/**
	 * The Up button.
	 */
	private Button upButton;

	/**
	 * The Down button.
	 */
	private Button downButton;

	/**
	 * The selection listener.
	 */
	private SelectionListener selectionListener;

	/** The selected. */
	private T selected;

	/** The list. */
	private List<T> list;

	/**
	 * Creates a new list field editor.
	 */
	protected ListViewerEditor() {
		super();
	}

	/**
	 * Creates a list field editor.
	 * 
	 * @param name the name of the preference this field editor works on
	 * @param labelText the label text of the field editor
	 * @param parent the parent of the field editor's control
	 */
	protected ListViewerEditor(String name, String labelText, Composite parent) {
		this.init(name, labelText);
		this.createControl(parent);
	}

	/**
	 * Notifies that the Add button has been pressed.
	 */
	private void addPressed() {
		this.setPresentsDefaultValue(false);
		T elt = this.getNewInputObject();

		if (elt != null) {
			this.list.add(elt);
			this.tableViewer.refresh();
			this.tableViewer.setSelection(new StructuredSelection(elt));
			this.selectionChanged();
		}
	}

	/* (non-Javadoc)
	 * Method declared on FieldEditor.
	 */
	@Override
	protected void adjustForNumColumns(int numColumns) {
		Control control = this.getLabelControl();
		((GridData) control.getLayoutData()).horizontalSpan = numColumns;
		((GridData) this.tableViewer.getControl().getLayoutData()).horizontalSpan = numColumns - 1;
	}

	/**
	 * Creates the Add, Remove, Up, and Down button in the given button box.
	 *
	 * @param box the box for the buttons
	 */
	private void createButtons(Composite box) {
		this.addButton = this.createPushButton(box, ISharedImages.IMG_ADD);
		this.removeButton = this
				.createPushButton(box, ISharedImages.IMG_REMOVE);
		this.upButton = this.createPushButton(box, ISharedImages.IMG_UP);
		this.downButton = this.createPushButton(box, ISharedImages.IMG_DOWN);
	}

	/**
	 * Combines the given list of items into a single string.
	 * This method is the converse of <code>parseString</code>. 
	 * <p>
	 * Subclasses must implement this method.
	 * </p>
	 *
	 * @param objects the list of items
	 * @return the combined string
	 * @see #parseString
	 */
	protected abstract String createList(List<T> objects);

	/**
	 * Helper method to create a push button.
	 *
	 * @param parent the parent control
	 * @param img the img
	 * @return Button
	 */
	private Button createPushButton(Composite parent, String img) {
		Button button = new Button(parent, SWT.PUSH);
		button.setFont(parent.getFont());
		button.setImage(OpenDartsUiPlugin.getImage(img));
		GridDataFactory.fillDefaults().applyTo(button);
		button.addSelectionListener(this.getSelectionListener());
		return button;
	}

	/**
	 * Creates a selection listener.
	 */
	public void createSelectionListener() {
		this.selectionListener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				Widget widget = event.widget;
				if (widget == ListViewerEditor.this.addButton) {
					ListViewerEditor.this.addPressed();
				} else if (widget == ListViewerEditor.this.removeButton) {
					ListViewerEditor.this.removePressed();
				} else if (widget == ListViewerEditor.this.upButton) {
					ListViewerEditor.this.upPressed();
				} else if (widget == ListViewerEditor.this.downButton) {
					ListViewerEditor.this.downPressed();
				}
			}
		};
	}

	/* (non-Javadoc)
	 * Method declared on FieldEditor.
	 */
	@Override
	protected void doFillIntoGrid(Composite parent, int numColumns) {
		Control control = this.getLabelControl(parent);
		GridData gd = new GridData();
		gd.horizontalSpan = numColumns;
		control.setLayoutData(gd);

		this.tableViewer = this.getListControl(parent);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.verticalAlignment = GridData.FILL;
		gd.horizontalSpan = numColumns - 1;
		gd.grabExcessHorizontalSpace = true;
		this.tableViewer.getControl().setLayoutData(gd);

		this.buttonBox = this.getButtonBoxControl(parent);
		gd = new GridData();
		gd.verticalAlignment = GridData.BEGINNING;
		this.buttonBox.setLayoutData(gd);

		ColumnViewerToolTipSupport.enableFor(this.tableViewer);
	}

	/* (non-Javadoc)
	 * Method declared on FieldEditor.
	 */
	@Override
	protected void doLoad() {
		if (this.tableViewer != null) {
			String s = this.getPreferenceStore().getString(
					this.getPreferenceName());
			List<T> array = this.parseString(s);
			this.list = new ArrayList<T>(array);
			this.tableViewer.setInput(this.list);
		}
	}

	/* (non-Javadoc)
	 * Method declared on FieldEditor.
	 */
	@Override
	protected void doLoadDefault() {
		if (this.tableViewer != null) {
			this.tableViewer.setInput(Collections.emptyList());
			String s = this.getPreferenceStore().getDefaultString(
					this.getPreferenceName());
			List<T> array = this.parseString(s);

			this.list = new ArrayList<T>(array);

			this.tableViewer.setInput(this.list);
		}
	}

	/* (non-Javadoc)
	 * Method declared on FieldEditor.
	 */
	@Override
	protected void doStore() {
		String s = this.createList(this.list);
		if (s != null) {
			this.getPreferenceStore().setValue(this.getPreferenceName(), s);
		}
	}

	/**
	 * Notifies that the Down button has been pressed.
	 */
	private void downPressed() {
		this.swap(false);
	}

	/**
	 * Returns this field editor's button box containing the Add, Remove,
	 * Up, and Down button.
	 *
	 * @param parent the parent control
	 * @return the button box
	 */
	public Composite getButtonBoxControl(Composite parent) {
		if (this.buttonBox == null) {
			this.buttonBox = new Composite(parent, SWT.NULL);
			GridLayout layout = new GridLayout();
			layout.marginWidth = 0;
			this.buttonBox.setLayout(layout);
			this.createButtons(this.buttonBox);
			this.buttonBox.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent event) {
					ListViewerEditor.this.addButton = null;
					ListViewerEditor.this.removeButton = null;
					ListViewerEditor.this.upButton = null;
					ListViewerEditor.this.downButton = null;
					ListViewerEditor.this.buttonBox = null;
				}
			});

		} else {
			this.checkParent(this.buttonBox, parent);
		}

		this.selectionChanged();
		return this.buttonBox;
	}

	/**
	 * Returns this field editor's list control.
	 *
	 * @param parent the parent control
	 * @return the list control
	 */
	public TableViewer getListControl(Composite parent) {
		if (this.tableViewer == null) {
			this.tableViewer = new TableViewer(parent, SWT.BORDER | SWT.SINGLE
					| SWT.V_SCROLL | SWT.H_SCROLL);
			this.tableViewer.setContentProvider(new ArrayContentProvider());
			this.tableViewer.setLabelProvider(this.getLabelProvider());
			this.tableViewer.addSelectionChangedListener(this);

		} else {
			this.checkParent(this.tableViewer.getControl(), parent);
		}
		return this.tableViewer;
	}

	/**
	 * Gets the label provider.
	 *
	 * @return the label provider
	 */
	protected ColumnLabelProvider getLabelProvider() {
		return new ColumnLabelProvider();
	}

	/**
	 * Creates and returns a new item for the list.
	 * <p>
	 * Subclasses must implement this method.
	 * </p>
	 *
	 * @return a new item
	 */
	protected abstract T getNewInputObject();

	/* (non-Javadoc)
	 * Method declared on FieldEditor.
	 */
	@Override
	public int getNumberOfControls() {
		return 2;
	}

	/**
	 * Returns this field editor's selection listener.
	 * The listener is created if necessary.
	 *
	 * @return the selection listener
	 */
	private SelectionListener getSelectionListener() {
		if (this.selectionListener == null) {
			this.createSelectionListener();
		}
		return this.selectionListener;
	}

	/**
	 * Returns this field editor's shell.
	 * <p>
	 * This method is internal to the framework; subclassers should not call
	 * this method.
	 * </p>
	 *
	 * @return the shell
	 */
	protected Shell getShell() {
		if (this.addButton == null) {
			return null;
		}
		return this.addButton.getShell();
	}

	/**
	 * Splits the given string into a list of strings.
	 * This method is the converse of <code>createList</code>. 
	 * <p>
	 * Subclasses must implement this method.
	 * </p>
	 *
	 * @param stringList the string
	 * @return an array of <code>String</code>
	 * @see #createList
	 */
	protected abstract List<T> parseString(String stringList);

	/**
	 * Notifies that the Remove button has been pressed.
	 */
	private void removePressed() {
		this.setPresentsDefaultValue(false);

		boolean remove = this.list.remove(this.selected);
		if (remove) {
			this.tableViewer.remove(this.selected);
			this.tableViewer.setSelection(StructuredSelection.EMPTY);
		}
	}

	/**
	 * Invoked when the selection in the list has changed.
	 * 
	 * <p>
	 * The default implementation of this method utilizes the selection index
	 * and the size of the list to toggle the enablement of the up, down and
	 * remove buttons.
	 * </p>
	 * 
	 * <p>
	 * Sublcasses may override.
	 * </p>
	 * 
	 * @since 3.5
	 */
	protected void selectionChanged() {
		if (this.list == null) {
			this.removeButton.setEnabled(false);
			this.upButton.setEnabled(false);
			this.downButton.setEnabled(false);
		} else {
			this.removeButton.setEnabled(this.selected != null);

			int index = this.list.indexOf(this.selected);
			int size = this.list.size();

			this.upButton.setEnabled((size > 1) && (index > 0));
			this.downButton.setEnabled((size > 1) && (index >= 0)
					&& (index < (size - 1)));
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection sel = (IStructuredSelection) event.getSelection();
		if (sel.isEmpty()) {
			this.selected = null;
		} else {
			this.selected = (T) sel.getFirstElement();
		}
		this.selectionChanged();
	}

	/* (non-Javadoc)
	 * Method declared on FieldEditor.
	 */
	@Override
	public void setFocus() {
		if (this.tableViewer != null) {
			this.tableViewer.getControl().setFocus();
		}
	}

	/**
	 * Moves the currently selected item up or down.
	 *
	 * @param up <code>true</code> if the item should move up,
	 *  and <code>false</code> if it should move down
	 */
	private void swap(boolean up) {
		this.setPresentsDefaultValue(false);

		int index = this.list.indexOf(this.selected);
		Collections.swap(this.list, index, index - 1);
		this.tableViewer.setInput(this.list);
		this.selectionChanged();
	}

	/**
	 * Notifies that the Up button has been pressed.
	 */
	private void upPressed() {
		this.swap(true);
	}

	/*
	 * @see FieldEditor.setEnabled(boolean,Composite).
	 */
	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditor#setEnabled(boolean, org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void setEnabled(boolean enabled, Composite parent) {
		super.setEnabled(enabled, parent);
		this.getListControl(parent).getControl().setEnabled(enabled);
		this.addButton.setEnabled(enabled);
		this.removeButton.setEnabled(enabled);
		this.upButton.setEnabled(enabled);
		this.downButton.setEnabled(enabled);
	}

	/**
	 * Return the Add button.  
	 * 
	 * @return the button
	 * @since 3.5
	 */
	protected Button getAddButton() {
		return this.addButton;
	}

	/**
	 * Return the Remove button.  
	 * 
	 * @return the button
	 * @since 3.5
	 */
	protected Button getRemoveButton() {
		return this.removeButton;
	}

	/**
	 * Return the Up button.  
	 * 
	 * @return the button
	 * @since 3.5
	 */
	protected Button getUpButton() {
		return this.upButton;
	}

	/**
	 * Return the Down button.  
	 * 
	 * @return the button
	 * @since 3.5
	 */
	protected Button getDownButton() {
		return this.downButton;
	}

	/**
	 * Return the List.
	 * 
	 * @return the list
	 */
	protected TableViewer getTable() {
		return this.tableViewer;
	}
}