package org.opendarts.ui.export.x01.defi.model;

import java.util.ArrayList;
import java.util.List;

import org.opendarts.core.model.dart.impl.ThreeDartsThrow;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.x01.model.GameX01Entry;

/**
 * The Class GameEntry.
 */
public class GameEntry {

	/** The entry. */
	private final GameX01Entry entry;

	/** The game. */
	private final GameDefi game;

	/** The scores. */
	private List<String> scores = null;

	/**
	 * Instantiates a new game entry.
	 *
	 * @param game the game
	 * @param entry the entry
	 */
	public GameEntry(GameDefi game, GameX01Entry entry) {
		super();
		this.entry = entry;
		this.game = game;
	}

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return "#" + entry.getRound();
	}

	/**
	 * Gets the scores.
	 *
	 * @return the scores
	 */
	public List<String> getScores() {
		if (this.scores == null) {
			this.scores = new ArrayList<String>();
			for (IPlayer p : this.game.getPlayerList()) {
				ThreeDartsThrow dartsThrow = this.entry.getPlayerThrow().get(p);
				if (dartsThrow == null) {
					this.scores.add("-");
				} else {
					this.scores.add(String.valueOf(dartsThrow.getScore()));
				}
			}
		}
		return this.scores;
	}
}
