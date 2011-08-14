package org.opendarts.ui.x01.pref;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.opendarts.ui.x01.X01UiPlugin;

/**
 * The Class X01PreferencePage.
 */
public class X01ColorsPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage, IX01Prefs {
	
	/**
	 * Instantiates a new stats preference page.
	 */
	public X01ColorsPreferencePage() {
		super(GRID);
	}

	/* (non-Javadoc)
	* @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	*/
	@Override
	public void init(IWorkbench workbench) {
		this.setPreferenceStore(X01UiPlugin.getX01Preferences());
		this.setDescription("x01 session statistics preferences");
	}

	/* (non-Javadoc)
	* @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	*/
	@Override
	protected void createFieldEditors() {
		Composite parent = this.getFieldEditorParent();
		FieldEditor editor;

		// Normal
		editor = new ColorFieldEditor(COLOR_NORMAL,
				"Base score color", parent);
		this.addField(editor);
		
		// Normal Gradient
		editor = new BooleanFieldEditor(COLOR_NORMAL_GRADIENT,
				"Gradiant for 0-59", parent);
		this.addField(editor);

		// 60
		editor = new ColorFieldEditor(COLOR_60,
				"60 color", parent);
		this.addField(editor);
		
		// 60 Gradient
		editor = new BooleanFieldEditor(COLOR_60_GRADIENT,
				"Gradiant for 60-99", parent);
		this.addField(editor);

		// 100
		editor = new ColorFieldEditor(COLOR_100,
				"100 color", parent);
		this.addField(editor);
		
		// 100 Gradient
		editor = new BooleanFieldEditor(COLOR_100_GRADIENT,
				"Gradiant for 100-179", parent);
		this.addField(editor);

		// 60
		editor = new ColorFieldEditor(COLOR_180,
				"180 color", parent);
		this.addField(editor);
	}

}
