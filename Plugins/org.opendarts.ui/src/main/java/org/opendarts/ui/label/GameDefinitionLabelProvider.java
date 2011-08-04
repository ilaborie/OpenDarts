package org.opendarts.ui.label;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.opendarts.ui.service.IGameDefinitionProvider;

/**
 * The Class GameDefinitionLabelProvider.
 */
public class GameDefinitionLabelProvider extends ColumnLabelProvider {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof IGameDefinitionProvider) {
			IGameDefinitionProvider gdp = (IGameDefinitionProvider) element;
			return gdp.getName();
		}
		return super.getText(element);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof IGameDefinitionProvider) {
			IGameDefinitionProvider gdp = (IGameDefinitionProvider) element;
			return gdp.getImage();
		}
		return super.getImage(element);
	}

}
