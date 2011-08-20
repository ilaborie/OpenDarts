package org.opendarts.core.stats.model.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.stats.model.IElementStats;
import org.opendarts.core.stats.model.IStatValue;
import org.opendarts.core.stats.model.IStats;
import org.opendarts.core.stats.model.IStatsEntry;

// TODO: Auto-generated Javadoc
/**
 * The Class ElementStats.
 *
 * @param <E> the element type
 */
public class ElementStats<E> implements IElementStats<E> {

	/** The element. */
	private final E element;

	/** The players stats. */
	private final Map<IPlayer, Map<String, IStatsEntry<E>>> playersEntries;

	/** The players stats. */
	private final Map<IPlayer, IStats<E>> playersStats;

	/**
	 * Instantiates a new element stats.
	 *
	 * @param element the element
	 */
	public ElementStats(E element) {
		super();
		this.element = element;
		this.playersEntries = new HashMap<IPlayer, Map<String, IStatsEntry<E>>>();
		this.playersStats = new HashMap<IPlayer, IStats<E>>();
	}

	/**
	 * Gets the element.
	 *
	 * @return the element
	 */
	@Override
	public E getElement() {
		return this.element;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.model.IElementStats#getStatsKeys()
	 */
	@Override
	public List<String> getStatsKeys() {
		Set<String> result = new TreeSet<String>();
		for (Map<String, IStatsEntry<E>> entry : this.playersEntries.values()) {
			result.addAll(entry.keySet());
		}
		return new ArrayList<String>(result);
	}

	/**
	 * Adds the player stats.
	 *
	 * @param player the player
	 * @param stats the stats
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addPlayerStats(IPlayer player, IStats<E> stats) {
		// Stats
		this.playersStats.put(player, stats);

		// Entries
		Map<String, IStatsEntry<E>> map = this.playersEntries.get(player);
		if (map == null) {
			map = new HashMap<String, IStatsEntry<E>>();
			this.playersEntries.put(player, map);
		}
		Map<String, IStatsEntry> allEntries = stats.getAllEntries();
		map.putAll((Map<String, ? extends IStatsEntry<E>>) allEntries);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.model.IElementStats#getStatsEntry(org.opendarts.core.model.player.IPlayer, java.lang.String)
	 */
	@Override
	public IStatsEntry<E> getStatsEntry(IPlayer player, String key) {
		IStatsEntry<E> result = null;
		Map<String, IStatsEntry<E>> map = this.playersEntries.get(player);
		if (map != null) {
			result = map.get(key);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.model.IElementStats#getPlayerStats(org.opendarts.core.model.player.IPlayer)
	 */
	@Override
	public IStats<E> getPlayerStats(IPlayer player) {
		return this.playersStats.get(player);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.model.IElementStats#getPlayers()
	 */
	@Override
	public List<IPlayer> getPlayers() {
		return new ArrayList<IPlayer>(this.playersStats.keySet());
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.model.IElementStats#getStatsEntries()
	 */
	@Override
	public List<IEntry<E>> getStatsEntries() {
		List<IEntry<E>> result = new ArrayList<IEntry<E>>();
		for (String key : this.getStatsKeys()) {
			result.add(new Entry(key, this));
		}
		return result;
	}

	/**
	 * Gets the best value.
	 * @param key 
	 *
	 * @return the best value
	 */
	public IStatValue<E> getBestValue(String key) {
		IStatValue<E> result = null;

		IStatsEntry<E> statsEntry;
		Comparator<E> comparator;
		E value;
		IStatValue<E> pVal;
		for (Map<String, IStatsEntry<E>> map : this.playersEntries.values()) {
			statsEntry = map.get(key);
			comparator = statsEntry.getComparator();
			if (comparator != null) {
				if (result == null || result.getValue() == null) {
					result = statsEntry.getValue();
				} else {
					value = result.getValue();
					pVal = statsEntry.getValue();
					if (comparator.compare(value, pVal.getValue()) < 0) {
						result = pVal;
					}
				}
			}
		}
		return result;
	}

	/**
	 * The Class Entry.
	 */
	private final class Entry implements IEntry<E> {

		/** The key. */
		private final String key;
		private final ElementStats<E> eltStats;

		/**
		 * Instantiates a new entry.
		 *
		 * @param key the key
		 */
		private Entry(String key, ElementStats<E> eltStats) {
			super();
			this.key = key;
			this.eltStats = eltStats;
		}

		/* (non-Javadoc)
		 * @see org.opendarts.core.stats.model.IElementStats.IEntry#getKey()
		 */
		@Override
		public String getKey() {
			return this.key;
		}

		/* (non-Javadoc)
		 * @see org.opendarts.core.stats.model.IElementStats.IEntry#getPlayerEntry(org.opendarts.core.model.player.IPlayer)
		 */
		@Override
		public IStatsEntry<E> getPlayerEntry(IPlayer player) {
			return this.eltStats.getStatsEntry(player, this.key);
		}

		/* (non-Javadoc)
		 * @see org.opendarts.core.stats.model.IElementStats.IEntry#getBestValue()
		 */
		@Override
		public IStatValue<E> getBestValue() {
			return this.eltStats.getBestValue(this.key);
		}
	}

}
