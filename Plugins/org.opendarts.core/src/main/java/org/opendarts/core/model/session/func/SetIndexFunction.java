package org.opendarts.core.model.session.func;

import java.text.MessageFormat;

import org.opendarts.core.model.session.ISet;

import com.google.common.base.Function;

/**
 * The Class SetPlayersFunction.
 */
public class SetIndexFunction implements Function<ISet, String> {

	/* (non-Javadoc)
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	@Override
	public String apply(ISet set) {
		int index = set.getParentSession().getAllGame().indexOf(set);
		return MessageFormat.format("#{0}", index + 1);
	}

}
