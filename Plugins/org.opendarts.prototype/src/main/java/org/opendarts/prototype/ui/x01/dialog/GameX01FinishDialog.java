package org.opendarts.prototype.ui.x01.dialog;

import java.text.MessageFormat;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.FormDialog;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.opendarts.prototype.internal.model.dart.x01.DartX01Util;
import org.opendarts.prototype.internal.model.game.x01.GameX01;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.ui.utils.OpenDartsFormsToolkit;

/**
 * The Class GameX01FinishDialog.
 */
public class GameX01FinishDialog extends FormDialog {

	/** The nb dart. */
	private int nbDarts;

	/** The game. */
	private final GameX01 game;

	/** The player. */
	private final IPlayer player;

	/** The toolkit. */
	private final OpenDartsFormsToolkit toolkit;

	/** The score. */
	private final int score;

	/**
	 * Instantiates a new game x501 finish dialog.
	 *
	 * @param parentShell the parent shell
	 * @param game the game
	 * @param player the player
	 */
	public GameX01FinishDialog(Shell parentShell, GameX01 game, IPlayer player,
			int score) {
		super(parentShell);
		this.game = game;
		this.player = player;
		this.score = score;
		this.toolkit = OpenDartsFormsToolkit.getToolkit();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.FormDialog#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void createFormContent(IManagedForm mform) {
		String title = MessageFormat.format("{0} Finished ?", this.game,
				this.player);
		ScrolledForm form = mform.getForm();
		form.setText(title);
		this.toolkit.decorateFormHeading(form.getForm());

		Composite body = form.getBody();
		GridLayoutFactory.fillDefaults().margins(5, 5).applyTo(body);

		// Message
		String msg = MessageFormat.format("{0} has finished with {1} ?",
				this.player, this.score);
		Label lbl = this.toolkit.createLabel(body, msg);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(lbl);

		// main composite
		Composite main = this.toolkit.createComposite(body);
		GridLayoutFactory.fillDefaults().margins(10, 10).numColumns(3)
				.equalWidth(true).applyTo(main);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(main);

		GridDataFactory btnData = GridDataFactory.fillDefaults();
		Button btn;
		// Finish Buttons
		btn = this.toolkit.createButton(main, "One Dart", SWT.PUSH);
		btn.setEnabled(DartX01Util.couldFinish(this.score, 1));
		btn.addSelectionListener(new NbDartSelectionListener(1));
		btnData.copy().applyTo(btn);

		btn = this.toolkit.createButton(main, "Two Dart", SWT.PUSH);
		btn.setEnabled(DartX01Util.couldFinish(this.score, 2));
		btn.addSelectionListener(new NbDartSelectionListener(2));
		btnData.copy().applyTo(btn);

		btn = this.toolkit.createButton(main, "Three Dart", SWT.PUSH);
		btn.setEnabled(DartX01Util.couldFinish(this.score, 3));
		btn.addSelectionListener(new NbDartSelectionListener(3));
		btnData.copy().applyTo(btn);

		lbl = this.toolkit.createLabel(main, "", SWT.HORIZONTAL);
		GridDataFactory.fillDefaults().span(3, 1).grab(true, false)
				.applyTo(lbl);

		btn = this.toolkit.createButton(main, "Broken", SWT.PUSH);
		btn.addSelectionListener(new NbDartSelectionListener(0));
		btnData.copy().applyTo(btn);
	}

	/**
	 * Gets the nb darts.
	 *
	 * @return the nb darts
	 */
	public int getNbDarts() {
		return this.nbDarts;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.FormDialog#createButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createButtonBar(Composite parent) {
		return new Label(parent, SWT.NONE);
	}

	public class NbDartSelectionListener extends SelectionAdapter {

		private final int nb;

		public NbDartSelectionListener(int nb) {
			super();
			this.nb = nb;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			GameX01FinishDialog.this.nbDarts = this.nb;
			GameX01FinishDialog.this.close();
		}
	}
}
