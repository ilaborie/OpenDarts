package org.opendarts.core.x01.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.opendarts.core.model.dart.IDart;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.service.dart.IDartService;
import org.opendarts.core.x01.OpenDartsX01Bundle;
import org.opendarts.core.x01.service.IBestDart;

/**
 * The Class BestDart.
 */
public class BestDart implements IBestDart {

	/** The index. */
	private final int index;

	/** The darts. */
	private final Map<Integer, IDart> darts;

	/** The default dart. */
	private final IDart defaultDart;

	/**
	 * Instantiates a new best dart.
	 *
	 * @param index the index
	 * @param props the props
	 */
	public BestDart(int index, Properties props) {
		super();
		this.index = index;
		IDartService dartService = OpenDartsX01Bundle.getBundle().getDartService();

		this.darts = new HashMap<Integer, IDart>();
		String sDart;
		String key;
		Integer score;
		for (Entry<Object, Object> entry : props.entrySet()) {
			key = entry.getKey().toString();
			sDart = entry.getValue().toString();
			try {
				score = Integer.valueOf(key.substring("score.".length()));
				this.darts.put(score, dartService.getDart(sDart));
			} catch (NumberFormatException e) {
				// Skip
			}
		}

		// Default
		sDart = props.getProperty("score.default");
		this.defaultDart = dartService.getDart(sDart);
	}

	/**
	 * Gets the index.
	 *
	 * @return the index
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * Gets the best dart.
	 *
	 * @param score the score
	 * @return the best dart
	 */
	@Override
	public IDart getBestDart(IPlayer player, int score, IGame game) {
		IDart dart = this.darts.get(score);
		if (dart == null) {
			dart = this.defaultDart;
		}
		return dart;
	}
}
