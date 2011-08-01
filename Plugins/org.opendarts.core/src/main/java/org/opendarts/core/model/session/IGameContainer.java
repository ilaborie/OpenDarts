package org.opendarts.core.model.session;

import java.util.List;

/**
 * The Interface IGameContainer.
 *
 * @param <T> the game type
 */
public interface IGameContainer<T> extends IAbstractGame {
	/**
	 * Gets the current set.
	 *
	 * @return the current set
	 */
	T getCurrentGame();

	/**
	 * Gets the all set.
	 *
	 * @return the all set
	 */
	List<T> getAllGame();

	/**
	 * Adds the game.
	 *
	 * @param t the t
	 */
	void addGame(T t);

}
