/*
 * 
 */
package org.opendarts.prototype.ui.x01.label;

import java.text.MessageFormat;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.forms.IFormColors;
import org.opendarts.prototype.ProtoPlugin;
import org.opendarts.prototype.internal.model.dart.ThreeDartThrow;
import org.opendarts.prototype.internal.model.game.x01.BrokenX01DartsThrow;
import org.opendarts.prototype.internal.model.game.x01.DummyX01Entry;
import org.opendarts.prototype.internal.model.game.x01.GameX01Entry;
import org.opendarts.prototype.internal.model.game.x01.WinningX01DartsThrow;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.ui.ISharedImages;
import org.opendarts.prototype.ui.utils.OpenDartsFormsToolkit;

/**
 * The Class ScoreLabelProvider.
 */
public class ScoreLabelProvider extends ColumnLabelProvider {

	/** The player. */
	private IPlayer player;

	/**
	 * Instantiates a new score label provider.
	 *
	 * @param player the player
	 */
	public ScoreLabelProvider(IPlayer player) {
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
			} else if (dartThrow instanceof WinningX01DartsThrow) {
				WinningX01DartsThrow winThrow = (WinningX01DartsThrow) dartThrow;
				result = MessageFormat.format("+{0} ({1})",
						winThrow.getNbDartToFinish(),
						gameEntry.getNbPlayedDart());
			} else {
				result = String.valueOf(dartThrow.getScore());
			}
			return result;
		} else if (element instanceof DummyX01Entry) {
			return "";
		}
		return super.getText(element);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof GameX01Entry) {
			GameX01Entry gameEntry = (GameX01Entry) element;
			Integer score = gameEntry.getGame().getScore(this.player);
			if (score != null && score == 0) {
				return ProtoPlugin.getImage(ISharedImages.IMG_TICK_DECO);
			}
		}
		return super.getImage(element);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getBackground(java.lang.Object)
	 */
	@Override
	public Color getBackground(Object element) {
		if (element instanceof DummyX01Entry) {
			return OpenDartsFormsToolkit.getToolkit().getColors()
					.getColor(IFormColors.H_GRADIENT_START);
		} else if (element instanceof GameX01Entry) {
			GameX01Entry entry = (GameX01Entry) element;
			ThreeDartThrow dartThrow = entry.getPlayerThrow().get(this.player);
			if (dartThrow instanceof BrokenX01DartsThrow) {
				return OpenDartsFormsToolkit.getToolkit().getColors()
						.getColor(OpenDartsFormsToolkit.COLOR_BROKEN);
			} else if (dartThrow instanceof WinningX01DartsThrow) {
				return OpenDartsFormsToolkit.getToolkit().getColors()
						.getColor(OpenDartsFormsToolkit.COLOR_WINNING);
			}
		}
		return super.getBackground(element);
	}

}
