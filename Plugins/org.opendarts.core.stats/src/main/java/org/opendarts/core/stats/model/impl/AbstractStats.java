package org.opendarts.core.stats.model.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.IStats;
import org.opendarts.core.stats.model.IStatsEntry;

/**
 * The Class SessionStats.
 */
@SuppressWarnings("rawtypes")
public class AbstractStats<T> implements IStats<T> {

	private final T element;

	/** The player. */
	private final IPlayer player;

	/** The entries map. */
	private final Map<String, IStatsEntry> entriesMap;

	/**
	 * Instantiates a new session stats.
	 *
	 * @param session the session
	 * @param player the player
	 */
	public AbstractStats(T element, IPlayer player) {
		super();
		this.element = element;
		this.player = player;
		this.entriesMap = new HashMap<String, IStatsEntry>();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.model.IStats#cleanStats(java.util.List)
	 */
	@Override
	public void cleanStats(List<String> keys) {
		Set<String> entries = new HashSet<String>(this.entriesMap.keySet());
		for (String key : entries) {
			if (!keys.contains(key)) {
				this.entriesMap.remove(key);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.stats.IStats#getElement()
	 */
	@Override
	public T getElement() {
		return this.element;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.stats.IStats#getPlayer()
	 */
	@Override
	public IPlayer getPlayer() {
		return this.player;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.stats.IStats#getAllEntries()
	 */
	@Override
	public Map<String, IStatsEntry> getAllEntries() {
		return Collections.unmodifiableMap(this.entriesMap);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.stats.IStats#getEntry(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <U> IStatsEntry<U> getEntry(String key) {
		return this.entriesMap.get(key);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.stats.IStats#addEntry(org.opendarts.prototype.model.stats.IStatsEntry)
	 */
	@Override
	public <U> void addEntry(IStatsEntry<U> entry) {
		this.entriesMap.put(entry.getKey(), entry);
	}

}
