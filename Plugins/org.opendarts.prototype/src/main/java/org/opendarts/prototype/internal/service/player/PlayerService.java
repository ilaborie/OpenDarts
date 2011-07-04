package org.opendarts.prototype.internal.service.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opendarts.prototype.internal.model.player.ComputerPlayer;
import org.opendarts.prototype.internal.model.player.Player;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.service.player.IPlayerService;

/**
 * The Class PlayerService.
 */
public class PlayerService implements IPlayerService {

	/** The players. */
	private final Map<String, IPlayer> players;

	/**
	 * Instantiates a new player service.
	 */
	public PlayerService() {
		super();
		this.players = new HashMap<String, IPlayer>();
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
		if (list.isEmpty()) {
			list.add(this.getComputerPlayer());
		}
		return list;
	}

}
