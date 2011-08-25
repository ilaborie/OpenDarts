package org.opendarts.ui.player.view;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.IFormPart;
import org.opendarts.core.model.player.IComputerPlayer;

/**
 * The Class ComputerDetailPage.
 */
public class ComputerDetailPage extends DetailsPage<IComputerPlayer> {

	/** The lbl level. */
	private Label lblLevel;
	
	/** The player. */
	private IComputerPlayer player;
	
	/**
	 * Instantiates a new computer detail page.
	 */
	public ComputerDetailPage() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.player.view.DetailsPage#createInternalDetail(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createInternalDetail(Composite parent) {
		Label lbl = this.getToolkit().createLabel(parent, "Level:");
		GridDataFactory.fillDefaults().applyTo(lbl);

		this.lblLevel = this.getToolkit().createLabel(parent, "");
		GridDataFactory.fillDefaults().applyTo(this.lblLevel);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IPartSelectionListener#selectionChanged(org.eclipse.ui.forms.IFormPart, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		this.player = (IComputerPlayer) ((IStructuredSelection) selection)
				.getFirstElement();
		if (this.player!= null) {
			this.getSection().setText(this.player.getName());
			this.lblLevel.setText(String.valueOf(this.player.getLevel()));
		} else {
			this.getSection().setText("");
			this.lblLevel.setText("");
		}
	}

}
