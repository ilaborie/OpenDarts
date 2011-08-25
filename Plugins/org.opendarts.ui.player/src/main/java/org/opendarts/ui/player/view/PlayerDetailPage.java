package org.opendarts.ui.player.view;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.IFormPart;
import org.opendarts.core.model.player.IComputerPlayer;
import org.opendarts.core.model.player.IPlayer;

/**
 * The Class ComputerDetailPage.
 */
public class PlayerDetailPage extends DetailsPage<IComputerPlayer> {

	/** The player. */
	private IPlayer player;

	/** The lbl uuid. */
	private Label lblUuid;

	/**
	 * Instantiates a new computer detail page.
	 */
	public PlayerDetailPage() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.player.view.DetailsPage#createInternalDetail(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createInternalDetail(Composite parent) {
		Label lbl = this.getToolkit().createLabel(parent, "uuid:");
		GridDataFactory.fillDefaults().applyTo(lbl);
		
		this.lblUuid = this.getToolkit().createLabel(parent, "");
		GridDataFactory.fillDefaults().applyTo(this.lblUuid);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IPartSelectionListener#selectionChanged(org.eclipse.ui.forms.IFormPart, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		this.player = (IPlayer) ((IStructuredSelection) selection)
				.getFirstElement();
		if (this.player != null) {
			this.getSection().setText(this.player.getName());
			this.lblUuid.setText(this.player.getUuid());
		} else {
			this.getSection().setText("");
			this.lblUuid.setText("");
		}
	}

}
