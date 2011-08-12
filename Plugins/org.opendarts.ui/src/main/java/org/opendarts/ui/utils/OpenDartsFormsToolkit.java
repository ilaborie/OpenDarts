package org.opendarts.ui.utils;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.opendarts.ui.OpenDartsUiPlugin;
import org.opendarts.ui.pref.IGeneralPrefs;

/**
 * The Class OpenDartsFormsToolkit.
 */
public class OpenDartsFormsToolkit extends FormToolkit implements IGeneralPrefs {

	/** The form colors. */
	private static FormColors formColors;

	/** The toolkit. */
	private static OpenDartsFormsToolkit toolkit;

	/** The font registry. */
	private static FontRegistry fontRegistry = JFaceResources.getFontRegistry();

	/** The display. */
	private static Display display;

	/**
	 * Instantiates a new open darts forms toolkit.
	 *
	 * @param colors the colors
	 */
	private OpenDartsFormsToolkit(FormColors colors) {
		super(colors);
		fontRegistry
				.put(FONT_BOLD,
						fontRegistry.getBold(JFaceResources.DEFAULT_FONT)
								.getFontData());
		List<String> fonts = Arrays.asList(FONT_SCORE_INPUT, FONT_SCORE_LEFT,
				FONT_SCORE_SHEET, FONT_SCORE_SHEET_LEFT, FONT_STATS,
				FONT_STATS_LABEL);

		for (String key : fonts) {
			FontData[] fontDataArray = PreferenceConverter.getFontDataArray(
					OpenDartsUiPlugin.getOpenDartsPreference(), key);
			fontRegistry.put(key, fontDataArray);
		}
	}

	/**
	 * Gets the form colors.
	 *
	 * @param display the display
	 * @return the form colors
	 */
	public static FormColors getFormColors(Display display) {
		if (formColors == null) {
			IPreferenceStore store = OpenDartsUiPlugin.getOpenDartsPreference();

			formColors = new FormColors(display);

			List<String> colors = Arrays.asList(COLOR_ACTIVE, COLOR_INACTIVE,
					COLOR_WINNING, COLOR_BROKEN);

			for (String key : colors) {
				formColors.createColor(key,
						PreferenceConverter.getColor(store, key));
			}

			formColors.markShared();
		}
		return formColors;
	}

	/**
	 * Gets the font.
	 *
	 * @param key the key
	 * @return the font
	 */
	public static Font getFont(String key) {
		return fontRegistry.get(key);
	}

	/**
	 * Gets the toolkit.
	 *
	 * @return the toolkit
	 */
	public static OpenDartsFormsToolkit getToolkit() {
		if (toolkit == null) {
			display = Display.getDefault();
			toolkit = new OpenDartsFormsToolkit(getFormColors(display));
		}
		return toolkit;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.widgets.FormToolkit#createLabel(org.eclipse.swt.widgets.Composite, java.lang.String, int)
	 */
	@Override
	public Label createLabel(Composite parent, String text, int style) {
		Label label = super.createLabel(parent, text, style);
		label.setForeground(this.getColors().getColor(IFormColors.TITLE));
		return label;
	}

	/**
	 * Creates the dummy label.
	 *
	 * @param parent the parent
	 * @param text the text
	 * @return the label
	 */
	public Label createDummyLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.WRAP);
		label.setText(text);
		label.setFont(fontRegistry.getItalic(JFaceResources.DIALOG_FONT));
		label.setForeground(Display.getDefault().getSystemColor(
				SWT.COLOR_DARK_CYAN));
		return label;
	}

	/**
	 * Builds the column.
	 *
	 * @param viewer the viewer
	 * @param descr the column descriptor
	 * @return the table viewer column
	 */
	public TableViewerColumn createTableColumn(TableViewer viewer,
			ColumnDescriptor descr) {
		// XXX workaround to fix a weird issue in Window platform
		// @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=43910
		if (SWT.getPlatform().equals("win32")) {
			Table table = viewer.getTable();
			if (table.getColumnCount() == 0) {
				TableColumn emptyCol = new TableColumn(table, SWT.LEFT, 0);
				emptyCol.setText("First Column");
				emptyCol.setWidth(0);
				emptyCol.setResizable(false);
			}
		}

		TableViewerColumn column = new TableViewerColumn(viewer,
				descr.getStyle());
		TableColumn tableColumn = column.getColumn();
		tableColumn.setText(descr.getLabel());
		tableColumn.setWidth(descr.getDefaultWidth());
		tableColumn.setResizable(descr.isResizable());

		column.setEditingSupport(descr.getEditingSupport());
		column.setLabelProvider(descr.getLabelProvider());

		descr.setColumn(column);
		return column;
	}

	/**
	 * Creates the tree column.
	 *
	 * @param viewer the viewer
	 * @param descr the descr
	 * @return the tree viewer column
	 */
	public TreeViewerColumn createTreeColumn(TreeViewer viewer,
			ColumnDescriptor descr) {
		TreeViewerColumn column = new TreeViewerColumn(viewer, descr.getStyle());
		TreeColumn treeColumn = column.getColumn();
		treeColumn.setText(descr.getLabel());
		treeColumn.setWidth(descr.getDefaultWidth());
		treeColumn.setResizable(descr.isResizable());

		column.setEditingSupport(descr.getEditingSupport());
		column.setLabelProvider(descr.getLabelProvider());

		descr.setColumn(column);
		return column;
	}
}
