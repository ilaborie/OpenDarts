package org.opendarts.ui.x01.pref;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * The Class ListUtils.
 */
public final class ListUtils {

	/**
	 * Instantiates a new list util.
	 */
	private ListUtils() {
		super();
	}

	/**
	 * Gets the list as string.
	 *
	 * @param stats the stats
	 * @return the list as string
	 */
	public static String getListAsString(List<String> stats) {
		StringBuilder result = new StringBuilder();
		boolean isFirst = true;
		for (String s : stats) {
			if (isFirst) {
				isFirst = false;
			} else {
				result.append('\n');
			}
			result.append(s);
		}
		return result.toString();
	}

	/**
	 * Gets the string as list.
	 *
	 * @param s the s
	 * @return the string as list
	 */
	public static List<String> getStringAsList(String s) {
		List<String> result = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(s, "\n");
		String key;
		while (st.hasMoreTokens()) {
			key = st.nextToken();
			result.add(key.trim());
		}
		return result;
	}
}
