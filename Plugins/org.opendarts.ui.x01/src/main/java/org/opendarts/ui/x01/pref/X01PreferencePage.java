package org.opendarts.ui.x01.pref;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.opendarts.ui.x01.X01UiPlugin;

/**
 * The Class X01PreferencePage.
 */
public class X01PreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage, IX01Prefs {
	
	/**
	 * Instantiates a new stats preference page.
	 */
	public X01PreferencePage() {
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

		// Show score left
		editor = new BooleanFieldEditor(SHOW_SCORE_LEFT,
				"Show Left Score Panel", parent);
		this.addField(editor);

		// Switch user position
		editor = new BooleanFieldEditor(SWITCH_USER_POSITION,
				"Switch User Position", parent);
		this.addField(editor);
		this.addField(editor);
	}

}
