package org.opendarts.ui.export.x01.model;

import java.util.ArrayList;
import java.util.List;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.core.x01.model.GameX01Definition;
import org.opendarts.core.x01.model.GameX01Entry;
import org.opendarts.ui.export.model.Game;

/**
 * The Class GameX01.
 */
public class GameX01 extends Game {

	/** The game definition. */
	private GameX01Definition gameDefinition;

	private List<GameEntry> entries;

	/**
	 * Instantiates a new game x01.
	 *
	 * @param set the set
	 * @param game the game
	 * @param statsServices the stats services
	 */
	public GameX01(SetX01 set, IGame game, List<IStatsService> statsServices) {
		super(set, game, statsServices);
		this.gameDefinition = (GameX01Definition) set.getElement()
				.getGameDefinition();
	}

	/**
	* Gets the starting score.
	*
	* @return the starting score
	*/
	public String getStartingScore() {
		return this.getNumberFormat().format(
				this.gameDefinition.getStartScore());
	}

	/**
	 * Gets the entries.
	 *
	 * @return the entries
	 */
	public List<GameEntry> getEntries() {
		if (this.entries == null) {
			this.entries = new ArrayList<GameEntry>();
			for (IGameEntry e : this.getElement().getGameEntries()) {
				this.entries.add(new GameEntry(this, (GameX01Entry) e));
			}
		}
		return this.entries;
	}

	/**
	 * Gets the nb game to win.
	 *
	 * @return the nb game to win
	 */
	public String getNbGameToWin() {
		return this.getNumberFormat().format(
				this.gameDefinition.getNbGameToWin());
	}

}
