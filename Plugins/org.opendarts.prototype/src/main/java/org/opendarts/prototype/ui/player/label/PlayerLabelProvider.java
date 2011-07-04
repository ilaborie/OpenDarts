package org.opendarts.prototype.ui.player.label;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.opendarts.prototype.ProtoPlugin;
import org.opendarts.prototype.internal.model.player.ComputerPlayer;
import org.opendarts.prototype.internal.model.player.Player;
import org.opendarts.prototype.ui.ISharedImages;

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
			return ProtoPlugin.getImage(ISharedImages.IMG_OBJ_COMPUTER);
		} else if (element instanceof Player) {
			return ProtoPlugin.getImage(ISharedImages.IMG_OBJ_USER);
		}
		return super.getImage(element);
	}

}
