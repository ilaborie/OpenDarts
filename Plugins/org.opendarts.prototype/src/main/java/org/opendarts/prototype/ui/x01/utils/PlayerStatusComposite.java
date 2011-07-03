package org.opendarts.prototype.ui.x01.utils;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;
import org.opendarts.prototype.internal.model.game.x01.GameX01;
import org.opendarts.prototype.internal.model.session.GameSet;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.model.session.ISession;
import org.opendarts.prototype.model.session.ISessionListener;
import org.opendarts.prototype.model.session.ISetListener;
import org.opendarts.prototype.model.session.SessionEvent;
import org.opendarts.prototype.model.session.SetEvent;
import org.opendarts.prototype.ui.utils.OpenDartsFormsToolkit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class PlayerStatusComposite.
 * XXX use statistics service
 */
public class PlayerStatusComposite implements ISetListener, ISessionListener {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(PlayerStatusComposite.class);

	/** The player. */
	private final IPlayer player;

	/** The game. */
	private final GameX01 game;

	/** The toolkit. */
	private final OpenDartsFormsToolkit toolkit;

	/** The main. */
	private final Composite main;

	/** The set. */
	private final GameSet set;

	/** The session. */
	private final ISession session;

	/** The lbl legs. */
	private Label lblLegs;

	/** The lbl sets. */
	private Label lblSets;

	/**
	 * Instantiates a new player status composite.
	 *
	 * @param parent the parent
	 * @param player the player
	 * @param game the game
	 */
	public PlayerStatusComposite(Composite parent, IPlayer player, GameX01 game) {
		super();
		this.player = player;
		this.game = game;
		this.set = this.game.getParentSet();
		this.session = this.set.getParentSession();
		this.toolkit = OpenDartsFormsToolkit.getToolkit();
		this.main = this.toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().applyTo(this.main);
		this.createContents(this.main);

		// listener
		this.set.addListener(this);
		this.session.addListener(this);
	}

	/**
	 * Gets the control.
	 *
	 * @return the control
	 */
	public Composite getControl() {
		return this.main;
	}

	/**
	 * Creates the contents.
	 *
	 * @param parent the parent
	 */
	private void createContents(Composite client) {
		GridDataFactory childData = GridDataFactory.fillDefaults().grab(true,
				false);

		// Session
		Composite cmpSession = this.createSessionComposite(client);
		childData.copy().applyTo(cmpSession);

		// Total
		Composite cmpTotal = this.createTotalComposite(client);
		childData.copy().applyTo(cmpTotal);

		// Best
		Composite cmpBest = this.createBestComposite(client);
		childData.copy().applyTo(cmpBest);

		// Average
		Composite cmpAvg = this.createAverageComposite(client);
		childData.copy().applyTo(cmpAvg);
	}

	/**
	 * Creates the session composite.
	 *
	 * @param parent the parent
	 * @param player the player
	 * @return the composite
	 */
	private Composite createSessionComposite(Composite parent) {
		Composite main = this.toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().applyTo(main);

		// Section
		Section section = this.toolkit.createSection(main,
				ExpandableComposite.SHORT_TITLE_BAR
						| ExpandableComposite.CLIENT_INDENT
						| ExpandableComposite.EXPANDED);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
		section.setText("Session");

		// Section body
		Composite client = this.toolkit.createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(client);

		Label lbl;
		GridDataFactory lblData = GridDataFactory.fillDefaults().grab(true,
				false);
		GridDataFactory valData = GridDataFactory.fillDefaults().align(SWT.END,
				SWT.CENTER);

		// Legs
		lbl = this.toolkit.createLabel(client, "Legs:");
		lblData.copy().applyTo(lbl);

		int win = this.set.getWinningGames(this.player);
		this.lblLegs = this.toolkit.createLabel(client, String.valueOf(win));
		valData.copy().applyTo(this.lblLegs);

		// Sets
		lbl = this.toolkit.createLabel(client, "Sets:");
		lblData.copy().applyTo(lbl);

		win = this.session.getWinningSet(this.player);
		this.lblSets = this.toolkit.createLabel(client, String.valueOf(win));
		valData.copy().applyTo(this.lblSets);

		// End section
		this.toolkit.paintBordersFor(client);
		section.setClient(client);
		return main;
	}

	/**
	 * Creates the total composite.
	 *
	 * @param parent the parent
	 * @param player the player
	 * @return the composite
	 */
	private Composite createTotalComposite(Composite parent) {
		Composite main = this.toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().applyTo(main);

		// Section
		Section section = this.toolkit.createSection(main,
				ExpandableComposite.SHORT_TITLE_BAR
						| ExpandableComposite.CLIENT_INDENT
						| ExpandableComposite.EXPANDED);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
		section.setText("Totals");

		// Section body
		Composite client = this.toolkit.createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(client);

		Label lbl;
		GridDataFactory lblData = GridDataFactory.fillDefaults().grab(true,
				false);
		GridDataFactory valData = GridDataFactory.fillDefaults().align(SWT.END,
				SWT.CENTER);
		// 180
		lbl = this.toolkit.createLabel(client, "180's:");
		lblData.copy().applyTo(lbl);

		lbl = this.toolkit.createLabel(client, "-");
		valData.copy().applyTo(lbl);

		// +140
		lbl = this.toolkit.createLabel(client, "+140:");
		lblData.copy().applyTo(lbl);

		lbl = this.toolkit.createLabel(client, "-");
		valData.copy().applyTo(lbl);

		// +100
		lbl = this.toolkit.createLabel(client, "+100:");
		lblData.copy().applyTo(lbl);

		lbl = this.toolkit.createLabel(client, "-");
		valData.copy().applyTo(lbl);

		// End section
		this.toolkit.paintBordersFor(client);
		section.setClient(client);
		return main;
	}

	/**
	 * Creates the best composite.
	 *
	 * @param parent the parent
	 * @param player the player
	 * @return the composite
	 */
	private Composite createBestComposite(Composite parent) {
		Composite main = this.toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().applyTo(main);

		// Section
		Section section = this.toolkit.createSection(main,
				ExpandableComposite.SHORT_TITLE_BAR
						| ExpandableComposite.CLIENT_INDENT
						| ExpandableComposite.EXPANDED);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
		section.setText("Best");

		// Section body
		Composite client = this.toolkit.createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(client);

		Label lbl;
		GridDataFactory lblData = GridDataFactory.fillDefaults().grab(true,
				false);
		GridDataFactory valData = GridDataFactory.fillDefaults().align(SWT.END,
				SWT.CENTER);
		// High out
		lbl = this.toolkit.createLabel(client, "High Out:");
		lblData.copy().applyTo(lbl);

		lbl = this.toolkit.createLabel(client, "-");
		valData.copy().applyTo(lbl);

		// Best leg
		lbl = this.toolkit.createLabel(client, "Best Leg:");
		lblData.copy().applyTo(lbl);

		lbl = this.toolkit.createLabel(client, "-");
		valData.copy().applyTo(lbl);

		// End section
		this.toolkit.paintBordersFor(client);
		section.setClient(client);
		return main;
	}

	/**
	 * Creates the average composite.
	 *
	 * @param parent the parent
	 * @param player the player
	 * @return the composite
	 */
	private Composite createAverageComposite(Composite parent) {
		Composite main = this.toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().applyTo(main);

		// Section
		Section section = this.toolkit.createSection(main,
				ExpandableComposite.SHORT_TITLE_BAR
						| ExpandableComposite.CLIENT_INDENT
						| ExpandableComposite.EXPANDED);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
		section.setText("Average");

		// Section body
		Composite client = this.toolkit.createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(client);

		Label lbl;
		GridDataFactory lblData = GridDataFactory.fillDefaults().grab(true,
				false);
		GridDataFactory valData = GridDataFactory.fillDefaults().align(SWT.END,
				SWT.CENTER);
		// By darts
		lbl = this.toolkit.createLabel(client, "By dart:");
		lblData.copy().applyTo(lbl);

		lbl = this.toolkit.createLabel(client, "-");
		valData.copy().applyTo(lbl);

		// 3Darts
		lbl = this.toolkit.createLabel(client, "3 darts:");
		lblData.copy().applyTo(lbl);

		lbl = this.toolkit.createLabel(client, "-");
		valData.copy().applyTo(lbl);

		// nb darts
		lbl = this.toolkit.createLabel(client, "Nb darts:");
		lblData.copy().applyTo(lbl);

		lbl = this.toolkit.createLabel(client, "-");
		valData.copy().applyTo(lbl);

		// End section
		this.toolkit.paintBordersFor(client);
		section.setClient(client);
		return main;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.session.ISetListener#notifySetEvent(org.opendarts.prototype.model.session.SetEvent)
	 */
	@Override
	public void notifySetEvent(SetEvent event) {
		if (this.set.equals(event.getSet())) {
			LOG.trace("New Set Event: {}", event);
			switch (event.getType()) {
				case NEW_CURRENT_GAME:
					int win = this.set.getWinningGames(this.player);
					this.lblLegs.setText(String.valueOf(win));
					break;
				default:
					break;
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.session.ISessionListener#notifySessionEvent(org.opendarts.prototype.model.session.SessionEvent)
	 */
	@Override
	public void notifySessionEvent(SessionEvent event) {
		if (this.session.equals(event.getSession())) {
			LOG.trace("New Session Event: {}", event);
			switch (event.getType()) {
				case NEW_CURRENT_SET:
					int win = this.session.getWinningSet(this.player);
					this.lblSets.setText(String.valueOf(win));
					break;
				default:
					break;
			}
		}
	}

}
