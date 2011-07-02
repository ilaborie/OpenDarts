package org.opendarts.prototype.ui.utils;

import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class OpenDartsFormsToolkit extends FormToolkit {

	/** The form colors. */
	private static FormColors formColors;

	/** The toolkit. */
	private static OpenDartsFormsToolkit toolkit;

	/** The font registry. */
	private static FontRegistry fontRegistry = JFaceResources.getFontRegistry();
	/**
	 * Instantiates a new open darts forms toolkit.
	 *
	 * @param colors the colors
	 */
	private OpenDartsFormsToolkit(FormColors colors) {
		super(colors);
	}

	/**
	 * Gets the form colors.
	 *
	 * @param display the display
	 * @return the form colors
	 */
	public static FormColors getFormColors(Display display) {
		if (formColors == null) {
			formColors = new FormColors(display);
			formColors.markShared();
		}
		return formColors;
	}

	/**
	 * Gets the toolkit.
	 *
	 * @return the toolkit
	 */
	public static OpenDartsFormsToolkit getToolkit() {
		if (toolkit == null) {
			toolkit = new OpenDartsFormsToolkit(
					getFormColors(Display.getDefault()));
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
		label.setFont(JFaceResources.getFontRegistry().getItalic(
				JFaceResources.DIALOG_FONT));
		label.setForeground(Display.getDefault().getSystemColor(
				SWT.COLOR_DARK_CYAN));
		return label;
	}

	/**
	 * Builds the column.
	 *
	 * @param title the title
	 * @param viewer the viewer
	 * @param width the width
	 * @param style the style
	 * @param labelProvider the label provider
	 * @return the table viewer column
	 */
	public TableViewerColumn createTableColumn(String title,
			TableViewer viewer, int width, int style,
			ColumnLabelProvider labelProvider) {
		TableViewerColumn column = new TableViewerColumn(viewer, style);
		column.getColumn().setText(title);
		column.getColumn().setWidth(width);
		column.getColumn().setResizable(false);
		column.setLabelProvider(labelProvider);
		return column;
	}

	/**
	 * Creates the table column.
	 *
	 * @param title the title
	 * @param viewer the viewer
	 * @param width the width
	 * @param style the style
	 * @param labelProvider the label provider
	 * @param editingSupport the editing support
	 * @return the table viewer column
	 */
	public TableViewerColumn createTableColumn(String title,
			TableViewer viewer, int width, int style,
			ColumnLabelProvider labelProvider, EditingSupport editingSupport) {
		TableViewerColumn column = this.createTableColumn(title, viewer, width,
				style, labelProvider);
		column.setEditingSupport(editingSupport);
		return column;
	}

	/**
	 * Gets the score font.
	 *
	 * @return the score font
	 */
	public Font getScoreFont() {
		Font font = fontRegistry.get("scoreFont");
		if (font == null) {
			FontData[] fd = JFaceResources.getDefaultFont().getFontData();
			for (FontData fdd : fd) {
				fdd.setHeight(48);
			}
			font = new Font(Display.getDefault(), fd);
			fontRegistry.put("scoreFont", font.getFontData());
		}
		return font;
	}


}
