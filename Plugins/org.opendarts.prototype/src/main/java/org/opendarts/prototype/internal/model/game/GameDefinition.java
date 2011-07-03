package org.opendarts.prototype.internal.model.game;

import java.util.ArrayList;
import java.util.List;

import org.opendarts.prototype.model.game.IGameDefinition;
import org.opendarts.prototype.model.player.IPlayer;

/**
 * The Class GameDefinition.
 */
public class GameDefinition implements IGameDefinition {

	/** The players. */
	private final List<IPlayer> players;

	/**
	 * Instantiates a new game definition.
	 *
	 * @param players the players
	 */
	public GameDefinition(List<IPlayer> players) {
		super();
		this.players = new ArrayList<IPlayer>(players);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.game.IGameDefinition#getPlayers()
	 */
	@Override
	public List<IPlayer> getPlayers() {
		return this.players;
	}
}
