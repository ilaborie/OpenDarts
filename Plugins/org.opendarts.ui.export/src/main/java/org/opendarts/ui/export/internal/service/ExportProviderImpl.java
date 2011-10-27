package org.opendarts.ui.export.internal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.opendarts.core.export.IExportOptions;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.ui.export.service.IExportProvider;
import org.opendarts.ui.export.service.IExportUiService;

/**
 * The Class ExportProviderImpl.
 */
public class ExportProviderImpl implements IExportProvider {

	/** The exporters. */
	private final CopyOnWriteArrayList<IExportUiService<IExportOptions>> exporters;

	/**
	 * Instantiates a new export provider impl.
	 */
	public ExportProviderImpl() {
		super();
		this.exporters = new CopyOnWriteArrayList<IExportUiService<IExportOptions>>();
	}
	
	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.IExportProvider#getAllExporter()
	 */
	@Override
	public List<IExportUiService<?>> getAllExporter() {
		List<IExportUiService<?>> result = new ArrayList<IExportUiService<?>>();
		for (IExportUiService<IExportOptions> service : this.exporters) {
			result.add(service);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.service.IExportProvider#getAllExporter(org.opendarts.core.model.session.ISession)
	 */
	@Override
	public List<IExportUiService<?>> getExporter(ISession session) {
		List<IExportUiService<?>> result = new ArrayList<IExportUiService<?>>();
		exp: for (IExportUiService<IExportOptions> service : this.exporters) {
			if (service.isApplicable(session)) {
				for (ISet set : session.getAllGame()) {
					if (service.isApplicable(set)) {
						for (IGame game : set.getAllGame()) {
							if (!service.isApplicable(game)) {
								continue exp;
							}
						}
					} else {
						continue exp;
					}
				}
				result.add(service);
			}
		}
		return result;
	}

	/**
	 * Adds the export ui service.
	 *
	 * @param service the service
	 */
	public void addExportUiService(IExportUiService<IExportOptions> service) {
		this.exporters.add(service);
	}

	/**
	 * Removes the export ui service.
	 *
	 * @param service the service
	 */
	public void removeExportUiService(IExportUiService<IExportOptions> service) {
		this.exporters.remove(service);
	}

}
