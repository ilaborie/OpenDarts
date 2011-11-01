package org.opendarts.ui.export.x01.defi.model;

import java.util.ArrayList;
import java.util.List;

import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.ui.export.model.Session;
import org.opendarts.ui.export.model.Set;

/**
 * The Class SessionX01.
 */
public class SessionDefi extends Session {

	/** The sets. */
	private List<Set> sets = null;

	/**
	 * Instantiates a new session x01.
	 *
	 * @param parent the parent
	 * @param statsServices the stats services
	 */
	public SessionDefi(ISession parent, List<IStatsService> statsServices) {
		super(parent, statsServices);
	}

	/**
	 * Gets the sets.
	 *
	 * @return the sets
	 */
	public List<Set> getSets() {
		if (sets == null) {
			this.sets = new ArrayList<Set>();
			for (ISet set : this.getElement().getAllGame()) {
				this.sets.add(new SetDefi(this, set, this.getStatsServices()));
			}
		}
		return this.sets;
	}

}
