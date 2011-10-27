package org.opendarts.ui.export.service;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.opendarts.core.export.IExportOptions;
import org.opendarts.core.export.IExportService;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.ui.export.composite.AbstractExportOptionComposite;

/**
 * The Interface IExportUiService.
 *
 * @param <O> the generic type
 */
public interface IExportUiService<O extends IExportOptions> extends IExportService<O> {

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();
	
	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	Image getImage();
	
	/**
	 * Creates the export options composite.
	 *
	 * @param parent the parent
	 * @return the abstract export option composite
	 */
	AbstractExportOptionComposite<O> createExportOptionsComposite(Composite parent);
	
	/**
	 * Checks if is applicable.
	 *
	 * @param session the session
	 * @return true, if is applicable
	 */
	boolean isApplicable(ISession session);
	
	/**
	 * Checks if is applicable.
	 *
	 * @param set the set
	 * @return true, if is applicable
	 */
	boolean isApplicable(ISet set);
	
	/**
	 * Checks if is applicable.
	 *
	 * @param game the game
	 * @return true, if is applicable
	 */
	boolean isApplicable(IGame game);
	
}
