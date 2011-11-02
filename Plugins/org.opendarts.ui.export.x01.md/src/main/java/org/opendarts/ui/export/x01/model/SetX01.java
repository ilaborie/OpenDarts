package org.opendarts.ui.export.x01.model;

import java.util.ArrayList;
import java.util.List;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.core.x01.model.GameX01Definition;
import org.opendarts.ui.export.model.Game;
import org.opendarts.ui.export.model.Session;
import org.opendarts.ui.export.model.Set;

/**
 * The Class SetX01.
 */
public class SetX01 extends Set {

	/** The games. */
	private List<Game> games;

	/** The game definition. */
	private final GameX01Definition gameDefinition;

	/**
	 * Instantiates a new sets the x01.
	 *
	 * @param parent the parent
	 * @param set the set
	 * @param statsServices the stats services
	 */
	public SetX01(Session parent, ISet set, List<IStatsService> statsServices) {
		super(parent, set, statsServices);
		this.gameDefinition = (GameX01Definition) set.getGameDefinition();
	}

	/**
	* Gets the starting score.
	*
	* @return the starting score
	*/
	public String getStartingScore() {
		return this.getFormatters().getNumberFormat().format(
				this.gameDefinition.getStartScore());
	}

	/**
	 * Gets the nb game to win.
	 *
	 * @return the nb game to win
	 */
	public String getNbGameToWin() {
		return this.getFormatters().getNumberFormat().format(
				this.gameDefinition.getNbGameToWin());
	}

	/**
	 * Gets the games.
	 *
	 * @return the games
	 */
	public List<Game> getGames() {
		if (this.games == null) {
			this.games = new ArrayList<Game>();
			for (IGame g : this.getElement().getAllGame()) {
				if (g instanceof org.opendarts.core.x01.model.GameX01) {
					this.games
							.add(new GameX01(this, g, this.getStatsServices()));
				} else {
					this.games.add(new Game(this, g, this.getStatsServices()));
				}
			}
		}
		return this.games;
	}

}
