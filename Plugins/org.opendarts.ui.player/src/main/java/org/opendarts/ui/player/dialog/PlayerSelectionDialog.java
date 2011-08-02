package org.opendarts.ui.player.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.service.player.IPlayerService;
import org.opendarts.ui.OpenDartsUiPlugin;
import org.opendarts.ui.label.PlayerLabelProvider;
import org.opendarts.ui.utils.ISharedImages;

/**
 * The Class PlayerSelectionDialog.
 */
public class PlayerSelectionDialog extends ElementListSelectionDialog{
	
	/** The player service. */
	private final IPlayerService playerService;
	
	private final List<IPlayer> players;

	/**
	 * Instantiates a new player selection dialog.
	 *
	 * @param parent the parent
	 */
	public PlayerSelectionDialog(Shell parent) {
		super(parent, new PlayerLabelProvider());
		this.setImage(OpenDartsUiPlugin.getImage(ISharedImages.IMG_OBJ_USER));
		this.setTitle("Select Players");
		this.playerService = OpenDartsUiPlugin.getService(IPlayerService.class);
		this.players = new ArrayList<IPlayer>();
		
		this.setElements(this.playerService.getAllPlayers().toArray());
		this.setEmptyListMessage("Please select at least one player");
		this.setHelpAvailable(false);
		this.setMessage("Choose player(s)");
		this.setMultipleSelection(true);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.SelectionStatusDialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		this.players.clear();
		for (Object obj: this.getSelectedElements() ) {
			players.add((IPlayer) obj);
		}
		super.okPressed();
	}
	
	
	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	public List<IPlayer> getPlayers() {
		return players;
	}

}
