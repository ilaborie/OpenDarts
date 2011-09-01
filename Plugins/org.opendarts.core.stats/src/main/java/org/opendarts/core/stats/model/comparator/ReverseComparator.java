package org.opendarts.core.stats.model.comparator;

import java.util.Comparator;

/**
 * The Class ReverseComparator.
 *
 * @param <T> the generic type
 */
public class ReverseComparator<T> implements Comparator<T> {

	/** The comparator. */
	private final Comparator<T> comparator;

	/**
	 * Instantiates a new reverse comparator.
	 *
	 * @param comparator the comparator
	 */
	public ReverseComparator(Comparator<T> comparator) {
		super();
		this.comparator = comparator;
	}

	/**
	 * Compare.
	 *
	 * @param o1 the o1
	 * @param o2 the o2
	 * @return the int
	 */
	@Override
	public int compare(T o1, T o2) {
		return this.comparator.compare(o2, o1);
	}
}