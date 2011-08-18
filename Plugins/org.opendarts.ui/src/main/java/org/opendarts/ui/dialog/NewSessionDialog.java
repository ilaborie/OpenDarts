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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
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
public class NewSessionDialog extends TitleAreaDialog implements
		ISelectionChangedListener, INewContainerDialog, SelectionListener {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(NewSessionDialog.class);

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

	/** The spi nb sets. */
	private Spinner spiNbSets;
	
	/** The nb set. */
	private int nbSets = 5;

	/**
	 * Instantiates a new new game dialog.
	 *
	 * @param parentShell the parent shell
	 */
	public NewSessionDialog(Shell parentShell) {
		super(parentShell);
		this.setHelpAvailable(false);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("New Session ...");
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

		Label lbl;
		// Combo for selecting game
		lbl = new Label(this.main, SWT.WRAP);
		GridDataFactory.fillDefaults().applyTo(lbl);
		lbl.setText("Available(s) games: ");

		this.cbGamesAvailable = new ComboViewer(this.main);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(this.cbGamesAvailable.getControl());
		this.cbGamesAvailable
				.setLabelProvider(new GameDefinitionLabelProvider());

		this.cbGamesAvailable.setContentProvider(new ArrayContentProvider());
		this.cbGamesAvailable.addSelectionChangedListener(this);
		this.cbGamesAvailable.setInput(allGameDefinition);
		
		// Nb Set
		lbl = new Label(this.main, SWT.WRAP);
		GridDataFactory.fillDefaults().applyTo(lbl);
		lbl.setText("Nb set: ");
		
		this.spiNbSets = new Spinner(this.main, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(this.spiNbSets);
		this.spiNbSets.setMinimum(1);
		this.spiNbSets.setIncrement(1);
		this.spiNbSets.setPageIncrement(5);
		this.spiNbSets.addSelectionListener(this);
		this.spiNbSets.setSelection(this.nbSets);
		
		// Game Definition
		this.body = new Composite(this.main, SWT.NONE);
		GridDataFactory.fillDefaults().span(2, 1).grab(true, true)
				.applyTo(this.body);
		GridLayoutFactory.fillDefaults().applyTo(this.body);

		if (!allGameDefinition.isEmpty()) {
			IGameDefinitionProvider gdp = allGameDefinition.get(0);
			this.cbGamesAvailable.setSelection(new StructuredSelection(gdp));
		}
		this.spiNbSets.setFocus();
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
	@Override
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
	
	/**
	 * Gets the nb sets.
	 *
	 * @return the nb sets
	 */
	public int getNbSets() {
		return this.nbSets;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// Nothing to do
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		Object src = e.getSource();
		if (this.spiNbSets.equals(src)) {
			this.nbSets = this.spiNbSets.getSelection();
		}
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
