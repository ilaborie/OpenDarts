/*
 * 
 */
package org.opendarts.core.model.dart;

import java.util.List;

/**
 * The Interface IComputerThrow.
 */
public interface IComputerThrow {

	/**
	 * Gets the darts throw.
	 *
	 * @return the darts throw
	 */
	IDartsThrow getDartsThrow();

	/**
	 * Gets the wished.
	 *
	 * @return the wished
	 */
	List<IDart> getWished();

	/**
	 * Gets the done.
	 *
	 * @return the done
	 */
	List<IDart> getDone();

}
