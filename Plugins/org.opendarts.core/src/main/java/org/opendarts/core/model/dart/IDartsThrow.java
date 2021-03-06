package org.opendarts.core.model.dart;

import java.util.List;

/**
 * The Interface IDartsThrow.
 */
public interface IDartsThrow {

	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	int getScore();
	
	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	long getTimestamp();

	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	List<IDart> getDarts();

}
