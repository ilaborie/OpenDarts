package org.opendarts.prototype.internal.service;

import org.opendarts.prototype.internal.model.GameSet;
import org.opendarts.prototype.model.ISession;
import org.opendarts.prototype.model.ISet;
import org.opendarts.prototype.service.ISetService;

/**
 * The Class SetService.
 */
public class SetService implements ISetService {

	/**
	 * Instantiates a new sets the service.
	 */
	public SetService() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.ISetService#createNewSet(org.opendarts.prototype.model.ISession, int)
	 */
	@Override
	public ISet createNewSet(ISession session, int nbGame) {
		ISet result = new GameSet(session, nbGame);
		return result;
	}

}
