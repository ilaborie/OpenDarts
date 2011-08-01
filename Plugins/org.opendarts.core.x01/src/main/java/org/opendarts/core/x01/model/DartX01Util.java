package org.opendarts.core.x01.model;

import java.util.Arrays;

/**
 * The Class DartX01Util.
 */
public class DartX01Util {

	/**
	 * Could finish.
	 *
	 * @param score the score
	 * @param nbDart the number of dart
	 * @return true, if successful
	 */
	public static boolean couldFinish(int score, int nbDart) {
		boolean result = false;

		switch (nbDart) {
			case 1:
				result = ((score % 2) == 0) && ((score == 50) || (score <= 40));
				break;
			default:
				int base = ((nbDart - 1) * 60) + 39;
				result = (score < base)
						|| (Arrays.asList(base + 1, base + 2, base + 5,
								base + 8, base + 11).contains(score));
				break;
		}

		return result;
	}

}
