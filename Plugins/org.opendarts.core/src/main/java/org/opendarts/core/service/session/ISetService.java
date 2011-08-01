package org.opendarts.core.service.session;

import org.opendarts.core.model.game.IGameDefinition;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;

/**
 * The Interface ISetService.
 */
public interface ISetService {

	/**
	 * Creates the new set.
	 *
	 * @param session the session
	 * @param gameDefinition the game definition
	 * @return the i set
	 */
	ISet createNewSet(ISession session, IGameDefinition gameDefinition);

	/**
	 * Start set.
	 *
	 * @param set the set
	 */
	void startSet(ISet set);

	/**
	 * Cancel set.
	 *
	 * @param set the set
	 */
	void cancelSet(ISet set);

}
