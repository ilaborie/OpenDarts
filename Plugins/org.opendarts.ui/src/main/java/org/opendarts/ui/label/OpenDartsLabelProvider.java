/*
 * 
 */
package org.opendarts.ui.label;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.player.model.ComputerPlayer;
import org.opendarts.core.player.model.Player;
import org.opendarts.ui.OpenDartsUiPlugin;
import org.opendarts.ui.utils.ISharedImages;

/**
 * The Class OpenDartsLabelProvider.
 */
public class OpenDartsLabelProvider extends ColumnLabelProvider {

	/**
	 * Instantiates a new stats label provider.
	 */
	public OpenDartsLabelProvider() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		String result = super.getText(element);
		if (element instanceof ISession) {
			ISession session = (ISession) element;
			result = session.toString();
		} else if (element instanceof ISet) {
			ISet set = (ISet) element;
			result = set.toString();
		} else if (element instanceof IGame) {
			IGame game = (IGame) element;
			result = game.toString();
		} else if (element instanceof IPlayer) {
			IPlayer player = (IPlayer) element;
			result = player.toString();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		String key = null;
		if (element instanceof ISession) {
			key = ISharedImages.IMG_OBJ_SESSION;
		} else if (element instanceof ISet) {
			key = ISharedImages.IMG_OBJ_SET;
		} else if (element instanceof IGame) {
			key = ISharedImages.IMG_OBJ_GAME;
		} else if (element instanceof ComputerPlayer) {
			return OpenDartsUiPlugin.getImage(ISharedImages.IMG_OBJ_COMPUTER);
		} else if (element instanceof Player) {
			return OpenDartsUiPlugin.getImage(ISharedImages.IMG_OBJ_USER);
		}
		return OpenDartsUiPlugin.getImage(key);
	}

}
