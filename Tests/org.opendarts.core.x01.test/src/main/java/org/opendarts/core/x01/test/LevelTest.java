package org.opendarts.core.x01.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.game.IGameDefinition;
import org.opendarts.core.model.player.IComputerPlayer;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.impl.GameSet;
import org.opendarts.core.service.game.IGameService;
import org.opendarts.core.service.player.IPlayerService;
import org.opendarts.core.service.session.ISessionService;
import org.opendarts.core.service.session.ISetService;
import org.opendarts.core.x01.model.GameX01;
import org.opendarts.core.x01.model.GameX01Definition;
import org.opendarts.core.x01.model.WinningX01DartsThrow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class LevelTest.
 */
public class LevelTest {

	/** The logger. */
	private static final Logger LOG = LoggerFactory.getLogger(LevelTest.class);

	/** The number of games played. */
	private final int nbGames = 2000;

	/** The LEVEL. */
	private final int lvl = 1;

	/** The played games. */
	private final List<GameX01> playedGames;

	/** The game. */
	private GameX01 game;

	/** The player. */
	private IComputerPlayer player;

	// Services
	/** The session service. */
	private ISessionService sessionService;

	/** The player service. */
	private IPlayerService playerService;

	/** The set service. */
	private ISetService setService;

	/** The game service. */
	private IGameService gameService;

	/**
	 * Instantiates a new level test.
	 */
	public LevelTest() {
		super();
		this.playedGames = new ArrayList<GameX01>();
	}

	/**
	 * Startup.
	 */
	public void startup() {
		ISession session = sessionService.getCurrentSession();
		this.player = (IComputerPlayer) this.playerService
				.getComputerPlayer(lvl);
		List<IPlayer> players = Collections
				.singletonList((IPlayer) this.player);

		IGameDefinition gameDefinition = new GameX01Definition(501, players,
				nbGames, false);
		GameSet set = (GameSet) setService
				.createNewSet(session, gameDefinition);
		this.gameService = set.getGameService();

		setService.startSet(set);
		while (this.playedGames.size() < nbGames) {
			this.game = (GameX01) set.getCurrentGame();
			this.gameService.startGame(this.game);

			this.playGame();

			this.playedGames.add(this.game);
			this.game = null;
		}

		this.display();
	}

	/**
	 * Shutdown.
	 */
	public void shutdown() {
		// Nothing to do
	}

	/**
	 * Play game.
	 */
	private void playGame() {
		IDartsThrow dartsThrow;
		while (!this.game.isFinished()) {
			dartsThrow = this.gameService.getComputerDartsThrow(this.game,
					this.player).getDartsThrow();
			if (dartsThrow instanceof WinningX01DartsThrow) {
				this.gameService.addWinningPlayerThrow(this.game, this.player,
						dartsThrow);
			} else {
				this.gameService.addPlayerThrow(this.game, this.player,
						dartsThrow);
			}
		}
	}

	/**
	 * Shutdown.
	 */
	public void display() {
		// Display summary & stats
		int max = 0;
		int min = Integer.MAX_VALUE;
		;
		int sum = 0;

		Map<Integer, Integer> distib = new TreeMap<Integer, Integer>();
		int current;
		Integer count;
		for (GameX01 game : this.playedGames) {
			current = game.getNbDartToFinish();

			sum += current;
			max = Math.max(max, current);
			min = Math.min(min, current);

			count = distib.get(current);
			if (count == null) {
				distib.put(current, 1);
			} else {
				count += 1;
				distib.put(current, count);
			}
		}

		double avg = ((double) sum) / ((double) this.playedGames.size());
		LOG.debug("Stats");
		LOG.info("Nb played games: {}", this.playedGames.size());
		LOG.info("Best leg: {}", min);
		LOG.info("Worst leg: {}", max);
		LOG.info("Average leg: {}", avg);

		LOG.info("Distibution");
		for (Entry<Integer, Integer> d : distib.entrySet()) {
			LOG.debug("{}: {}", d.getKey(), d.getValue());
		}
	}

	/**
	 * Sets the player service.
	 *
	 * @param playerService the new player service
	 */
	public void setPlayerService(IPlayerService playerService) {
		this.playerService = playerService;
	}

	/**
	 * Unset player service.
	 *
	 * @param playerService the player service
	 */
	public void unsetPlayerService(IPlayerService playerService) {
		this.playerService = null;
	}

	public void setSessionService(ISessionService sessionService) {
		this.sessionService = sessionService;
	}

	public void unsetSessionService(ISessionService sessionService) {
		this.sessionService = null;
	}

	public void setSetService(ISetService setService) {
		this.setService = setService;
	}

	public void unsetSetService(ISetService setService) {
		this.setService = null;
	}
}