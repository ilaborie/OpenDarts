package org.opendarts.ui.x01.chart.session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opendarts.core.model.dart.impl.ThreeDartsThrow;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.x01.model.GameX01Entry;
import org.opendarts.ui.x01.chart.Category;

/**
 * The Class SetThrowDistributionChartX01.
 *
 * @param <T> the generic type
 */
public class SessionThrowDistributionChartX01<T> extends
		SessionCategoryChartX01<T> {

	/** The distribution. */
	private final Map<IPlayer, int[]> distribution;

	/** The categories. */
	private List<Category> categories;

	/** The session. */
	private final ISession session;

	/**
	 * Instantiates a new session throw distribution chart x01.
	 *
	 * @param name the name
	 * @param statKey the stat key
	 * @param session the session
	 */
	public SessionThrowDistributionChartX01(String name, String statKey,
			ISession session) {
		super(name, statKey, session);
		this.distribution = new HashMap<IPlayer, int[]>();
		this.session = session;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.x01.chart.SessionCategoryChartX01#getCategories()
	 */
	@Override
	protected List<Category> getCategories() {
		if (this.categories == null) {
			this.categories = new ArrayList<Category>();
			Category category;
			for (int i = 1; i < 18; i++) {
				category = new Category(String.valueOf(i*10));
				this.categories.add(category);
			}
		}
		return this.categories;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.x01.chart.SessionCategoryChartX01#getValue(org.opendarts.ui.x01.chart.Category, org.opendarts.core.model.player.IPlayer)
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
			for (ISet set : this.session.getAllGame()) {
				for (IGame game : set.getAllGame()) {
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
			}
		}
		int i = (Integer.valueOf(c.getName())/10);
		return (double) values[i];
	}
}
