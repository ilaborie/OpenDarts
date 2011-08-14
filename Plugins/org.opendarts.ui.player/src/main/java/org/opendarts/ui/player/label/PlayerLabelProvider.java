package org.opendarts.ui.player.label;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.opendarts.core.player.model.ComputerPlayer;
import org.opendarts.core.player.model.Player;
import org.opendarts.ui.OpenDartsUiPlugin;
import org.opendarts.ui.utils.ISharedImages;

/**
 * The Class PlayerLabelProvider.
 */
public class PlayerLabelProvider extends ColumnLabelProvider {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof ComputerPlayer) {
			return OpenDartsUiPlugin.getImage(ISharedImages.IMG_OBJ_COMPUTER);
		} else if (element instanceof Player) {
			return OpenDartsUiPlugin.getImage(ISharedImages.IMG_OBJ_USER);
		}
		return super.getImage(element);
	}

}
