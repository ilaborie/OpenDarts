package org.opendarts.core.player.internal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.player.model.ComputerPlayer;
import org.opendarts.core.player.model.Player;
import org.opendarts.core.service.dart.IDartService;
import org.opendarts.core.service.player.IPlayerService;

/**
 * The Class PlayerService.
 */
public class PlayerService implements IPlayerService {

	/** The players. */
	private final Map<String, IPlayer> players;

	/** The dart service. */
	private final AtomicReference<IDartService> dartService = new AtomicReference<IDartService>();

	/**
	 * Instantiates a new player service.
	 */
	public PlayerService() {
		super();
		this.players = new HashMap<String, IPlayer>();
		// XXX init
		IPlayer player;
		String name = System.getenv("USER");
		if ((name == null) || "".equals(name)) {
			name = System.getenv("USERNAME");
		}
		player = this.createPlayer(name);
		this.players.put(player.getName(), player);

		// A computer
		for (int lvl = 0; lvl<13 ; lvl++) {
			player = this.createComputer(lvl);
			this.players.put(player.getName(), player);
		}
	}
	
	/**
	 * Creates the player.
	 *
	 * @param name the name
	 * @return the player
	 */
	private Player createPlayer(String name) {
		Player player = new Player();
		player.setUuid(UUID.randomUUID().toString());
		if (name==null || "".equals(name)) {
			player.setName("The misterious player");
		} else {
			player.setName(name);
		}
		return player;
	}
	
	/**
	 * Creates the computer.
	 *
	 * @param level the level
	 * @return the computer player
	 */
	private ComputerPlayer createComputer(int level) {
		ComputerPlayer player = new ComputerPlayer();
		player.setUuid(UUID.randomUUID().toString());
		player.setName("COM_"+level);
		player.setLevel(level);
		return player;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.IPlayerService#getPlayer(java.lang.String)
	 */
	@Override
	public IPlayer getPlayer(String playerName) {
		IPlayer result = this.players.get(playerName);
		if (result == null) {
			result = new Player(playerName);
			this.players.put(playerName, result);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.IPlayerService#getComputerPlayer()
	 */
	@Override
	public IPlayer getComputerPlayer() {
		return new ComputerPlayer();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.player.IPlayerService#getAllPlayers()
	 */
	@Override
	public List<IPlayer> getAllPlayers() {
		ArrayList<IPlayer> list = new ArrayList<IPlayer>(this.players.values());
		return list;
	}

	/**
	 * Sets the dart service.
	 *
	 * @param dartService the new dart service
	 */
	public void setDartService(IDartService dartService) {
		this.dartService.set(dartService);
	}

	/**
	 * Unset dart service.
	 *
	 * @param dartService the dart service
	 */
	public void unsetDartService(IDartService dartService) {
		this.dartService.compareAndSet(dartService, null);
	}

}
