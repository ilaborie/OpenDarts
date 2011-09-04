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

/**
 * The Class ElementStats.
 * 
 * @param <E>
 *            the element type
 */
public class ElementStats<E> implements IElementStats<E> {

	/** The element. */
	private final E element;

	/** The players stats. */
	private final Map<IPlayer, Map<String, IStatsEntry<?>>> playersEntries;

	/** The players stats. */
	private final Map<IPlayer, IStats<E>> playersStats;

	/**
	 * Instantiates a new element stats.
	 * 
	 * @param element
	 *            the element
	 */
	public ElementStats(E element) {
		super();
		this.element = element;
		this.playersEntries = new HashMap<IPlayer, Map<String, IStatsEntry<?>>>();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opendarts.core.stats.model.IElementStats#getStatsKeys()
	 */
	@Override
	public List<String> getStatsKeys() {
		Set<String> result = new TreeSet<String>();
		for (Map<String, IStatsEntry<?>> entry : this.playersEntries.values()) {
			result.addAll(entry.keySet());
		}
		return new ArrayList<String>(result);
	}

	/**
	 * Adds the player stats.
	 * 
	 * @param player
	 *            the player
	 * @param stats
	 *            the stats
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addPlayerStats(IPlayer player, IStats<E> stats) {
		// Stats
		this.playersStats.put(player, stats);

		// Entries
		Map<String, IStatsEntry<?>> map = this.playersEntries.get(player);
		if (map == null) {
			map = new HashMap<String, IStatsEntry<?>>();
			this.playersEntries.put(player, map);
		}
		Map<String, IStatsEntry> allEntries = stats.getAllEntries();
		map.putAll((Map<String, ? extends IStatsEntry<E>>) allEntries);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opendarts.core.stats.model.IElementStats#getStatsEntry(org.opendarts
	 * .core.model.player.IPlayer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <V> IStatsEntry<V> getStatsEntry(IPlayer player, String key) {
		IStatsEntry<V> result = null;
		Map<String, IStatsEntry<?>> map = this.playersEntries.get(player);
		if (map != null) {
			result = (IStatsEntry<V>) map.get(key);
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see org.opendarts.core.stats.model.IElementStats#getStatsEntries(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <V> Map<IPlayer, IStatsEntry<V>> getStatsEntries(String key) {
		Map<IPlayer,IStatsEntry<V>> result = new HashMap<IPlayer, IStatsEntry<V>>();
		IStatsEntry<?> stEntry;
		for (java.util.Map.Entry<IPlayer, Map<String, IStatsEntry<?>>> entry : this.playersEntries.entrySet()) {
			stEntry =entry.getValue().get(key);
			if (stEntry!=null) {
				result.put(entry.getKey(), (IStatsEntry<V>) stEntry);
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opendarts.core.stats.model.IElementStats#getPlayerStats(org.opendarts
	 * .core.model.player.IPlayer)
	 */
	@Override
	public IStats<E> getPlayerStats(IPlayer player) {
		return this.playersStats.get(player);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opendarts.core.stats.model.IElementStats#getPlayers()
	 */
	@Override
	public List<IPlayer> getPlayers() {
		return new ArrayList<IPlayer>(this.playersStats.keySet());
	}

	/*
	 * (non-Javadoc)
	 * 
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
	 * 
	 * @param key
	 * 
	 * @return the best value
	 */
	@SuppressWarnings("unchecked")
	public <V> IStatValue<V> getBestValue(String key) {
		IStatValue<V> result = null;

		IStatsEntry<V> statsEntry;
		Comparator<V> comparator;
		V value;
		IStatValue<V> pVal;
		for (Map<String, IStatsEntry<?>> map : this.playersEntries.values()) {
			statsEntry = (IStatsEntry<V>) map.get(key);
			comparator = statsEntry.getComparator();
			if (comparator != null) {
				if (result == null || result.getValue() == null) {
					result = statsEntry.getValue();
				} else {
					value = result.getValue();
					pVal = statsEntry.getValue();
					if (pVal != null
							&& comparator.compare(value, pVal.getValue()) < 0) {
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
		 * @param key
		 *            the key
		 */
		private Entry(String key, ElementStats<E> eltStats) {
			super();
			this.key = key;
			this.eltStats = eltStats;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.opendarts.core.stats.model.IElementStats.IEntry#getKey()
		 */
		@Override
		public String getKey() {
			return this.key;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.opendarts.core.stats.model.IElementStats.IEntry#getPlayerEntry
		 * (org.opendarts.core.model.player.IPlayer)
		 */
		@Override
		public IStatsEntry<E> getPlayerEntry(IPlayer player) {
			return this.eltStats.getStatsEntry(player, this.key);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.opendarts.core.stats.model.IElementStats.IEntry#getBestValue()
		 */
		@Override
		public IStatValue<E> getBestValue() {
			return this.eltStats.getBestValue(this.key);
		}
	}

}
