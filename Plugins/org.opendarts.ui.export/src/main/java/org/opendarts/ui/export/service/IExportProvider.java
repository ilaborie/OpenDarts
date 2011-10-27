package org.opendarts.ui.export.service;

import java.util.List;

import org.opendarts.core.model.session.ISession;

/**
 * The Interface IExportProvider.
 */
public interface IExportProvider {

	/**
	 * Gets the all exporter.
	 *
	 * @param session the session
	 * @return the all exporter
	 */
	List<IExportUiService<?>> getExporter(ISession session);

	/**
	 * Gets the all exporter.
	 *
	 * @return the all exporter
	 */
	List<IExportUiService<?>> getAllExporter();

}
