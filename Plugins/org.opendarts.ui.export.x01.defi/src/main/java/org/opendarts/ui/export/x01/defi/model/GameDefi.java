package org.opendarts.ui.export.x01.defi.model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.IElementStats;
import org.opendarts.core.stats.model.IStats;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.core.x01.defi.model.GameX01Defi;
import org.opendarts.core.x01.defi.model.GameX01DefiDefinition;
import org.opendarts.core.x01.model.GameX01Entry;
import org.opendarts.ui.export.model.Game;

/**
 * The Class GameX01.
 */
public class GameDefi extends Game {

	/** The game definition. */
	private GameX01DefiDefinition gameDefinition;

	/** The entries. */
	private List<GameEntry> entries;

	/** The game detail. */
	private GameDetail gameDetail;

	/** The players game detail. */
	private List<PlayerGameDetail> playersGameDetail;

	/**
	 * Instantiates a new game x01.
	 *
	 * @param set the set
	 * @param game the game
	 * @param statsServices the stats services
	 */
	public GameDefi(SetDefi set, IGame game, List<IStatsService> statsServices) {
		super(set, game, statsServices);
		this.gameDefinition = (GameX01DefiDefinition) set.getElement()
				.getGameDefinition();
	}

	/**
	 * Gets the game definition.
	 *
	 * @return the game definition
	 */
	protected GameX01DefiDefinition getGameDefinition() {
		return this.gameDefinition;
	}

	/**
	 * Gets the game detail.
	 *
	 * @return the game detail
	 */
	public GameDetail getGameDetail() {
		if (this.gameDetail == null) {
			GameX01Defi gameDefi = (GameX01Defi) this.getElement();

			int scoreDone = this.gameDefinition.getStartScore();
			int nbDarts = gameDefi.getNbDartToFinish();
			double duration = Long.valueOf(gameDefi.getDuration())
					.doubleValue();
			double nbThrows = (double) gameDefi.getNbDartToFinish()
					/ (double) (gameDefi.getPlayers().size() * 3);

			this.gameDetail = new GameDetail(this, duration, scoreDone,
					nbThrows, nbDarts);
		}
		return this.gameDetail;
	}

	/**
	 * Gets the players game detail.
	 *
	 * @return the players game detail
	 */
	public List<PlayerGameDetail> getPlayersGameDetail() {
		if (this.playersGameDetail == null) {
			this.playersGameDetail = new ArrayList<PlayerGameDetail>();

			IStats<IGame> stats;
			IElementStats<IGame> gameStats;
			for (IStatsService iss : this.getStatsServices()) {
				gameStats = iss.getGameStats(this.getElement());
				if (!gameStats.getStatsEntries().isEmpty()) {
					for (IPlayer p : this.getPlayerList()) {
						stats = gameStats.getPlayerStats(p);
						this.playersGameDetail.add(new PlayerGameDetail(this,
								p, stats));
					}
				}
			}
		}
		return this.playersGameDetail;
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
	 * Gets the target time.
	 *
	 * @return the target time
	 */
	public String getTargetTime() {
		DateFormat timeFormatter = gameDefinition.getTimeFormatter();
		Date date = new Date(gameDefinition.getTimeTarget());
		return timeFormatter.format(date);
	}

}
