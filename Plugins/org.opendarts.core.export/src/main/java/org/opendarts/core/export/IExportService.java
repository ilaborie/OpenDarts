package org.opendarts.core.export;

import java.io.File;

import org.opendarts.core.model.session.ISession;

/**
 * The Interface IExportService.
 *
 * @param <O> the option generic type
 */
public interface IExportService<O extends IExportOptions> {

	/**
	 * Export.
	 *
	 * @param session the session
	 * @param file the file
	 * @param option the option
	 */
	void export(ISession session, File file, O option);
}