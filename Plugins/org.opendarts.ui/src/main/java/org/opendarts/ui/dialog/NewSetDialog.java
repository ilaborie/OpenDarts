package org.opendarts.ui.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.opendarts.core.model.game.IGameDefinition;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.ui.OpenDartsUiPlugin;
import org.opendarts.ui.label.GameDefinitionLabelProvider;
import org.opendarts.ui.service.IGameDefinitionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class NewSetDialog.
 */
public class NewSetDialog extends TitleAreaDialog implements
		ISelectionChangedListener {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(NewSetDialog.class);

	/** The last game definition. */
	private static IGameDefinition lastGameDefinition;

	/** The players. */
	private List<IPlayer> players;

	/** The game definition. */
	private IGameDefinition gameDefinition;

	/** The comp game def. */
	private IGameDefinitionComposite compGameDef;

	/** The editor id. */
	private String editorId;

	/** The body. */
	private Composite body;

	/** The main. */
	private Composite main;

	/** The cb games available. */
	private ComboViewer cbGamesAvailable;

	/**
	 * Instantiates a new new game dialog.
	 *
	 * @param parentShell the parent shell
	 */
	public NewSetDialog(Shell parentShell) {
		super(parentShell);
		this.setHelpAvailable(false);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("New Set ...");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#isResizable()
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);
		this.getShell().pack();
		return control;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite comp = (Composite) super.createDialogArea(parent);

		// Main composite
		this.main = new Composite(comp, SWT.NONE);
		GridLayoutFactory.fillDefaults().margins(5, 5).numColumns(2)
				.applyTo(this.main);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(this.main);

		List<IGameDefinitionProvider> allGameDefinition = OpenDartsUiPlugin
				.getAllService(IGameDefinitionProvider.class);

		// Combo for selecting game
		Label lblGameAvailable = new Label(this.main, SWT.WRAP);
		lblGameAvailable.setText("Available(s) games: ");
		GridDataFactory.fillDefaults().applyTo(lblGameAvailable);

		this.cbGamesAvailable = new ComboViewer(this.main);
		this.cbGamesAvailable
				.setLabelProvider(new GameDefinitionLabelProvider());

		this.cbGamesAvailable.setContentProvider(new ArrayContentProvider());
		this.cbGamesAvailable.addSelectionChangedListener(this);
		this.cbGamesAvailable.setInput(allGameDefinition);

		this.body = new Composite(this.main, SWT.NONE);
		GridDataFactory.fillDefaults().span(2, 1).grab(true, true)
				.applyTo(this.body);
		GridLayoutFactory.fillDefaults().applyTo(this.body);

		if (!allGameDefinition.isEmpty()) {
			IGameDefinitionProvider gdp = allGameDefinition.get(0);
			this.cbGamesAvailable.setSelection(new StructuredSelection(gdp));
		}

		return comp;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		this.compGameDef.createButtonsForButtonBar(parent);
		super.createButtonsForButtonBar(parent);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		LOG.debug("NewSet#ok");
		try {
			int type = this.notifyUpdate();
			switch (type) {
				case IMessageProvider.NONE:
				case IMessageProvider.WARNING:
				case IMessageProvider.INFORMATION:
					this.gameDefinition = this.compGameDef.getGameDefinition();
					this.editorId = this.compGameDef.getEditorId();
					lastGameDefinition = this.gameDefinition;
					super.okPressed();
				default:
					break;
			}
		} catch (Exception e) {
			this.setErrorMessage(e.getMessage());
		}
	}

	/**
	 * Gets the editor id.
	 *
	 * @return the editor id
	 */
	public String getEditorId() {
		return this.editorId;
	}

	/**
	 * Notify update.
	 *
	 * @return the int
	 */
	public int notifyUpdate() {
		List<ValidationEntry> list = this.compGameDef.validate();
		int type = IMessageProvider.NONE;
		ValidationEntry worstEntry = null;
		if (list != null) {
			for (ValidationEntry e : list) {
				if (type < e.getMessageType()) {
					worstEntry = e;
					type = e.getMessageType();
				}
			}
		}

		this.setMessage(null, IMessageProvider.NONE);
		if (worstEntry != null) {
			this.setMessage(worstEntry.getMessage(),
					worstEntry.getMessageType());
		}

		Button btn = this.getButton(OK);
		btn.setEnabled(type != IMessageProvider.ERROR);

		return type;
	}

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	public List<IPlayer> getPlayers() {
		return this.players;
	}

	/**
	 * Gets the game definition.
	 *
	 * @return the game definition
	 */
	public IGameDefinition getGameDefinition() {
		return this.gameDefinition;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		this.body.dispose();

		this.body = new Composite(this.main, SWT.NONE);
		GridDataFactory.fillDefaults().span(2, 1).grab(true, true)
				.applyTo(this.body);
		GridLayoutFactory.fillDefaults().applyTo(this.body);

		ISelection sel = this.cbGamesAvailable.getSelection();
		if (sel instanceof IStructuredSelection) {
			IGameDefinitionProvider gdp = (IGameDefinitionProvider) ((IStructuredSelection) sel)
					.getFirstElement();

			this.compGameDef = gdp.createGameDefinitionComposite();
			Composite composite = this.compGameDef.createSetConfiguration(this,
					this.body, lastGameDefinition);
			GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);

			this.main.layout(true);
			this.compGameDef.setFocus();
		}
	}

}
