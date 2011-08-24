package org.opendarts.ui.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.IPreferenceStore;
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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.opendarts.core.model.game.IGameDefinition;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.ui.OpenDartsUiPlugin;
import org.opendarts.ui.label.GameDefinitionLabelProvider;
import org.opendarts.ui.pref.IGeneralPrefs;
import org.opendarts.ui.service.IGameDefinitionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class NewSetDialog.
 */
public class NewSetDialog extends TitleAreaDialog implements
		ISelectionChangedListener, INewContainerDialog, SelectionListener {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(NewSetDialog.class);

	/** The last game definition. */
	private static IGameDefinition lastGameDefinition;

	/** The last game def provider. */
	private static IGameDefinitionProvider lastGameDefProvider;

	/** The players. */
	private List<IPlayer> players;

	/** The game definition. */
	private IGameDefinition gameDefinition;

	/** The comp game def. */
	private IGameDefinitionComposite compGameDef;

	/** The body. */
	private Composite body;

	/** The main. */
	private Composite main;

	/** The cb games available. */
	private ComboViewer cbGamesAvailable;

	/** The btn store default. */
	private Button btnStoreDefault;

	/** The game def provider. */
	private IGameDefinitionProvider gameDefProvider;

	/**
	 * Instantiates a new new game dialog.
	 *
	 * @param parentShell the parent shell
	 */
	public NewSetDialog(Shell parentShell) {
		super(parentShell);
		this.setHelpAvailable(false);

		IPreferenceStore store = OpenDartsUiPlugin.getOpenDartsPreference();
		// get Game Def Provider
		if (lastGameDefProvider != null) {
			this.gameDefProvider = lastGameDefProvider;
		} else {
			String providerName = store
					.getString(IGeneralPrefs.DEFAUlT_GAME_DEFINITION_NAME);
			List<IGameDefinitionProvider> providers = OpenDartsUiPlugin
					.getAllService(IGameDefinitionProvider.class);
			for (IGameDefinitionProvider provider : providers) {
				if (providerName != null
						&& providerName.equals(provider.getName())) {
					this.gameDefProvider = provider;
					break;
				}
			}

			if (this.gameDefProvider == null && !providers.isEmpty()) {
				this.gameDefProvider = providers.get(0);
			}
		}

		// Game definition
		if (lastGameDefinition != null) {
			this.gameDefinition = lastGameDefinition;
		} else if (this.gameDefProvider != null) {
			String def = store.getString(IGeneralPrefs.DEFAUlT_GAME_DEFINITION);
			this.gameDefinition = this.gameDefProvider
					.getGameDefinitionFromString(def);
		}
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

		// Set current gameDefinitionProvider
		if (this.gameDefProvider != null) {
			this.cbGamesAvailable.setSelection(new StructuredSelection(
					this.gameDefProvider));
		}
		return comp;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		((GridLayout) parent.getLayout()).numColumns++;
		this.btnStoreDefault = new Button(parent, SWT.PUSH);
		this.btnStoreDefault.setText("Set as default");
		this.btnStoreDefault.addSelectionListener(this);
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
					lastGameDefinition = this.gameDefinition;
					lastGameDefProvider = this.gameDefProvider;
					super.okPressed();
				default:
					break;
			}
		} catch (Exception e) {
			this.setErrorMessage(e.getMessage());
		}
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
			this.gameDefProvider = (IGameDefinitionProvider) ((IStructuredSelection) sel)
					.getFirstElement();

			this.compGameDef = this.gameDefProvider
					.createGameDefinitionComposite();
			Composite composite = this.compGameDef.createSetConfiguration(this,
					this.body, this.gameDefinition);
			GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);

			this.main.layout(true);
			this.compGameDef.setFocus();
		}
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
		this.gameDefinition = this.compGameDef.getGameDefinition();
		IPreferenceStore store = OpenDartsUiPlugin.getOpenDartsPreference();
		if (this.gameDefProvider != null) {
			store.setValue(IGeneralPrefs.DEFAUlT_GAME_DEFINITION,
					this.gameDefProvider.getGameDefinitionAsString(this.gameDefinition));
			store.setValue(IGeneralPrefs.DEFAUlT_GAME_DEFINITION_NAME,
					this.gameDefProvider.getName());
		}
	}

}
