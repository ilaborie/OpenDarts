package org.opendarts.prototype.internal.service.game.x01;

import java.util.List;

import org.opendarts.prototype.internal.model.dart.ThreeDartThrow;
import org.opendarts.prototype.internal.model.game.x01.GameX01;
import org.opendarts.prototype.internal.model.game.x01.WinningX01DartsThrow;
import org.opendarts.prototype.internal.model.session.GameSet;
import org.opendarts.prototype.model.dart.IDartsThrow;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.model.session.ISet;
import org.opendarts.prototype.service.game.IGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class GameX01Service.
 */
public class GameX01Service implements IGameService {

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
	public IGame createGame(ISet set, List<IPlayer> players) {
		GameX01 result = new GameX01((GameSet) set, players);
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

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.game.IGameService#addPlayerThrow(org.opendarts.prototype.internal.model.game.x01.GameX01, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	public void addPlayerThrow(GameX01 igame, IPlayer player,
			IDartsThrow idartThrow) {
		GameX01 game = (GameX01) igame;
		ThreeDartThrow dartThrow = (ThreeDartThrow) idartThrow;
		game.addPlayerThrow(player, dartThrow);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.game.IGameService#addWinningPlayerThrow(org.opendarts.prototype.internal.model.game.x01.GameX01, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	public void addWinningPlayerThrow(GameX01 igame, IPlayer player,
			IDartsThrow idartThrow) {
		GameX01 game = (GameX01) igame;
		WinningX01DartsThrow dartThrow = (WinningX01DartsThrow) idartThrow;
		game.addWinningPlayerThrow(player, dartThrow);
	}
}
