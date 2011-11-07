package org.opendarts.ui.export.service.impl;

import com.google.common.base.Function;

/**
 * The Class ScoreToString.
 */
public class ScoreToString implements Function<Integer, String> {

	/* (non-Javadoc)
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	@Override
	public String apply(Integer input) {
		String result;
		if (input==null || input==-1) {
			result = "-";
		} else {
			result = String.valueOf(input);
		}
		return result;
	}

}
