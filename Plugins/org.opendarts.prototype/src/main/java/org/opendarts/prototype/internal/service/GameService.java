package org.opendarts.prototype.internal.service;

import java.util.List;

import org.opendarts.prototype.internal.model.Game;
import org.opendarts.prototype.internal.model.GameDefinition;
import org.opendarts.prototype.internal.model.GameSet;
import org.opendarts.prototype.model.IGame;
import org.opendarts.prototype.model.IGameDefinition;
import org.opendarts.prototype.model.IPlayer;
import org.opendarts.prototype.model.ISet;
import org.opendarts.prototype.service.IGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class GameService.
 */
public class GameService implements IGameService{
	
	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(GameService.class);
	
	
	/**
	 * Instantiates a new game service.
	 */
	public GameService() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.IGameService#createGame(org.opendarts.prototype.model.ISet, org.opendarts.prototype.model.IGameDefinition, java.util.List)
	 */
	@Override
	public IGame createGame(ISet set, IGameDefinition gameDef,
			List<IPlayer> players) {
		IPlayer[] pl = players.toArray(new IPlayer[players.size()]);
		Game result = new Game((GameSet) set, (GameDefinition) gameDef, pl);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.IGameService#startGame(org.opendarts.prototype.model.IGame)
	 */
	@Override
	public void startGame(IGame game) {
		LOG.info("Start the game: {}", game);
	}

}
