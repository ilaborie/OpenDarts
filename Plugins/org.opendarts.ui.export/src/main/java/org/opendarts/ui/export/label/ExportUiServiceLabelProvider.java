package org.opendarts.ui.export.label;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.opendarts.ui.export.service.IExportUiService;

/**
 * The Class ExportUiServiceLabelProvider.
 */
public class ExportUiServiceLabelProvider extends ColumnLabelProvider implements
		IBaseLabelProvider {

	/**
	 * Instantiates a new export ui service label provider.
	 */
	public ExportUiServiceLabelProvider() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public String getText(Object element) {
		String result = null;
		if (element instanceof IExportUiService) {
			result = ((IExportUiService) element).getName();
		} else {
			result = super.getText(element);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getImage(java.lang.Object)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Image getImage(Object element) {
		Image result = null;
		if (element instanceof IExportUiService) {
			result = ((IExportUiService) element).getImage();
		} else {
			result = super.getImage(element);
		}
		return result;
	}

}
