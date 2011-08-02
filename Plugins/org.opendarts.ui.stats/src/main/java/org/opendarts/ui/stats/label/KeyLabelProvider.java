package org.opendarts.ui.stats.label;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.opendarts.core.stats.model.IElementStats.IEntry;

/**
 * The Class KeyLabelProvider.
 */
public class KeyLabelProvider extends ColumnLabelProvider {
	
	/**
	 * Instantiates a new key label provider.
	 */
	public KeyLabelProvider() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String getText(Object element) {
		return ((IEntry) element).getKey();
	}

}
