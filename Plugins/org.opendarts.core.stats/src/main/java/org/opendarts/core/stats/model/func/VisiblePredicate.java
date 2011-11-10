package org.opendarts.core.stats.model.func;

import org.opendarts.core.stats.model.IElementStats.IEntry;

import com.google.common.base.Predicate;

/**
 * The Class VisiblePredicate.
 */
public class VisiblePredicate implements Predicate<IEntry<?>>{
	
	/* (non-Javadoc)
	 * @see com.google.common.base.Predicate#apply(java.lang.Object)
	 */
	@Override
	public boolean apply(IEntry<?> input) {
		return input.isVisible();
	}
}
