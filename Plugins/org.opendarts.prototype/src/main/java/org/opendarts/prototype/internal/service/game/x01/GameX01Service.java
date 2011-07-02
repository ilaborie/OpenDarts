package org.opendarts.prototype.internal.service.game.x01;

import java.util.List;

import org.opendarts.prototype.internal.model.game.GameDefinition;
import org.opendarts.prototype.internal.model.game.x01.GameX01;
import org.opendarts.prototype.internal.model.session.GameSet;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.game.IGameDefinition;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.model.session.ISet;
import org.opendarts.prototype.service.game.IGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class GameX01Service.
 */
public class GameX01Service implements IGameService{
	
	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(GameX01Service.class);
	
	
	/**
	 * Instantiates a new game service.
	 */
	public GameX01Service() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.IGameService#createGame(org.opendarts.prototype.model.ISet, org.opendarts.prototype.model.IGameDefinition, java.util.List)
	 */
	@Override
	public IGame createGame(ISet set, IGameDefinition gameDef,
			List<IPlayer> players) {
		IPlayer[] pl = players.toArray(new IPlayer[players.size()]);
		GameX01 result = new GameX01((GameSet) set, (GameDefinition) gameDef, pl);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.IGameService#startGame(org.opendarts.prototype.model.IGame)
	 */
	@Override
	public void startGame(IGame igame) {
		GameX01 game = (GameX01) igame;
		game.initGame();
		LOG.info("Game {} started", igame);
	}

}
