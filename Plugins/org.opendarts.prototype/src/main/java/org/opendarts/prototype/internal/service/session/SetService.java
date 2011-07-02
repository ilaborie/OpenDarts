package org.opendarts.prototype.internal.service.session;

import org.opendarts.prototype.internal.model.session.GameSet;
import org.opendarts.prototype.model.session.ISession;
import org.opendarts.prototype.model.session.ISet;
import org.opendarts.prototype.service.session.ISetService;

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
