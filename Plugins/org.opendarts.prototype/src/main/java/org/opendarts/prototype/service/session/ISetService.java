package org.opendarts.prototype.service.session;

import org.opendarts.prototype.model.session.ISession;
import org.opendarts.prototype.model.session.ISet;

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
