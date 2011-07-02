/*
 * 
 */
package org.opendarts.prototype.ui.editor.x01;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Font;
import org.opendarts.prototype.internal.model.dart.ThreeDartThrow;
import org.opendarts.prototype.internal.model.game.x01.GameX01;
import org.opendarts.prototype.internal.model.game.x01.GameX01Entry;
import org.opendarts.prototype.model.player.IPlayer;

/**
 * The Class ScoreLabelProvider.
 */
public class GoToLabelProvider extends ColumnLabelProvider {

	/** The player. */
	private IPlayer player;

	/**
	 * Instantiates a new score label provider.
	 *
	 * @param player the player
	 */
	public GoToLabelProvider(IPlayer player) {
		super();
		this.player = player;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof GameX01Entry) {
			String result;
			GameX01Entry gameEntry = (GameX01Entry) element;
			ThreeDartThrow dartThrow = gameEntry.getPlayerThrow().get(
					this.player);
			if (dartThrow == null) {
				result = "";
			} else {
				GameX01 game = gameEntry.getGame();
				result = String.valueOf(game.getScore(this.player));
			}
			return result;
		} else if (element instanceof DummyX01Entry) {
			DummyX01Entry entry = (DummyX01Entry) element;
			return String.valueOf(entry.getGame().getScoreToDo());
		}
		return super.getText(element);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getFont(java.lang.Object)
	 */
	@Override
	public Font getFont(Object element) {
		return JFaceResources.getFontRegistry().getBold(
				JFaceResources.BANNER_FONT);
	}

}
