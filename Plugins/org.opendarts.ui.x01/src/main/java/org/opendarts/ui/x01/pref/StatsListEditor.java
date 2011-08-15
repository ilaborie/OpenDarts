package org.opendarts.ui.x01.pref;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.opendarts.core.x01.OpenDartsX01Bundle;
import org.opendarts.core.x01.service.StatsX01Service;
import org.opendarts.ui.stats.service.IStatsUiProvider;
import org.opendarts.ui.utils.ListViewerEditor;
import org.opendarts.ui.x01.X01UiPlugin;

/**
 * The Class StatsListEditor.
 */
public class StatsListEditor extends ListViewerEditor<String> {

	/** The available stats. */
	private final List<String> availableStats;

	/** The parent shell. */
	private final Shell parentShell;

	/**
	 * Instantiates a new stats list editor.
	 *
	 * @param key the key
	 * @param labelText the label text
	 * @param parent the parent
	 * @param availableStats the available stats
	 */
	public StatsListEditor(String key, String labelText, Composite parent,
			List<String> availableStats) {
			super(key, labelText, parent);
		this.parentShell = parent.getShell();
		this.availableStats = new ArrayList<String>(availableStats);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.utils.ListViewerEditor#createList(java.util.List)
	 */
	@Override
	protected String createList(List<String> items) {
		return PreferencesConverterUtils.getListAsString(items);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.utils.ListViewerEditor#getLabelProvider()
	 */
	@Override
	protected ColumnLabelProvider getLabelProvider() {
		StatsX01Service statsService = OpenDartsX01Bundle.getStatsX01Service();
		return X01UiPlugin.getService(IStatsUiProvider.class)
				.getStatsUiService(statsService).getStatsLabelProvider();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.utils.ListViewerEditor#getNewInputObject()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected String getNewInputObject() {
		String result = null;

		List<String> sel = (List<String>) this.getTable().getInput();

		List<String> statsEntries = new ArrayList<String>(this.availableStats);
		statsEntries.removeAll(sel);

		StatsSelectionDialog dialog = new StatsSelectionDialog(
				this.parentShell, statsEntries, this.getLabelProvider());
		if (dialog.open() == Window.OK) {
			result = dialog.getStats();
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.utils.ListViewerEditor#parseString(java.lang.String)
	 */
	@Override
	protected List<String> parseString(String stringList) {
		List<String> result = PreferencesConverterUtils
				.getStringAsList(stringList);
		return result;
	}

}
