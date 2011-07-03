package org.opendarts.prototype.internal.service.session;

import org.opendarts.prototype.internal.model.game.GameDefinition;
import org.opendarts.prototype.internal.model.session.GameSet;
import org.opendarts.prototype.model.game.IGameDefinition;
import org.opendarts.prototype.model.session.ISession;
import org.opendarts.prototype.model.session.ISet;
import org.opendarts.prototype.service.session.ISetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class SetService.
 */
public class SetService implements ISetService {

	/** The logger. */
	private static final Logger LOG = LoggerFactory.getLogger(SetService.class);

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
	public ISet createNewSet(ISession session, IGameDefinition gameDefinition) {
		ISet set = new GameSet(session, (GameDefinition) gameDefinition);
		LOG.info("New set created: {}", set);
		return set;
	}

}
