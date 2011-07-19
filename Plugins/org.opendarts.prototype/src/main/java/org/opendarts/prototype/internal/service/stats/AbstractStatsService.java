package org.opendarts.prototype.internal.service.stats;

import java.util.concurrent.CopyOnWriteArraySet;

import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.session.ISession;
import org.opendarts.prototype.model.session.ISet;
import org.opendarts.prototype.model.stats.IStatEntry;
import org.opendarts.prototype.model.stats.IStatValue;
import org.opendarts.prototype.model.stats.IStats;
import org.opendarts.prototype.service.stats.IStatService;
import org.opendarts.prototype.service.stats.IStatsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public abstract class AbstractStatsService implements IStatService {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(AbstractStatsService.class);

	/** The listeners. */
	private final CopyOnWriteArraySet<IStatsListener> listeners;

	/**
	 * Instantiates a new abstract stats service.
	 */
	public AbstractStatsService() {
		super();
		this.listeners = new CopyOnWriteArraySet<IStatsListener>();
	}

	@Override
	public IStats<ISession> getSessionStats(ISession session) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IStats<ISet> getSetStats(ISet set) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IStats<IGame> getGameStats(IGame game) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.stats.IStatService#addStatsListener(org.opendarts.prototype.service.stats.IStatsListener)
	 */
	@Override
	public <T> void addStatsListener(IStatsListener<T> listener) {
		this.listeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.service.stats.IStatService#removeStatsListener(org.opendarts.prototype.service.stats.IStatsListener)
	 */
	@Override
	public <T> void removeStatsListener(IStatsListener<T> listener) {
		this.listeners.remove(listener);
	}

	/**
	 * Fire entry created.
	 *
	 * @param stats the stats
	 * @param entry the entry
	 */
	@SuppressWarnings({ "unchecked" })
	protected void fireEntryCreated(IStats stats, IStatEntry entry) {
		for (IStatsListener listener : this.listeners) {
			try {
				listener.createdEntry(stats, entry);
			} catch (Throwable t) {
				LOG.error("Error in listener", t);
			}
		}
	}

	/**
	 * Fire entry updated.
	 *
	 * @param stats the stats
	 * @param oldValue the old value
	 * @param entry the entry
	 */
	@SuppressWarnings({ "unchecked" })
	protected void fireEntryUpdated(IStats stats, IStatValue oldValue,
			IStatEntry entry) {
		for (IStatsListener listener : this.listeners) {
			try {
				listener.updatedEntry(stats, oldValue, entry);
			} catch (Throwable t) {
				LOG.error("Error in listener", t);
			}
		}
	}

}
