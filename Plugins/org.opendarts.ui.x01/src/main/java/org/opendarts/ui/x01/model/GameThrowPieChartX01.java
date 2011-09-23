package org.opendarts.ui.x01.model;

import java.util.ArrayList;
import java.util.List;

import org.opendarts.core.model.dart.impl.ThreeDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.x01.model.GameX01Entry;

/**
 * The Class SessionThrowPieChartX01.
 *
 * @param <T> the generic type
 */
public class GameThrowPieChartX01<T> extends GamePieChartX01<T> {

	/**
	 * The Class ThrowCategory.
	 */
	private final class ThrowCategory extends Category {

		/** The min. */
		private final int min;

		/** The max. */
		private final int max;

		/**
		 * Instantiates a new throw category.
		 *
		 * @param min the min
		 * @param max the max
		 */
		public ThrowCategory(int min, int max) {
			super("" + min + "-" + max);
			this.min = min;
			this.max = max;
		}

		/**
		 * Instantiates a new throw category.
		 *
		 * @param min the min
		 */
		public ThrowCategory(int min) {
			super("+" + min);
			this.min = min;
			this.max = Integer.MAX_VALUE;
		}

		/**
		 * Checks if is in category.
		 *
		 * @param value the value
		 * @return true, if is in category
		 */
		public boolean isInCategory(int value) {
			return value >= this.min && value <= this.max;
		}

	}

	/** The session. */
	private final IGame game;

	/** The categories. */
	private List<Category> categories;

	/**
	 * Instantiates a new session throw pie chart x01.
	 *
	 * @param name the name
	 * @param statKey the stat key
	 * @param session the session
	 */
	public GameThrowPieChartX01(String name, String statKey, IGame game) {
		super(name, statKey, game);
		this.game = game;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.x01.model.SessionPieChartX01#getCategories()
	 */
	@Override
	protected List<Category> getCategories() {
		if (this.categories == null) {
			this.categories = new ArrayList<Category>();
			this.categories.add(new ThrowCategory(0, 19));
			this.categories.add(new ThrowCategory(20, 39));
			this.categories.add(new ThrowCategory(40, 59));
			this.categories.add(new ThrowCategory(60, 79));
			this.categories.add(new ThrowCategory(80, 99));
			this.categories.add(new ThrowCategory(100));
		}
		return this.categories;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.x01.model.SessionPieChartX01#getValue(org.opendarts.ui.x01.model.Category, org.opendarts.core.model.player.IPlayer)
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected Double getValue(Category c, IPlayer player) {
		int result = 0;

		GameX01Entry entry;
		ThreeDartsThrow dThrow;
		int val;
		ThrowCategory cat = (ThrowCategory) c;
		for (IGameEntry ientry : game.getGameEntries()) {
			entry = (GameX01Entry) ientry;
			dThrow = entry.getPlayerThrow().get(player);
			if (dThrow != null) {
				val = dThrow.getScore();
				if (cat.isInCategory(val)) {
					result++;
				}
			}
		}
		return (double) result;
	}

}
