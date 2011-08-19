package org.opendarts.core.x01.test;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opendarts.core.model.dart.DartSector;
import org.opendarts.core.model.dart.DartZone;
import org.opendarts.core.model.dart.IDart;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameDefinition;
import org.opendarts.core.model.player.IComputerPlayer;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.impl.GameSet;
import org.opendarts.core.service.game.IGameService;
import org.opendarts.core.service.player.IPlayerService;
import org.opendarts.core.service.session.ISessionService;
import org.opendarts.core.service.session.ISetService;
import org.opendarts.core.x01.model.GameX01Definition;

/**
 * The Class BugLastDartAt90.
 */
public class BugLastDartAt90 {

	/** The session service. */
	private static ISessionService sessionService;

	/** The set service. */
	private static ISetService setService;

	/** The player service. */
	private static IPlayerService playerService;

	private static int lvl;

	/**
	 * Inits the service.
	 */
	@BeforeClass
	public static void initService() {
		sessionService = CoreX01TestBundle.getSessionService();
		setService = CoreX01TestBundle.getSetService();
		playerService = CoreX01TestBundle.getPlayerService();
		lvl = 10;
	}

	/** The game service. */
	private IGameService gameService;

	/** The player. */
	private IComputerPlayer player;

	/** The game. */
	private IGame game;

	/**
	 * Inits the service.
	 */
	@Before
	public void initGame() {
		ISession session = sessionService.getSession();
		this.player = (IComputerPlayer) playerService.getComputerPlayer(lvl);
		List<IPlayer> players = Collections
				.singletonList((IPlayer) this.player);

		IGameDefinition gameDefinition = new GameX01Definition(501, players, 1,
				false);
		GameSet set = (GameSet) setService
				.createNewSet(session, gameDefinition);
		this.gameService = set.getGameService();

		this.game = this.gameService.createGame(set, players);
	}

	/**
	 * Test dart at90.
	 */
	@Test
	public void testDartAt90() {
		IDart dart = gameService.chooseBestDart(player, 90, 1, game);
		Assert.assertEquals(DartZone.TRIPLE, dart.getZone());
		Assert.assertEquals(DartSector.EIGHTEEN, dart.getSector());
	}
}
