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
public class GameStatsPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage, IX01Prefs {

	/**
	 * Instantiates a new stats preference page.
	 */
	public GameStatsPreferencePage() {
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
		List<String> stats = Arrays.asList(StatsX01Service.GAME_AVG_DART,
				StatsX01Service.GAME_AVG_3_DARTS, StatsX01Service.GAME_180s,
				StatsX01Service.GAME_140, StatsX01Service.GAME_TONS,
				StatsX01Service.GAME_60, StatsX01Service.GAME_60_PLUS,
				StatsX01Service.GAME_TONS_PLUS);

		// Editor
		editor = new StatsListEditor(GAME_STATS, "Statistics entries", parent,
				stats);
		this.addField(editor);
	}

}
