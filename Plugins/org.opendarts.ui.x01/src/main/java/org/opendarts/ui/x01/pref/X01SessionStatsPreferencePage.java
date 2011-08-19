package org.opendarts.ui.x01.pref;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.opendarts.core.x01.service.StatsX01Service;
import org.opendarts.ui.x01.X01UiPlugin;

/**
 * The Class X01PreferencePage.
 */
public class X01SessionStatsPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage, IX01Prefs {

	/**
	 * Instantiates a new stats preference page.
	 */
	public X01SessionStatsPreferencePage() {
		super(GRID);
	}

	/* (non-Javadoc)
	* @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	*/
	@Override
	public void init(IWorkbench workbench) {
		this.setPreferenceStore(X01UiPlugin.getX01Preferences());
		this.setDescription("x01 preferences");
	}

	/* (non-Javadoc)
	* @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	*/
	@Override
	protected void createFieldEditors() {
		Composite parent = this.getFieldEditorParent();
		FieldEditor editor;

		// List of available stats
		List<String> stats = Arrays.asList(StatsX01Service.SESSION_AVG_DART,
				StatsX01Service.SESSION_AVG_3_DARTS,
				StatsX01Service.SESSION_180s, StatsX01Service.SESSION_140,
				StatsX01Service.SESSION_TONS, StatsX01Service.SESSION_60,
				StatsX01Service.SESSION_60_PLUS,
				StatsX01Service.SESSION_TONS_PLUS,
				StatsX01Service.SESSION_BEST_LEG,
				StatsX01Service.SESSION_AVG_LEG,
				StatsX01Service.SESSION_BEST_OUT,
				StatsX01Service.SESSION_COUNT_DARTS,
				StatsX01Service.SESSION_TOTAL_SCORE,
				StatsX01Service.SESSION_OUT_OVER_100);

		// Editor
		editor = new StatsListEditor(SESSION_STATS, "Statistics entries",
				parent, stats);
		this.addField(editor);
	}

}
