package org.opendarts.prototype.internal.service.game.x01;

import java.util.List;

import org.opendarts.prototype.internal.model.dart.Dart;
import org.opendarts.prototype.internal.model.dart.ThreeDartsThrow;
import org.opendarts.prototype.internal.model.dart.x01.WinningX01DartsThrow;
import org.opendarts.prototype.internal.model.game.x01.GameX01;
import org.opendarts.prototype.internal.model.game.x01.GameX01Definition;
import org.opendarts.prototype.internal.model.session.GameSet;
import org.opendarts.prototype.model.dart.DartSector;
import org.opendarts.prototype.model.dart.DartZone;
import org.opendarts.prototype.model.dart.IDart;
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
		GameX01Definition gameDef = (GameX01Definition) set.getGameDefinition();
		GameX01 result = new GameX01((GameSet) set, players,
				gameDef.getStartScore());
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

	@Override
	public void cancelGame(IGame igame) {
		GameX01 game = (GameX01) igame;
		game.cancelGame();
		LOG.info("Game {} canceled", igame);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.game.IGameService#addPlayerThrow(org.opendarts.prototype.internal.model.game.x01.GameX01, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	public void addPlayerThrow(GameX01 igame, IPlayer player,
			IDartsThrow idartThrow) {
		GameX01 game = igame;
		ThreeDartsThrow dartThrow = (ThreeDartsThrow) idartThrow;
		game.addPlayerThrow(player, dartThrow);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.game.IGameService#addWinningPlayerThrow(org.opendarts.prototype.internal.model.game.x01.GameX01, org.opendarts.prototype.model.player.IPlayer, org.opendarts.prototype.model.dart.IDartsThrow)
	 */
	@Override
	public void addWinningPlayerThrow(GameX01 igame, IPlayer player,
			IDartsThrow idartThrow) {
		GameX01 game = igame;
		WinningX01DartsThrow dartThrow = (WinningX01DartsThrow) idartThrow;
		game.addWinningPlayerThrow(player, dartThrow);
	}

	/**
	 * Choose best dart.
	 *
	 * @param score the score
	 * @return the i dart
	 */
	@Override
	public IDart chooseBestDart(int score, int nbDart) {
		DartZone zone;
		DartSector sector;
		// Target: double, finish 32/40, finish 16; finish 8, finish 4, finish 2
		switch (score) {
			case 135:
				zone = DartZone.DOUBLE;
				sector = DartSector.BULL;
				break;
			case 132:
				zone = DartZone.DOUBLE;
				sector = DartSector.BULL;
				break;
			case 129:
				zone = DartZone.TRIPLE;
				sector = DartSector.NINETEEN;
				break;
			case 128:
				zone = DartZone.TRIPLE;
				sector = DartSector.EIGHTEEN;
				break;
			case 126:
				zone = DartZone.TRIPLE;
				sector = DartSector.NINETEEN;
				break;
			case 125:
				zone = DartZone.DOUBLE;
				sector = DartSector.BULL;
				break;
			case 123:
				zone = DartZone.TRIPLE;
				sector = DartSector.NINETEEN;
				break;
			case 122:
				zone = DartZone.TRIPLE;
				sector = DartSector.EIGHTEEN;
				break;
			case 121:
				zone = DartZone.DOUBLE;
				sector = DartSector.BULL;
				break;
			case 119:
				zone = DartZone.TRIPLE;
				sector = DartSector.NINETEEN;
				break;
			case 115:
				zone = DartZone.TRIPLE;
				sector = DartSector.NINETEEN;
				break;
			case 107:
				zone = DartZone.TRIPLE;
				sector = DartSector.NINETEEN;
				break;
			case 104:
				zone = DartZone.TRIPLE;
				sector = DartSector.EIGHTEEN;
				break;
			case 99:
				zone = DartZone.TRIPLE;
				sector = DartSector.SEVENTEEN;
				break;
			case 97:
				zone = DartZone.TRIPLE;
				sector = DartSector.NINETEEN;
				break;
			case 95:
				zone = DartZone.TRIPLE;
				sector = DartSector.NINETEEN;
				break;
			case 94:
				zone = DartZone.TRIPLE;
				sector = DartSector.EIGHTEEN;
				break;
			case 91:
				zone = DartZone.TRIPLE;
				sector = DartSector.SEVENTEEN;
				break;
			case 90:
				if (nbDart == 2) {
					zone = DartZone.TRIPLE;
					sector = DartSector.EIGHTEEN;
				} else {
					zone = DartZone.TRIPLE;
					sector = DartSector.TWENTY;
				}
				break;
			case 89://
				zone = DartZone.TRIPLE;
				sector = DartSector.NINETEEN;
				break;
			case 87:
				zone = DartZone.TRIPLE;
				sector = DartSector.SEVENTEEN;
				break;
			case 86:
				zone = DartZone.TRIPLE;
				sector = DartSector.EIGHTEEN;
				break;
			case 85:
				zone = DartZone.TRIPLE;
				sector = DartSector.FIVETEEN;
				break;
			case 83:
				zone = DartZone.TRIPLE;
				sector = DartSector.SEVENTEEN;
				break;
			case 81:
				zone = DartZone.TRIPLE;
				sector = DartSector.FIVETEEN;
				break;
			case 79:
				zone = DartZone.TRIPLE;
				sector = DartSector.NINETEEN;
				break;
			case 78:
				zone = DartZone.TRIPLE;
				sector = DartSector.EIGHTEEN;
				break;
			case 77:
				zone = DartZone.TRIPLE;
				sector = DartSector.NINETEEN;
				break;
			case 75:
				zone = DartZone.TRIPLE;
				sector = DartSector.SEVENTEEN;
				break;
			case 73:
				zone = DartZone.TRIPLE;
				sector = DartSector.NINETEEN;
				break;
			case 69:
				zone = DartZone.TRIPLE;
				sector = DartSector.FIVETEEN;
				break;
			case 61:
				zone = DartZone.SINGLE;
				sector = DartSector.BULL;
				break;
			case 60:
				zone = DartZone.SINGLE;
				sector = DartSector.TWENTY;
				break;
			case 59:
				zone = DartZone.SINGLE;
				sector = DartSector.NINETEEN;
				break;
			case 58:
				zone = DartZone.SINGLE;
				sector = DartSector.EIGHTEEN;
				break;
			case 57:
				zone = DartZone.SINGLE;
				sector = DartSector.SEVENTEEN;
				break;
			case 56:
				zone = DartZone.SINGLE;
				sector = DartSector.SIXTEEN;
				break;
			case 55:
				zone = DartZone.SINGLE;
				sector = DartSector.FIVETEEN;
				break;
			case 54:
				zone = DartZone.SINGLE;
				sector = DartSector.FOURTEEN;
				break;
			case 53:
				zone = DartZone.SINGLE;
				sector = DartSector.THIRTEEN;
				break;
			case 52:
				zone = DartZone.SINGLE;
				sector = DartSector.TWENTY;
				break;
			case 51:
				zone = DartZone.SINGLE;
				sector = DartSector.NINETEEN;
				break;
			case 50:
				zone = DartZone.DOUBLE;
				sector = DartSector.BULL;
				break;
			case 49:
				zone = DartZone.SINGLE;
				sector = DartSector.SEVENTEEN;
				break;
			case 48:
				zone = DartZone.SINGLE;
				sector = DartSector.SIXTEEN;
				break;
			case 47:
				zone = DartZone.SINGLE;
				sector = DartSector.FIVETEEN;
				break;
			case 46:
				zone = DartZone.SINGLE;
				sector = DartSector.SIX;
				break;
			case 45:
				zone = DartZone.SINGLE;
				sector = DartSector.THIRTEEN;
				break;
			case 44:
				zone = DartZone.SINGLE;
				sector = DartSector.FOUR;
				break;
			case 43:
				zone = DartZone.SINGLE;
				sector = DartSector.ELEVEN;
				break;
			case 42:
				zone = DartZone.SINGLE;
				sector = DartSector.TEN;
				break;
			case 41:
				zone = DartZone.SINGLE;
				sector = DartSector.NINE;
				break;
			case 40:
				zone = DartZone.DOUBLE;
				sector = DartSector.TWENTY;
				break;
			case 39:
				zone = DartZone.SINGLE;
				sector = DartSector.SEVEN;
				break;
			case 38:
				zone = DartZone.DOUBLE;
				sector = DartSector.NINETEEN;
				break;
			case 37:
				zone = DartZone.SINGLE;
				sector = DartSector.FIVE;
				break;
			case 36:
				zone = DartZone.DOUBLE;
				sector = DartSector.EIGHTEEN;
				break;
			case 35:
				zone = DartZone.SINGLE;
				sector = DartSector.THREE;
				break;
			case 34:
				zone = DartZone.DOUBLE;
				sector = DartSector.SEVENTEEN;
				break;
			case 33:
				zone = DartZone.SINGLE;
				sector = DartSector.ONE;
				break;
			case 32:
				zone = DartZone.DOUBLE;
				sector = DartSector.SIXTEEN;
				break;
			case 31:
				zone = DartZone.SINGLE;
				sector = DartSector.FIVETEEN;
				break;
			case 30:
				zone = DartZone.DOUBLE;
				sector = DartSector.FIVETEEN;
				break;
			case 29:
				zone = DartZone.SINGLE;
				sector = DartSector.THIRTEEN;
				break;
			case 28:
				zone = DartZone.DOUBLE;
				sector = DartSector.FOURTEEN;
				break;
			case 27:
				zone = DartZone.SINGLE;
				sector = DartSector.ELEVEN;
				break;
			case 26:
				zone = DartZone.DOUBLE;
				sector = DartSector.THIRTEEN;
				break;
			case 25:
				zone = DartZone.SINGLE;
				sector = DartSector.NINE;
				break;
			case 24:
				zone = DartZone.DOUBLE;
				sector = DartSector.TWELVE;
				break;
			case 23:
				zone = DartZone.SINGLE;
				sector = DartSector.SEVEN;
				break;
			case 22:
				zone = DartZone.DOUBLE;
				sector = DartSector.ELEVEN;
				break;
			case 21:
				zone = DartZone.SINGLE;
				sector = DartSector.ONE;
				break;
			case 20:
				zone = DartZone.DOUBLE;
				sector = DartSector.TEN;
				break;
			case 19:
				zone = DartZone.SINGLE;
				sector = DartSector.THREE;
				break;
			case 18:
				zone = DartZone.DOUBLE;
				sector = DartSector.NINE;
				break;
			case 17:
				zone = DartZone.SINGLE;
				sector = DartSector.ONE;
				break;
			case 16:
				zone = DartZone.DOUBLE;
				sector = DartSector.EIGHT;
				break;
			case 15:
				zone = DartZone.SINGLE;
				sector = DartSector.SEVEN;
				break;
			case 14:
				zone = DartZone.DOUBLE;
				sector = DartSector.SEVEN;
				break;
			case 13:
				zone = DartZone.SINGLE;
				sector = DartSector.FIVE;
				break;
			case 12:
				zone = DartZone.DOUBLE;
				sector = DartSector.SIX;
				break;
			case 11:
				zone = DartZone.SINGLE;
				sector = DartSector.FIVE;
				break;
			case 10:
				zone = DartZone.DOUBLE;
				sector = DartSector.FIVE;
				break;
			case 9:
				zone = DartZone.SINGLE;
				sector = DartSector.ONE;
				break;
			case 8:
				zone = DartZone.DOUBLE;
				sector = DartSector.FOUR;
				break;
			case 7:
				zone = DartZone.SINGLE;
				sector = DartSector.THREE;
				break;
			case 6:
				zone = DartZone.DOUBLE;
				sector = DartSector.THREE;
				break;
			case 5:
				zone = DartZone.SINGLE;
				sector = DartSector.ONE;
				break;
			case 4:
				zone = DartZone.DOUBLE;
				sector = DartSector.TWO;
				break;
			case 3:
				zone = DartZone.SINGLE;
				sector = DartSector.ONE;
				break;
			case 2:
				zone = DartZone.DOUBLE;
				sector = DartSector.ONE;
				break;
			default:
				zone = DartZone.TRIPLE;
				sector = DartSector.TWENTY;
				break;
		}
		return new Dart(sector, zone);
	}
}
