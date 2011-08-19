package org.opendarts.ui.pref;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FontFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.opendarts.ui.OpenDartsUiPlugin;

/**
 * The Class GeneralPreferencesPage.
 */
public class GeneralFontPreferencesPage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage, IGeneralPrefs {

	/**
	 * Instantiates a new general preferences.
	 */
	public GeneralFontPreferencesPage() {
		super(GRID);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
		this.setPreferenceStore(OpenDartsUiPlugin.getOpenDartsPreference());
		this.setDescription("Font preferences");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	protected void createFieldEditors() {
		Composite parent = this.getFieldEditorParent();
		FieldEditor editor;

		// Fonts
		editor = new FontFieldEditor(FONT_SCORE_INPUT, "Input Score Font",
				parent);
		this.addField(editor);

		editor = new FontFieldEditor(FONT_SCORE_LEFT, "Score Left Font", parent);
		this.addField(editor);

		editor = new FontFieldEditor(FONT_SCORE_SHEET, "Score Sheet Font",
				parent);
		this.addField(editor);

		editor = new FontFieldEditor(FONT_SCORE_SHEET_LEFT, "Score Sheet Left",
				parent);
		this.addField(editor);

		editor = new FontFieldEditor(FONT_STATS, "Stats Font", parent);
		this.addField(editor);

		editor = new FontFieldEditor(FONT_STATS_LABEL, "Stats Label Font",
				parent);
		this.addField(editor);
	}

}
