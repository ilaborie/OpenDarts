/*
 * 
 */
package org.opendarts.ui.stats.label;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.ui.OpenDartsUiPlugin;
import org.opendarts.ui.utils.ISharedImages;

/**
 * The Class StatsLabelProvider.
 */
public class StatsLabelProvider extends ColumnLabelProvider {

	/**
	 * Instantiates a new stats label provider.
	 */
	public StatsLabelProvider() {
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
		}
		return OpenDartsUiPlugin.getImage(key);
	}

}
