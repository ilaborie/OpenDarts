package org.opendarts.core.x01.defi.service;

import java.util.List;

import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.dart.impl.ThreeDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.model.session.impl.GameSet;
import org.opendarts.core.x01.defi.model.GameX01Defi;
import org.opendarts.core.x01.defi.model.GameX01DefiDefinition;
import org.opendarts.core.x01.model.GameX01;
import org.opendarts.core.x01.model.WinningX01DartsThrow;
import org.opendarts.core.x01.service.GameX01Service;

/**
 * The Class GameX01DefiService.
 */
public class GameX01DefiService extends GameX01Service {
	
	/**
	 * Instantiates a new game x01 defi service.
	 */
	public GameX01DefiService() {
		super();
	}


	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.IGameService#createGame(org.opendarts.prototype.model.ISet, org.opendarts.prototype.model.IGameDefinition, java.util.List)
	 */
	@Override
	public IGame createGame(ISet set, List<IPlayer> players) {
		GameX01DefiDefinition gameDef = (GameX01DefiDefinition) set.getGameDefinition();
		GameX01 result = new GameX01Defi((GameSet) set, players,
				gameDef.getStartScore());
		return result;
	}
	
	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.game.IGameService#addPlayerThrow(org.opendarts.prototype.internal.model.game.x01.GameX01, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	public void addPlayerThrow(IGame igame, IPlayer player,
			IDartsThrow idartThrow) {
		GameX01Defi game = (GameX01Defi) igame;
		ThreeDartsThrow dartThrow = (ThreeDartsThrow) idartThrow;
		game.addPlayerThrow(player, dartThrow);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.game.IGameService#addWinningPlayerThrow(org.opendarts.prototype.internal.model.game.x01.GameX01, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	public void addWinningPlayerThrow(IGame igame, IPlayer player,
			IDartsThrow idartThrow) {
		GameX01Defi game = (GameX01Defi) igame;
		WinningX01DartsThrow dartThrow = (WinningX01DartsThrow) idartThrow;
		game.addWinningPlayerThrow(player, dartThrow);
	}

}
