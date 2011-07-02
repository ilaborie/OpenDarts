package org.opendarts.prototype.ui.x01.utils;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.Section;
import org.opendarts.prototype.internal.model.game.x01.GameX01;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.ui.utils.OpenDartsFormsToolkit;

/**
 * The Class PlayerStatusComposite.
 * XXX use statistics service
 */
public class PlayerStatusComposite {

	/** The player. */
	private final IPlayer player;

	/** The game. */
	private final GameX01 game;

	/** The toolkit. */
	private final OpenDartsFormsToolkit toolkit;

	/** The main. */
	private final Composite main;

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
		this.toolkit = OpenDartsFormsToolkit.getToolkit();

		this.main = this.toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().applyTo(this.main);
		this.createContents(main);
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
		Section section = this.toolkit.createSection(main, Section.TWISTIE
				| Section.SHORT_TITLE_BAR | Section.CLIENT_INDENT
				| Section.EXPANDED);
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

		lbl = this.toolkit.createLabel(client, "-");
		valData.copy().applyTo(lbl);

		// Sets
		lbl = this.toolkit.createLabel(client, "Sets:");
		lblData.copy().applyTo(lbl);

		lbl = this.toolkit.createLabel(client, "-");
		valData.copy().applyTo(lbl);

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
		Section section = this.toolkit.createSection(main, Section.TWISTIE
				| Section.SHORT_TITLE_BAR | Section.CLIENT_INDENT
				| Section.EXPANDED);
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
		Section section = this.toolkit.createSection(main, Section.TWISTIE
				| Section.SHORT_TITLE_BAR | Section.CLIENT_INDENT
				| Section.EXPANDED);
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
		Section section = this.toolkit.createSection(main, Section.TWISTIE
				| Section.SHORT_TITLE_BAR | Section.CLIENT_INDENT
				| Section.EXPANDED);
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

}
