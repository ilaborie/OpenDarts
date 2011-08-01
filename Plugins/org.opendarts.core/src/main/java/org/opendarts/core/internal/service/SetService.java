/*
 * 
 */
package org.opendarts.core.internal.service;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameDefinition;
import org.opendarts.core.model.game.impl.GameDefinition;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.model.session.impl.GameSet;
import org.opendarts.core.service.game.IGameService;
import org.opendarts.core.service.session.ISetService;
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
		session.addGame(set);
		LOG.info("New set created: {}", set);
		return set;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.session.ISetService#startSet(org.opendarts.prototype.model.session.ISet)
	 */
	@Override
	public void startSet(ISet set) {
		set.initSet();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.session.ISetService#cancelSet(org.opendarts.prototype.model.session.ISet)
	 */
	@Override
	public void cancelSet(ISet set) {
		IGame game = set.getCurrentGame();
		if (game != null) {
			IGameService gameService = set.getGameService();
			gameService.cancelGame(game);
		}
		GameSet gSet = (GameSet) set;
		gSet.cancelSet();
		LOG.info("Set canceled: {}", set);
	}

}
