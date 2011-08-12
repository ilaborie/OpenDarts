
package org.opendarts.ui.pref;

import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.opendarts.ui.OpenDartsUiPlugin;

/**
 * The Class GeneralPreferencesPage.
 */
public class GeneralColorPreferencesPage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage, IGeneralPrefs {

	/**
	 * Instantiates a new general preferences.
	 */
	public GeneralColorPreferencesPage() {
		super(GRID);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
		this.setPreferenceStore(OpenDartsUiPlugin.getOpenDartsPreference());
		this.setDescription("Color preferences");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	protected void createFieldEditors() {
		Composite parent = this.getFieldEditorParent();
		FieldEditor editor;

		// Color
		editor = new ColorFieldEditor(COLOR_ACTIVE, "Active Color", parent);
		this.addField(editor);

		editor = new ColorFieldEditor(COLOR_INACTIVE, "Inactive Color", parent);
		this.addField(editor);

		editor = new ColorFieldEditor(COLOR_WINNING, "Winning Color", parent);
		this.addField(editor);

		editor = new ColorFieldEditor(COLOR_BROKEN, "Broken Color", parent);
		this.addField(editor);
	}

}
