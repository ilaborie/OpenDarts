package org.opendarts.ui.player.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.ui.OpenDartsUiPlugin;
import org.opendarts.ui.player.label.PlayerLabelProvider;

public class PlayesOrderComposite extends Composite implements
		ISelectionChangedListener, SelectionListener {

	/** The players. */
	private final List<IPlayer> players;

	/** The current player. */
	private final List<IPlayer> currentPlayers;

	/** The btn up. */
	private Button btnUp;

	/** The btn down. */
	private Button btnDown;

	/** The table players. */
	private TableViewer tablePlayers;

	/**
	 * Instantiates a new player selection composite.
	 *
	 * @param parent the parent
	 * @param players the players
	 */
	public PlayesOrderComposite(Composite parent, List<IPlayer> players) {
		super(parent, SWT.NONE);

		this.players = new ArrayList<IPlayer>(players);
		this.currentPlayers = new ArrayList<IPlayer>(this.players);

		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(this);
		this.createContents(this);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Composite#setFocus()
	 */
	@Override
	public boolean setFocus() {
		return this.btnUp.setFocus();
	}

	/**
	 * Creates the contents.
	 *
	 * @param parent the parent
	 */
	protected void createContents(Composite parent) {
		this.tablePlayers = new TableViewer(parent, SWT.V_SCROLL | SWT.SINGLE
				| SWT.BORDER);
		GridDataFactory.fillDefaults().hint(100, 60).grab(true, true)
				.applyTo(this.tablePlayers.getTable());
		this.tablePlayers.setLabelProvider(new PlayerLabelProvider());
		this.tablePlayers.setContentProvider(new ArrayContentProvider());
		this.tablePlayers.setInput(this.players);
		this.tablePlayers.addSelectionChangedListener(this);

		// buttons
		Composite cmpBtn = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.BEGINNING)
				.applyTo(cmpBtn);
		GridLayoutFactory.fillDefaults().applyTo(cmpBtn);

		// up
		this.btnUp = new Button(cmpBtn, SWT.PUSH);
		this.btnUp.setImage(OpenDartsUiPlugin
				.getImage(org.opendarts.ui.utils.ISharedImages.IMG_UP));
		GridDataFactory.fillDefaults().applyTo(this.btnUp);
		this.btnUp.addSelectionListener(this);

		// Down
		this.btnDown = new Button(cmpBtn, SWT.PUSH);
		this.btnDown.setImage(OpenDartsUiPlugin
				.getImage(org.opendarts.ui.utils.ISharedImages.IMG_DOWN));
		GridDataFactory.fillDefaults().applyTo(this.btnDown);
		this.btnDown.addSelectionListener(this);

		this.updateButtonsState();
	}

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	public List<IPlayer> getPlayers() {
		return Collections.unmodifiableList(this.players);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection sel = (IStructuredSelection) this.tablePlayers
				.getSelection();
		this.currentPlayers.clear();
		if (!sel.isEmpty()) {
			for (Object obj : sel.toArray()) {
				this.currentPlayers.add((IPlayer) obj);
			}
		}
		this.updateButtonsState();
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
		Object obj = e.getSource();
		if (obj.equals(this.btnUp)) {
			int index = this.players.indexOf(this.currentPlayers.get(0));
			Collections.swap(this.players, index, index - 1);
			this.tablePlayers.setInput(this.players);
			this.updateButtonsState();
		} else if (obj.equals(this.btnDown)) {
			int index = this.players.indexOf(this.currentPlayers.get(0));
			Collections.swap(this.players, index, index + 1);
			this.tablePlayers.setInput(this.players);
			this.updateButtonsState();
		}
	}

	/**
	 * Update buttons state.
	 */
	private void updateButtonsState() {
		if (this.currentPlayers.size() == 1) {
			int index = this.players.indexOf(this.currentPlayers.get(0));
			this.btnUp.setEnabled(index > 0);
			this.btnDown.setEnabled(index < (this.players.size() - 1));
		} else {
			this.btnDown.setEnabled(false);
			this.btnUp.setEnabled(false);
		}
	}

}
