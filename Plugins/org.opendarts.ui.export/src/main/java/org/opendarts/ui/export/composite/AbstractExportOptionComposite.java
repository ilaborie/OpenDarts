package org.opendarts.ui.export.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.opendarts.core.export.IExportOptions;

/**
 * The Class AbstractExportOptionComposite.
 *
 * @param <O> the generic type
 */
public abstract class AbstractExportOptionComposite<O extends IExportOptions>
		extends Composite {

	/**
	 * Instantiates a new abstract export option composite.
	 *
	 * @param parent the parent
	 * @param style the style
	 */
	public AbstractExportOptionComposite(Composite parent) {
		super(parent, SWT.NONE);
	}

	/**
	 * Gets the export options.
	 *
	 * @return the export options
	 */
	public abstract O getExportOptions();

}
