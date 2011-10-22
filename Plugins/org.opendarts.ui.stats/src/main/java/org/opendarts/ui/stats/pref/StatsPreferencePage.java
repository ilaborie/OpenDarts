package org.opendarts.ui.stats.pref;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.opendarts.ui.stats.OpenDartsStatsUiPlugin;

/**
 * The Class StatsPreferencePage.
 */
public class StatsPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage, IStatsPrefs {

	/**
	 * Instantiates a new stats preference page.
	 */
	public StatsPreferencePage() {
		super(GRID);
	}

	/* (non-Javadoc)
	* @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	*/
	@Override
	public void init(IWorkbench workbench) {
		this.setPreferenceStore(OpenDartsStatsUiPlugin.getOpenDartsStats());
		this.setDescription("Statistics preferences");
	}

	/* (non-Javadoc)
	* @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	*/
	@Override
	protected void createFieldEditors() {
		Composite parent = this.getFieldEditorParent();
		FieldEditor editor;

		// Store
		editor = new BooleanFieldEditor(STORE_STATISTICS, "Store Statistics",
				parent);
		editor.setEnabled(false, parent);
		this.addField(editor);

		// Broken Dart
		editor = new BooleanFieldEditor(STATS_BROKEN,
				"Use broken darts for statistics", parent);
		this.addField(editor);
		
		// Color even
		editor = new ColorFieldEditor(STATS_COLOR_EVEN, "Color Even", parent);
		this.addField(editor);

		// Color odd
		editor = new ColorFieldEditor(STATS_COLOR_ODD, "Color Odd", parent);
		this.addField(editor);
	}

}
