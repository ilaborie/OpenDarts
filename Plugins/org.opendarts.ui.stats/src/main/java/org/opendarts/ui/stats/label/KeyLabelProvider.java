package org.opendarts.ui.stats.label;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.opendarts.core.stats.model.IElementStats.IEntry;
import org.opendarts.core.stats.service.IStatsProvider;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.ui.stats.OpenDartsStatsUiPlugin;
import org.opendarts.ui.stats.service.IStatsUiProvider;
import org.opendarts.ui.stats.service.IStatsUiService;

/**
 * The Class KeyLabelProvider.
 */
public class KeyLabelProvider extends ColumnLabelProvider {
	/** The stats ui provider. */
	private final IStatsUiProvider statsUiProvider;

	/** The stats provider. */
	private final IStatsProvider statsProvider;

	/**
	 * Instantiates a new key label provider.
	 */
	public KeyLabelProvider() {
		super();
		this.statsProvider = OpenDartsStatsUiPlugin
				.getService(IStatsProvider.class);
		this.statsUiProvider = OpenDartsStatsUiPlugin
				.getService(IStatsUiProvider.class);
	}

	/**
	 * Gets the label provider.
	 *
	 * @param statsKey the stats key
	 * @return the label provider
	 */
	private ColumnLabelProvider getLabelProvider(String statsKey) {
		IStatsService statsService = this.statsProvider
				.getStatsService(statsKey);
		if (statsService != null) {
			IStatsUiService statsUiService = this.statsUiProvider
					.getStatsUiService(statsService);
			if (statsUiService != null) {
				return statsUiService.getStatsLabelProvider();
			}
		}
		return new ColumnLabelProvider();
	}

	/**
	 * Gets the stats key.
	 *
	 * @param element the element
	 * @return the stats key
	 */
	@SuppressWarnings("rawtypes")
	private String getStatsKey(Object element) {
		return ((IEntry) element).getKey();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		String key = this.getStatsKey(element);
		return this.getLabelProvider(key).getText(key);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getFont(java.lang.Object)
	 */
	@Override
	public Font getFont(Object element) {
		String key = this.getStatsKey(element);
		return this.getLabelProvider(key).getFont(key);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getBackground(java.lang.Object)
	 */
	@Override
	public Color getBackground(Object element) {
		String key = this.getStatsKey(element);
		return this.getLabelProvider(key).getBackground(key);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getForeground(java.lang.Object)
	 */
	@Override
	public Color getForeground(Object element) {
		String key = this.getStatsKey(element);
		return this.getLabelProvider(key).getForeground(key);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		String key = this.getStatsKey(element);
		return this.getLabelProvider(key).getImage(key);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipImage(java.lang.Object)
	 */
	@Override
	public Image getToolTipImage(Object element) {
		String key = this.getStatsKey(element);
		return this.getLabelProvider(key).getToolTipImage(key);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipText(java.lang.Object)
	 */
	@Override
	public String getToolTipText(Object element) {
		String key = this.getStatsKey(element);
		return this.getLabelProvider(key).getToolTipText(key);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipBackgroundColor(java.lang.Object)
	 */
	@Override
	public Color getToolTipBackgroundColor(Object element) {
		String key = this.getStatsKey(element);
		return this.getLabelProvider(key).getToolTipBackgroundColor(key);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipForegroundColor(java.lang.Object)
	 */
	@Override
	public Color getToolTipForegroundColor(Object element) {
		String key = this.getStatsKey(element);
		return this.getLabelProvider(key).getToolTipForegroundColor(key);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipFont(java.lang.Object)
	 */
	@Override
	public Font getToolTipFont(Object element) {
		String key = this.getStatsKey(element);
		return this.getLabelProvider(key).getToolTipFont(key);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipShift(java.lang.Object)
	 */
	@Override
	public Point getToolTipShift(Object element) {
		String key = this.getStatsKey(element);
		return this.getLabelProvider(key).getToolTipShift(key);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipTimeDisplayed(java.lang.Object)
	 */
	@Override
	public int getToolTipTimeDisplayed(Object element) {
		String key = this.getStatsKey(element);
		return this.getLabelProvider(key).getToolTipTimeDisplayed(key);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipDisplayDelayTime(java.lang.Object)
	 */
	@Override
	public int getToolTipDisplayDelayTime(Object element) {
		String key = this.getStatsKey(element);
		return this.getLabelProvider(key).getToolTipDisplayDelayTime(key);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipStyle(java.lang.Object)
	 */
	@Override
	public int getToolTipStyle(Object element) {
		String key = this.getStatsKey(element);
		return this.getLabelProvider(key).getToolTipStyle(key);
	}
}
