package org.opendarts.prototype.service;

import org.opendarts.prototype.model.ISession;
import org.opendarts.prototype.model.ISet;

/**
 * The Interface ISetService.
 */
public interface ISetService {

	/**
	 * Creates the new set.
	 *
	 * @param selection the selection
	 * @return the i set
	 */
	ISet createNewSet(ISession session, int nbGame);

}
