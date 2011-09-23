package org.opendarts.ui.x01.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opendarts.core.model.dart.impl.ThreeDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.x01.model.GameX01Entry;

/**
 * The Class SessionThrowDistributionChartX01.
 *
 * @param <T> the generic type
 */
public class GameThrowDistributionChartX01<T> extends GameCategoryChartX01<T> {

	/** The distribution. */
	private final Map<IPlayer, int[]> distribution;

	/** The categories. */
	private List<Category> categories;

	/** The game. */
	private final IGame game;

	/**
	 * Instantiates a new session throw distribution chart x01.
	 *
	 * @param name the name
	 * @param statKey the stat key
	 * @param session the session
	 */
	public GameThrowDistributionChartX01(String name, String statKey, IGame game) {
		super(name, statKey, game);
		this.distribution = new HashMap<IPlayer, int[]>();
		this.game = game;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.x01.model.SessionCategoryChartX01#getCategories()
	 */
	@Override
	protected List<Category> getCategories() {
		if (this.categories == null) {
			this.categories = new ArrayList<Category>();
			Category category;
			for (int i = 1; i < 18; i++) {
				category = new Category(String.valueOf(i * 10));
				this.categories.add(category);
			}
		}
		return this.categories;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.x01.model.SessionCategoryChartX01#getValue(org.opendarts.ui.x01.model.Category, org.opendarts.core.model.player.IPlayer)
	 */
	@Override
	protected Double getValue(Category c, IPlayer player) {
		int[] values = this.distribution.get(player);
		if (values == null) {
			values = new int[18];
			Arrays.fill(values, 0);

			GameX01Entry entry;
			ThreeDartsThrow dThrow;
			int val;
			int i;
			for (IGameEntry ientry : game.getGameEntries()) {
				entry = (GameX01Entry) ientry;
				dThrow = entry.getPlayerThrow().get(player);
				if (dThrow != null) {
					val = dThrow.getScore();
					if (val == 180) {
						i = 17;
					} else {
						i = val / 10;
					}
					values[i] = values[i] + 1;
				}
			}
		}
		int i = (Integer.valueOf(c.getName()) / 10);
		return (double) values[i];
	}
}
