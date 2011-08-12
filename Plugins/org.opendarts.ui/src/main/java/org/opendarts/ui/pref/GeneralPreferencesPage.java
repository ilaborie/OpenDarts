package org.opendarts.ui.pref;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.opendarts.ui.OpenDartsUiPlugin;

/**
 * The Class GeneralPreferencesPage.
 */
public class GeneralPreferencesPage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage, IGeneralPrefs {

	/**
	 * Instantiates a new general preferences.
	 */
	public GeneralPreferencesPage() {
		super(GRID);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
		this.setPreferenceStore(OpenDartsUiPlugin.getOpenDartsPreference());
		this.setDescription("OpenDarts general preferences");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	protected void createFieldEditors() {
		Composite parent = this.getFieldEditorParent();
		FieldEditor editor;

		// FullScreen
		editor = new BooleanFieldEditor(STARTING_FULLSCREN,
				"Auto Starting in FullScreen", parent);
		this.addField(editor);
	}

}
