package org.opendarts.core.export;

import java.io.File;

import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;

/**
 * The Interface IExportService.
 *
 * @param <O> the option generic type
 */
public interface IExportService<O extends IExportOptions> {

	/**
	 * Export.
	 *
	 * @param session the session
	 * @param file the file
	 * @param option the option
	 */
	void export(ISession session, File file, O option);

	/**
	 * Export.
	 *
	 * @param set the set
	 * @param file the file
	 * @param option the option
	 */
	void export(ISet set, File file, O option);

	/**
	 * Export.
	 *
	 * @param game the game
	 * @param file the file
	 * @param option the option
	 */
	void export(IGame game, File file, O option);
}
